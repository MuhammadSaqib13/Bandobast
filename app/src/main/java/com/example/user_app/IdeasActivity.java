package com.example.user_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.user_app.ImportantClass.LocationHelper;
import com.example.user_app.RecylerView.CategoryAdapter;
import com.example.user_app.RecylerView.NewIdeasAdapter;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.ResponseVendorTypeList;
import com.example.user_app.Retrofit.VendorListControls;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdeasActivity extends AppCompatActivity {

    ArrayList<VendorListControls> vendorList;
//    //ArrayList<String> vendorListString;
    CategoryAdapter adapter;
//
//    ArrayList<VendorTypeControls> tptimglist;
//    ArrayList<VendorTypeControls> catimglist;
    // categoriesRv
    BottomNavigationView bottomNavigationView;
    private Handler sliderHandler = new Handler();
    RecyclerView catRv;
    double latitude,longitude;

FloatingActionButton imgHome;
    private SpinKitView SkinkitWave;

//    String CatName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideas);


        ViewPager2 ideasViewPager = findViewById(R.id.locationViewPager);

        List<VendorTypeControls> ideasModel_list = new ArrayList<>();

        imgHome = findViewById(R.id.fb_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IdeasActivity.this,MainActivity.class));

            }
        });
        ApiBaseUrl baseUrl = new ApiBaseUrl();
        String BaseUrl = baseUrl.getBaseUrl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);

        SkinkitWave = findViewById(R.id.spin_kit);
        SkinkitWave.setVisibility(View.VISIBLE);

        LocationHelper locationHelper = new LocationHelper(getApplicationContext());
        LatLng latLng = locationHelper.getLatitudeLongitude();

        if (latLng != null) {
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            // Use the latitude and longitude as needed
            // Example: Log the values
            Log.i("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
        }
        Call<ResponseVendorType> getImgCall = api.getVendorType(latitude,longitude);
        getImgCall.enqueue(new Callback<ResponseVendorType>() {
            @Override
            public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                for (int i = 0; i < response.body().getData().size(); i++) {
                    VendorTypeControls vendorModel = new VendorTypeControls(response.body().getData().get(i).getImage(),response.body().getData().get(i).getName(),response.body().getData().get(i).getLocation());
                    ideasModel_list.add(vendorModel);

                }
                ideasViewPager.setAdapter(new NewIdeasAdapter(ideasModel_list));

                SkinkitWave.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResponseVendorType> call, Throwable t) {

            }
        });

        ideasViewPager.setClipToPadding(false);
        ideasViewPager.setClipChildren(false);
        ideasViewPager.setOffscreenPageLimit(3);
        ideasViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.95f + r * 0.05f);
            }
        });

        ideasViewPager.setPageTransformer(compositePageTransformer);



        //images
        catRv = findViewById(R.id.catRv);
        catRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));



        //
        vendorList =new ArrayList<>();
//        catimglist = new ArrayList<>();
//        tptimglist = new ArrayList<>();


        bottomNavigationView =findViewById(R.id.bottm_navigation);

        Menu bottomMenu = bottomNavigationView.getMenu();
        bottomMenu.findItem(R.id.imgVendor).setChecked(false);

        bottomMenu.findItem(R.id.imgIdeas).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.imgVendor:
                        startActivity(new Intent(IdeasActivity.this,Vendor_Main.class));
                        finish();
                        break;
                    case R.id.imgIdeas:
                        startActivity(new Intent(IdeasActivity.this,IdeasActivity.class));
                        finish();
                        break;
                    case R.id.img_Plan:
                        startActivity(new Intent(IdeasActivity.this,MainActivity.class));
                        finish();
                        break;
                    case R.id.imgNear:
                        startActivity(new Intent(IdeasActivity.this,Vendor_Main.class));
                        break;
                }
                return true;
            }
        });


    }

    private void categoryList() {
        ApiBaseUrl baseUrl = new ApiBaseUrl();
        String BaseUrl = baseUrl.getBaseUrl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api_cat = retrofit.create(Api.class);
        Call<ResponseVendorTypeList> cat_call = api_cat.getVendorList();
        cat_call.enqueue(new Callback<ResponseVendorTypeList>() {
            @Override
            public void onResponse(Call<ResponseVendorTypeList> call, Response<ResponseVendorTypeList> response) {
                for (int i = 1; i < response.body().getData().size(); i++) {
                    vendorList.add(new VendorListControls(response.body().getData().get(i).getId(),response.body().getData().get(i).getName()));
            }
                adapter = new CategoryAdapter(IdeasActivity.this, 1, vendorList);
                catRv.setAdapter(adapter);
                Log.i("vendorlist", vendorList.get(0).getName());
            }

            @Override
            public void onFailure(Call<ResponseVendorTypeList> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });
    }

    protected void onStart() {
        super.onStart();
        categoryList();
    }
    @Override
    public void onBackPressed() {
        // Call the super method to allow the default back button behavior
        super.onBackPressed();

        // Finish the current activity
        finish();
    }
}