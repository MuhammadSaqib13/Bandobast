package com.example.user_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.user_app.ImportantClass.LocationHelper;
import com.example.user_app.RecylerView.GalleryAdapter;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryActivity extends AppCompatActivity {
    String sImage;
    private Toolbar toolbar;
    private SpinKitView SkinkitWave;
    ArrayList<VendorTypeControls> VendorImageList = null;

    GalleryAdapter galleryAdapter;
FloatingActionButton imgHome;
    RecyclerView ImgRecView;

    double latitude,longitude;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);


        LocationHelper locationHelper = new LocationHelper(getApplicationContext());
        LatLng latLng = locationHelper.getLatitudeLongitude();

        if (latLng != null) {
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            // Use the latitude and longitude as needed
            // Example: Log the values
            Log.i("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
        }

        SkinkitWave = findViewById(R.id.spin_kit);
        SkinkitWave.setVisibility(View.VISIBLE);

        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle((getIntent().getStringExtra("Vendor Name")).toUpperCase());

        imgHome = findViewById(R.id.fb_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GalleryActivity.this,NewMainActivity.class));

            }
        });


        bottomNavigationView =findViewById(R.id.bottm_navigation);
        Menu bottomMenu = bottomNavigationView.getMenu();
        bottomMenu.findItem(R.id.imgVendor).setChecked(false);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.imgVendor:
                        startActivity(new Intent(GalleryActivity.this,Vendor_Main.class));
                        finish();
                        break;

                    case R.id.imgIdeas:
                        startActivity(new Intent(GalleryActivity.this, UserPackage2.class));
                        finish();
                        break;
                    case R.id.img_Plan:
                        startActivity(new Intent(GalleryActivity.this,NewMainActivity.class));
                        finish();
                        break;
                    case R.id.imgNear:
                        startActivity(new Intent(GalleryActivity.this,Vendor_Main.class));
                        finish();
                        break;
                }
                return true;
            }
        });
        ImgRecView = findViewById(R.id.imageGallery);

        Intent intent = getIntent();
        String VendName = intent.getStringExtra("Vendor Name");
        VendName = (VendName.replace(" ",""));

        ApiBaseUrl base = new ApiBaseUrl();
        String BaseUrl =  base.getBaseUrl();

        VendorImageList = new ArrayList<VendorTypeControls>();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(20, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        Api api_cat =retrofit.create(Api.class);
        Call<ResponseVendorType> call_cat = api_cat.getCatererType(latitude,longitude);

        if(VendName.equals("Caterer"))
        {
            call_cat.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
//                            sImage = response.body().getData().get(i).getImage();
                                String ImageendPoints = response.body().getData().get(i).getImage();
                                if (ImageendPoints.contains(".jpg,")){
                                    String[] images = ImageendPoints.split(",");
                                    sImage = BaseUrl +"/"+ images[0];
                                }else{
                                    sImage= BaseUrl + "/" + ImageendPoints;
                                }
                                VendorImageList.add(new VendorTypeControls(sImage));

                            }
                            galleryAdapter = new GalleryAdapter(GalleryActivity.this, VendorImageList);
                            ImgRecView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                            ImgRecView.setAdapter(galleryAdapter);

                            SkinkitWave.setVisibility(View.GONE);
                        }
                        if (response.body().getData() == null) {
                            Log.i("error", "No data found");
                        }
                    }
                    else{
                        Toast.makeText(GalleryActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }

                    //Log.i("response","response size" + response.body().getData().size());


                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("response_f","no response");
                }
            });
        }
        Api api =retrofit.create(Api.class);
        Call<ResponseVendorType> call = api.getEventPlacerType(latitude,longitude);

        if(VendName.equals("EventPlacer"))
        {

            call.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
//                            sImage = response.body().getData().get(i).getImage();
                                String ImageendPoints = response.body().getData().get(i).getImage();
                                if (ImageendPoints.contains(".jpg,")){
                                    String[] images = ImageendPoints.split(",");
                                    sImage = BaseUrl +"/"+ images[0];
                                }else{
                                    sImage= BaseUrl + "/" + ImageendPoints;
                                }
                                VendorImageList.add(new VendorTypeControls(sImage));

                            }
                            galleryAdapter = new GalleryAdapter(GalleryActivity.this, VendorImageList);
                            ImgRecView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                            ImgRecView.setAdapter(galleryAdapter);

                            SkinkitWave.setVisibility(View.GONE);
                        }
                        if (response.body().getData() == null) {
                            Log.i("error", "No data found");
                        }
                    }
                    else{
                        Toast.makeText(GalleryActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("response_f",t.getMessage());
                    Toast.makeText(getApplicationContext(),"Check your connection and Try again",Toast.LENGTH_LONG).show();

                }
            });
        }

        Api api2 = retrofit.create(Api.class);
        Call<ResponseVendorType> call2 = api2.getVendorType(latitude,longitude);

        if(VendName.equals("Transporter"))
        {
            call2.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    if (response.isSuccessful()) {
                        if(response.body().getData() != null)
                        {
                            for (int i = 0; i < response.body().getData().size(); i++) {
//                            sImage = response.body().getData().get(i).getImage();
                                String ImageendPoints = response.body().getData().get(i).getImage();
                                if (ImageendPoints.contains(".jpg,")){
                                    String[] images = ImageendPoints.split(",");
                                    sImage = BaseUrl +"/"+ images[0];
                                }else{
                                    sImage= BaseUrl + "/" + ImageendPoints;
                                }
                                VendorImageList.add(new VendorTypeControls(sImage));

                            }
                            galleryAdapter = new GalleryAdapter(GalleryActivity.this,VendorImageList);
                            ImgRecView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                            ImgRecView.setAdapter(galleryAdapter);

                            SkinkitWave.setVisibility(View.GONE);

                        }
                        if(response.body().getData() == null)
                        {
                            Log.i("error", "No data found");
                        }
                    }
                    else{
                        Toast.makeText(GalleryActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }




                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("response_f","no response");
                    Toast.makeText(getApplicationContext(),"Check your connection and Try again",Toast.LENGTH_LONG).show();
                }
            });
        }

        Api api_photo =retrofit.create(Api.class);
        Call<ResponseVendorType> call_photo = api_photo.getPhotographerType(latitude,longitude);

        if(VendName.equals("Photographer")) {

            call_photo.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    if (response.isSuccessful()) {

                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
//                            sImage = response.body().getData().get(i).getImage();
                                String ImageendPoints = response.body().getData().get(i).getImage();
                                if (ImageendPoints.contains(".jpg,")){
                                    String[] images = ImageendPoints.split(",");
                                    sImage = BaseUrl +"/"+ images[0];
                                }else{
                                    sImage= BaseUrl + "/" + ImageendPoints;
                                }
                                VendorImageList.add(new VendorTypeControls(sImage));

                            }
                            galleryAdapter = new GalleryAdapter(GalleryActivity.this, VendorImageList);
                            ImgRecView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                            ImgRecView.setAdapter(galleryAdapter);

                            SkinkitWave.setVisibility(View.GONE);
                        }
                        if (response.body().getData() == null) {
                            Log.i("error", "No data found");
                        }
                    }
                    else{
                        Toast.makeText(GalleryActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }



                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("response_f", "no response");
                    Toast.makeText(getApplicationContext(), "Check your connection and Try again", Toast.LENGTH_LONG).show();
                }
            });
        }
        Api api_dec =retrofit.create(Api.class);
        Call<ResponseVendorType> call_dec = api_dec.getDecoratorType(latitude,longitude);

        if(VendName.equals("Decorator")) {

            call_dec.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
//                            sImage = response.body().getData().get(i).getImage();
                                String ImageendPoints = response.body().getData().get(i).getImage();
                                if (ImageendPoints.contains(".jpg,")){
                                    String[] images = ImageendPoints.split(",");
                                    sImage = BaseUrl +"/"+ images[0];
                                }else{
                                    sImage= BaseUrl + "/" + ImageendPoints;
                                }
                                VendorImageList.add(new VendorTypeControls(sImage));

                            }
                            galleryAdapter = new GalleryAdapter(GalleryActivity.this, VendorImageList);
                            ImgRecView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                            ImgRecView.setAdapter(galleryAdapter);

                            SkinkitWave.setVisibility(View.GONE);
                        }
                        if (response.body().getData() == null) {
                            Log.i("error", "No data found");
                        }
                    }
                    else{
                        Toast.makeText(GalleryActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }



                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("response_f", "no response");
                    Toast.makeText(getApplicationContext(), "Check your connection and Try again", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
    @Override
    public void onBackPressed() {
        // Call the super method to allow the default back button behavior
        super.onBackPressed();

        // Finish the current activity
        finish();
    }

}