package com.example.user_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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

import com.example.user_app.ImportantClass.LocationHelper;
import com.example.user_app.RecylerView.DecoHorizotalAdapter;
import com.example.user_app.RecylerView.HallAdapter;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectedPackageActivity extends AppCompatActivity {

    FloatingActionButton imgHome;
    ImageView imgMenu, imgBanquet, imgCaterer, imgPhotographer;

    double latitude, longitude;

    RecyclerView trptRecView, decoRecView;
    HallAdapter adpater;
    DecoHorizotalAdapter adapter;
    String _TrpCode, _DecCode, sImage,Tname,TPrice,Tlocation;
    //    ArrayList<HallModel> HallList = null;
    ArrayList<VendorTypeControls> TrpList = null;
    ArrayList<VendorTypeControls> DecoList = null;

    private SpinKitView SkinkitWave;

    TextView _Banqcode, BanqName, BanqLocation, BanqAmount, _CatCode, CatName, CatLocation, CatAmount, _PhotoCode, PhotoName, PhotoLocation,PhotoAmount;

    CardView PhotoCard, BanqCard, CatCard;
    BottomNavigationView bottomNavigationView;

    ApiBaseUrl Base = new ApiBaseUrl();
    String BaseUrl = Base.getBaseUrl();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Api api =retrofit.create(Api.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_package);


        SkinkitWave = findViewById(R.id.spin_kit);
        SkinkitWave.setVisibility(View.VISIBLE);

        PhotoCard = findViewById(R.id.Photographer_Card);
        CatCard = findViewById(R.id.Caterer_Card);
        BanqCard = findViewById(R.id.Banquet_Card);

        LocationHelper locationHelper = new LocationHelper(getApplicationContext());
        LatLng latLng = locationHelper.getLatitudeLongitude();

        if (latLng != null) {
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            // Use the latitude and longitude as needed
            // Example: Log the values
            Log.i("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
        }

        imgHome = findViewById(R.id.fb_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedPackageActivity.this, NewMainActivity.class));
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
                        startActivity(new Intent(SelectedPackageActivity.this,Vendor_Main.class));
                        finish();
                        break;
                    case R.id.imgIdeas:
                        startActivity(new Intent(SelectedPackageActivity.this,UserPackage2.class));
                        finish();
                        break;
                    case R.id.img_Plan:
                        startActivity(new Intent(SelectedPackageActivity.this,NewMainActivity.class));
                        finish();
                        break;
                    case R.id.imgNear:
                        startActivity(new Intent(SelectedPackageActivity.this,Vendor_Main.class));
                        break;
                }
                return true;
            }
        });


        _Banqcode = findViewById(R.id.lbl_BanqCode);
        imgBanquet = findViewById(R.id.imgBanquet);
        BanqName = findViewById(R.id.txt_BanqName);
        BanqLocation = findViewById(R.id.BanqLocation);
        BanqAmount = findViewById(R.id.banq_amnt_range);

        //Caterer Info
        _CatCode = findViewById(R.id.lbl_CatCode);
        imgCaterer = findViewById(R.id.imgCaterer);
        CatName = findViewById(R.id.txt_CatName);
        CatLocation = findViewById(R.id.CatLocation);
        CatAmount = findViewById(R.id.cat_amnt_range);

        String banquet, caterer, photographer, BaseUrl, imageUrl;

        ApiBaseUrl baseUrl = new ApiBaseUrl();
        BaseUrl = baseUrl.getBaseUrl();

        banquet = getIntent().getStringExtra("Banquet");
        try {
            JSONObject banquetObject = new JSONObject(banquet);
            Log.i("baquetObjectImage",banquetObject.get("image").toString());
            imageUrl = banquetObject.get("image").toString().replace("\\\\","/");
            Picasso.get().load(BaseUrl+"/"+imageUrl).into(imgBanquet);
            BanqName.setText(banquetObject.get("banquet").toString());
            BanqLocation.setText(banquetObject.get("location").toString());
            BanqAmount.setText("PKR: "+String.valueOf(banquetObject.getInt("price")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
//      CatererInfo
        caterer = getIntent().getStringExtra("Caterer");
        try {
            JSONObject catererObject = new JSONObject(caterer);
            Log.i("catererObject",catererObject.get("restaurant").toString());
            imageUrl = catererObject.get("image").toString().replace("\\\\","/");
            Picasso.get().load(BaseUrl+"/"+imageUrl).into(imgCaterer);
            CatName.setText(catererObject.get("restaurant").toString());
            CatLocation.setText(catererObject.get("location").toString());
            CatAmount.setText("PKR: "+String.valueOf(catererObject.getInt("price")) +" "+ catererObject.get("priceCategoryId"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Photographer Info
        _PhotoCode = findViewById(R.id.lbl_PhotoCode);
        imgPhotographer = findViewById(R.id.imgPhotographer);
        PhotoName = findViewById(R.id.txt_PhotoName);
        PhotoLocation = findViewById(R.id.PhotoLocation);
        PhotoAmount = findViewById(R.id.photo_amnt_range);

        photographer = getIntent().getStringExtra("Photographer");
        try {
            JSONObject photographerObject = new JSONObject(photographer);
            Log.i("photographerObject",photographerObject.toString());
            imageUrl = photographerObject.get("image").toString().replace("\\\\","/");
            Log.i("PhotoUrl",imageUrl);
            if (imageUrl.contains(".jpg,")) {
                String[] images = imageUrl.split(",");
                Picasso.get().load(BaseUrl+"/"+images[0]).into(imgPhotographer);

            }
            else
            {
                Picasso.get().load(BaseUrl+"/"+imageUrl).into(imgPhotographer);
            }
            PhotoName.setText(photographerObject.get("name").toString());
//            if (photographerObject.get("location").toString().contains("un")) {
//
//            }
            PhotoLocation.setText(photographerObject.get("location").toString().replace("unnamed road, ",""));
            PhotoAmount.setText("PKR: "+String.valueOf(photographerObject.getInt("price")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Photographer Single Activity
        PhotoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedPackageActivity.this, SingleVendor.class);
                intent.putExtra("Photographer",photographer);
                intent.putExtra("FromPackage",true);
                intent.putExtra("VendorType","Photographer");
                startActivity(intent);
            }
        });

        //Banquet Single Activity
        BanqCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedPackageActivity.this, SingleVendor.class);
                intent.putExtra("Banquet",banquet);
                intent.putExtra("FromPackage",true);
                intent.putExtra("VendorType","EventPlacer");
                startActivity(intent);
            }
        });

        //Caterer Single Activity
        CatCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedPackageActivity.this, SingleVendor.class);
                intent.putExtra("Caterer",caterer);
                intent.putExtra("FromPackage",true);
                intent.putExtra("VendorType","Caterer");
                startActivity(intent);
            }
        });



        trptRecView = findViewById(R.id.trpt_scroll);
        TrpList = new ArrayList<VendorTypeControls>();
        getTransporter();

        decoRecView = findViewById(R.id.deco_scroll);
        DecoList = new ArrayList<VendorTypeControls>();
        getDecorator();



    }

    public void getDecorator(){

        Call<ResponseVendorType> call = api.getDecoratorType(latitude,longitude);
        call.enqueue(new Callback<ResponseVendorType>() {
            @Override
            public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        String ImageendPoints = response.body().getData().get(i).getImage();
                        if (ImageendPoints.contains(".jpg,")) {
                            String[] images = ImageendPoints.split(",");
                            for (int k = 0; k < images.length; k++) {
                                images[k] = images[k].replace("\\ ", "/");
                            }
                            sImage = BaseUrl +"/"+ images[0];
                        } else {
                            sImage = BaseUrl + "/" + ImageendPoints;
                        }

                        //sImage = response.body().getData().get(i).getImage();
                        _DecCode = response.body().getData().get(i).getCode();
                        Tname = response.body().getData().get(i).getName();
                        Tlocation = response.body().getData().get(i).getPrice();
                        TPrice = response.body().getData().get(i).getPrice();

                        DecoList.add(new VendorTypeControls(_DecCode,Tname,TPrice,Tlocation,sImage));

                    }
                    adapter = new DecoHorizotalAdapter(SelectedPackageActivity.this,DecoList);
                    decoRecView.setLayoutManager(new LinearLayoutManager(SelectedPackageActivity.this,LinearLayoutManager.HORIZONTAL,false));
                    decoRecView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                Log.i("decorator",t.getMessage());
                ;            }
        });

    }

    public void getTransporter(){
//        ApiBaseUrl Base = new ApiBaseUrl();
//        String BaseUrl = Base.getBaseUrl();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Api api =retrofit.create(Api.class);
        Call<ResponseVendorType> call = api.getVendorType(latitude,longitude);
        call.enqueue(new Callback<ResponseVendorType>() {
            @Override
            public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        String ImageendPoints = response.body().getData().get(i).getImage();
                            if (ImageendPoints.contains(".jpg,")) {
                                String[] images = ImageendPoints.split(",");
                                for (int k = 0; k < images.length; k++) {
                                    images[k] = images[k].replace("\\ ", "/");
                                }
                                sImage = BaseUrl +"/"+ images[0];
                            } else {
                                sImage = BaseUrl + "/" + ImageendPoints;
                            }

                        //sImage = response.body().getData().get(i).getImage();
                        _TrpCode = response.body().getData().get(i).getCode();
                        Tname = response.body().getData().get(i).getName();
                        Tlocation = response.body().getData().get(i).getPrice();
                        TPrice = response.body().getData().get(i).getPrice()+" per km";

                        TrpList.add(new VendorTypeControls(_TrpCode,Tname,TPrice,Tlocation,sImage));

                    }
                    adpater = new HallAdapter(SelectedPackageActivity.this,TrpList);
                    trptRecView.setLayoutManager(new GridLayoutManager(SelectedPackageActivity.this,3));
                    trptRecView.setAdapter(adpater);
                    SkinkitWave.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                Log.i("transporter",t.getMessage());
                ;            }
        });
    }




    private void showBottomDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SelectedPackageActivity.this,R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.layout_bottom_sheet,
                        (LinearLayout) findViewById(R.id.bottomSheetContainer)
                );

        bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectedPackageActivity.this, "Share...", Toast.LENGTH_SHORT).show();
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