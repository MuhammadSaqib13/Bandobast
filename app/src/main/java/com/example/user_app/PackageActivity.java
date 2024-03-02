package com.example.user_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_app.RecylerView.HallAdapter;
import com.example.user_app.RecylerView.PackageAdapter;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.Response;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackageActivity extends AppCompatActivity {

    PackageAdapter adapter;
    RecyclerView packageRecView;

    FloatingActionButton imgHome;
    ImageView imgMenu;
    BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        packageRecView = findViewById(R.id.package_rv);

        imgHome = findViewById(R.id.fb_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PackageActivity.this, NewMainActivity.class));
                finish();

            }
        });

        imgMenu = findViewById(R.id.img_dialog);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });

        bottomNavigationView =findViewById(R.id.bottm_navigation);
        Menu bottomMenu = bottomNavigationView.getMenu();
        bottomMenu.findItem(R.id.imgVendor).setChecked(false);
        bottomMenu.findItem(R.id.imgIdeas).setChecked(false);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.imgVendor:
                        startActivity(new Intent(PackageActivity.this,Vendor_Main.class));
                        finish();
                        break;
                    case R.id.imgIdeas:
                        startActivity(new Intent(PackageActivity.this,UserPackage2.class));
                        finish();
                        break;
                    case R.id.img_Plan:
                        startActivity(new Intent(PackageActivity.this,MainActivity.class));
                        finish();
                        break;
                    case R.id.imgNear:
                        startActivity(new Intent(PackageActivity.this,Vendor_Main.class));
                        finish();
                        break;
                }
                return true;
            }
        });

        Intent intent = getIntent();
