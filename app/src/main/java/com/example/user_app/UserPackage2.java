package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.ResponseVendorTypeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPackage2 extends AppCompatActivity {

    private static final ArrayList<String> event_types_for_autocomplete = new ArrayList<String>();
    private static final Map<String,String> EventType = new HashMap<String,String>();

    AutoCompleteTextView txt_eventType;
    ImageView img_back;
    Button btn_next;
    String Cat_rate, Trp_rate, Phot_rate, Decor_rate, Bnqut_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_package2);
        btn_next = findViewById(R.id.btn_Next);
        txt_eventType = findViewById(R.id.eventlistautocomplete);
        getEventType();

        img_back = findViewById(R.id.btn_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPackage2.this,NewMainActivity.class));
                finish();
            }
        });

        Intent intent = getIntent();
        Cat_rate = intent.getStringExtra("Catering Rate");
        Decor_rate = intent.getStringExtra("Decorator Rate");
        Bnqut_rate = intent.getStringExtra("Banquet Rate");
        Phot_rate = intent.getStringExtra("Photographer Rate");
        Trp_rate = intent.getStringExtra("Transport Rate");

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


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EventTypeId = EventType.get(txt_eventType.getText().toString());
                String EventSelect = txt_eventType.getText().toString();
                //Log.i("EventType",EventTypeId);

                if (EventTypeId == null) {
                    Toast.makeText(UserPackage2.this,"No Event Selected Please Try Again!",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent1 = new Intent(UserPackage2.this, UserPackage3.class);
                    intent1.putExtra("Catering Rate", "5");
                    intent1.putExtra("Decorator Rate", "0");
                    intent1.putExtra("Banquet Rate", "5");
                    intent1.putExtra("Transport Rate", "0");
                    intent1.putExtra("Photographer Rate", "2");
                    intent1.putExtra("EventTypeId", EventTypeId);
                    intent1.putExtra("SelectedEvent",EventSelect);


                    startActivity(intent1);
                }
            }
        });
    }
    private void getEventType() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, event_types_for_autocomplete);
        txt_eventType = findViewById(R.id.eventlistautocomplete);
        txt_eventType.setAdapter(adapter);

        ApiBaseUrl base = new ApiBaseUrl();
        String BaseUrl =  base.getBaseUrl();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(20,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        Api api =retrofit.create(Api.class);

        Call<ResponseVendorTypeList> menuResponse = api.getEventList();
        menuResponse.enqueue(new Callback<ResponseVendorTypeList>() {
            @Override
            public void onResponse(Call<ResponseVendorTypeList> call, retrofit2.Response<ResponseVendorTypeList> response) {
                EventType.clear();
                event_types_for_autocomplete.clear();
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        event_types_for_autocomplete.add(response.body().getData().get(i).getName());
                        EventType.put(response.body().getData().get(i).getName(),response.body().getData().get(i).getId());
                        //Log.i("EventList",EventType.get(txt_eventType.getText().toString()));
                    }
                }
                else{
                    Toast.makeText(UserPackage2.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseVendorTypeList> call, Throwable t) {
                Log.i("Failed",t.getMessage());
            }
        });

//        event_types_for_autocomplete.add("All wedding events");
//        event_types_for_autocomplete.add("Baraat");
//        event_types_for_autocomplete.add("Baraat & Valima");
//        event_types_for_autocomplete.add("Birthday Party");
//        event_types_for_autocomplete.add("Casual Birthday ");
//        event_types_for_autocomplete.add("Mehndi");
//        event_types_for_autocomplete.add("Mehndi & Baraat");
//        event_types_for_autocomplete.add("Picnic");
//        event_types_for_autocomplete.add("Mehndi & Mayon");
//        event_types_for_autocomplete.add("Valima");

        //EventType.put(response.body().getData().get(i).getName(),response.body().getData().get(i).getId());

    }
    @Override
    public void onBackPressed() {
        // Call the super method to allow the default back button behavior
        super.onBackPressed();

        // Finish the current activity
        finish();
    }
}