//        CatRate.setText(intent.getStringExtra("Catering Rate"));
//        DecorRate.setText(intent.getStringExtra("Decorator Rate"));
//        EventRate.setText(intent.getStringExtra("Banquet Rate"));
//        TrpRate.setText(intent.getStringExtra("Transport Rate"));
//        PhotoRate.setText(intent.getStringExtra("Photographer Rate"));
//
//        Guest.setText(intent.getStringExtra("Guests"));
//        Budget.setText(intent.getStringExtra("Amount"));
//        Event.setText(intent.getStringExtra("EventType"));

        double catrate = Double.parseDouble(intent.getStringExtra("Catering Rate"));
        int CatInt = (int) catrate;
        Log.i("integerCatRate", String.valueOf(CatInt));

        double decorate = Double.parseDouble(intent.getStringExtra("Decorator Rate"));
        int DecoInt = (int) decorate;
        Log.i("integerDecoRate", String.valueOf(DecoInt));

        double banqrate = Double.parseDouble(intent.getStringExtra("Banquet Rate"));
        int BanqInt = (int) banqrate;
        Log.i("integerBanqRate", String.valueOf(BanqInt));

        double trprate = Double.parseDouble(intent.getStringExtra("Transport Rate"));
        int TrpInt = (int) trprate;
        Log.i("integerTrpRate", String.valueOf(TrpInt));

        double photorate = Double.parseDouble(intent.getStringExtra("Photographer Rate"));
        int PhotoInt = (int) photorate;
        Log.i("integerPhotoRate", String.valueOf(PhotoInt));

        JsonObject jsonParams = new JsonObject();
        jsonParams.addProperty("catererRating",5);
        jsonParams.addProperty("transporterRating",0);
        jsonParams.addProperty("banquetRating",5);
        jsonParams.addProperty("decoratorRating", 0);
        jsonParams.addProperty("photographerRating", 2);

        UUID EventId = UUID.fromString(intent.getStringExtra("EventTypeId"));
        jsonParams.addProperty("event", EventId.toString());

        jsonParams.addProperty("selectedEvent",intent.getStringExtra("SelectedEvent"));

        Integer guests = Integer.parseInt(intent.getStringExtra("Guests"));
        Log.i("integerGuest", guests.toString());

        Integer budget = Integer.parseInt(intent.getStringExtra("Amount"));
        Log.i("integerBudget", budget.toString());


        jsonParams.addProperty("guests", guests);
        jsonParams.addProperty("budget", budget);


        getPackageResponse(jsonParams);








    }
    private void getPackageResponse(JsonObject jsonParams){
//        String servResponse = jsonParams.toString();
//        Log.i("parentObj", jsonParams.toString());
//        JSONObject parentObj = null;
//        try {
//            parentObj = new JSONObject(servResponse);
//            Log.i("parentObj", parentObj.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        ApiBaseUrl base = new ApiBaseUrl();
        String BaseUrl =  base.getBaseUrl();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        Api api =retrofit.create(Api.class);



        Call<Response> call = api.GetPackageResponse(jsonParams);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response){
                String[] packages = null;// declare krdya ab nkya inin karo input kya krna hai?
                Integer[] totamount =null;
                JSONObject jsonObject =null;
                JSONArray jsonArray = null;
                if (response.isSuccessful()) {
                        String servResponse = response.body().getData().toString();
                        try {
                            jsonArray = new JSONArray(servResponse);
                            Log.i("jsonArray",String.valueOf(jsonArray.length()) );
                            packages = new String[12];
                            totamount = new Integer[12];
                            for (int i = 0; i < 12; i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                packages[i]= "Package - "+String.valueOf(i+1);
                                //jsonObject yahan ye kynke jab package 1 per me cleck karonga to next per mujhe issi ke object me se values uthhani he ab tum chaaho to card ke onclick per object pass karado iska sahi hai
                                // Total Amount agai yahan se Usko set karao task 1
                                // jis Pakcage per click karon uskaobject log i me print karado task 2 ye karo phir mujhe batao ok
                                // mager ye dihan rakhan ke total amount match horhi ho tumhari reverse order krna hai na? list bnakr ulti krdeta hun/> ruko 2 mint kuch karna nhi

                                Log.i("TotalAmount",String.valueOf(jsonObject.getInt("totalAmount")) );
                                totamount[i] = jsonObject.getInt("totalAmount");
                                Log.i("AmountList", String.valueOf(totamount[i]));


                                // Sort the JSON array based on the "totalAmount" property in ascending order
//                            Log.i("UnsortedArray",jsonArray.toString());
//                            jsonArray = sortJSONArray(jsonArray, "totalAmount");
//
//                            Log.i("SortedArray",jsonArray.toString());



                                // ab tum ye karoge jo sb se bari amount he wo package 1 he reverse order me giraoge q?
                                // ab jo package uthaya he us index ka poora package kahin store karaoge

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < packages.length; i++) {
                            Log.i(String.valueOf(i), packages[i]);
                        }
                        Log.i("JSonObject", jsonObject.toString());
                        adapter = new PackageAdapter(PackageActivity.this,packages,totamount, jsonArray);
                        packageRecView.setLayoutManager(new LinearLayoutManager(PackageActivity.this,LinearLayoutManager.VERTICAL,false));
                        packageRecView.setAdapter(adapter);





                }
                else{
                    Log.i("failed", String.valueOf(response.code()));
//                    Log.i("responseMessage",response.body().getMessage().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(PackageActivity.this);
                    builder.setTitle("No Packages Found");
                    builder.setMessage("We are sorry you have to raise your budget to get a Package.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
//                                showEnableWifiDialog();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
//                    Toast.makeText(PackageActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.i("PackageResponse", t.getMessage());

            }
        });



    }
    private JSONArray sortJSONArray(JSONArray jsonArray, final String property) {
        JSONArray sortedArray = new JSONArray();

        try {
            List<JSONObject> jsonObjects = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObjects.add(jsonArray.getJSONObject(i));
            }

            Collections.sort(jsonObjects, (obj1, obj2) -> {
                int value1 = 0;
                try {
                    value1 = obj1.getInt(property);
                    Log.i("Value1",String.valueOf(value1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int value2 = 0;
                try {
                    value2 = obj2.getInt(property);
                    Log.i("Value2",String.valueOf(value2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return Integer.compare(value2, value1);
            });

            for (JSONObject jsonObject : jsonObjects) {
                sortedArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("ReturnArray",sortedArray.toString());
        return sortedArray;
    }
    private void showBottomDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PackageActivity.this,R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.layout_bottom_sheet,
                        (LinearLayout) findViewById(R.id.bottomSheetContainer)
                );

        bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PackageActivity.this, "Share...", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
    @Override
    public void onBackPressed() {
        // Call the super method to allow the default back button behavior
        super.onBackPressed();

        // Finish the current activity
        finish();
    }
}
