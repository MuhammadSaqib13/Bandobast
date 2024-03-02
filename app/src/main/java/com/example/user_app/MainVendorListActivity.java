package com.example.user_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_app.ImportantClass.LocationHelper;
import com.example.user_app.RecylerView.CatererAdapter;
import com.example.user_app.RecylerView.DecoratorAdapter;
import com.example.user_app.RecylerView.EventPlacerAdapter;
import com.example.user_app.RecylerView.PhotoAdapter;
import com.example.user_app.RecylerView.TransporterAdapter;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.example.user_app.databinding.ActivityMainBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
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

public class MainVendorListActivity extends AppCompatActivity {
    RecyclerView VendorRecView;
    TransporterAdapter adpater;
    TransporterAdapter adapterfilter;
    EventPlacerAdapter EvntAdapter;
    CatererAdapter CatAdapter;
    DecoratorAdapter decAdapter;
    PhotoAdapter photoAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout dataLayout;
    SearchView searchView;
    ArrayList<VendorTypeControls> VendorServiceList = null;
    ArrayList<VendorTypeControls> DecList = null;
    LocationListener locationTrack;
    private static double LAT = 0.0;
    private static double LONG = 0.0;

    double latitude, longitude;

    ImageView ImgVendor;
    TextView Hometxt;

    FloatingActionButton imgHome;
    ImageView Homebtn;
    TextView TxtVendor;

    TextView VendorName;
    String sImage,Tname,Tloc, TPrice,Tcode;
    double distance;
    private Toolbar toolbar;
    private SpinKitView SkinkitWave;

    BottomNavigationView bottomNavigationView;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vendor_list);


//        SkinkitWave = findViewById(R.id.spin_kit);
//        SkinkitWave.setVisibility(View.VISIBLE);

        dataLayout = findViewById(R.id.main_data);
        dataLayout.setVisibility(View.GONE);

        imgHome = findViewById(R.id.fb_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainVendorListActivity.this,NewMainActivity.class));

            }
        });
        shimmerFrameLayout= findViewById(R.id.shimmer_view);
        shimmerFrameLayout.startShimmerAnimation();

        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle((getIntent().getStringExtra("Vendor Name")).toUpperCase());

        LocationHelper locationHelper = new LocationHelper(getApplicationContext());
        LatLng latLng = locationHelper.getLatitudeLongitude();

        if (latLng != null) {
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            // Use the latitude and longitude as needed
            // Example: Log the values
            Log.i("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
        }
        bottomNavigationView =findViewById(R.id.bottm_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.imgVendor:
                        startActivity(new Intent(MainVendorListActivity.this,Vendor_Main.class));
                        finish();
                        break;
                    case R.id.imgIdeas:
                        startActivity(new Intent(MainVendorListActivity.this,UserPackage2.class));
                        finish();
                        break;
                    case R.id.img_Plan:
                        startActivity(new Intent(MainVendorListActivity.this,NewMainActivity.class));
                        finish();
                        break;
                    case R.id.imgNear:
                        startActivity(new Intent(MainVendorListActivity.this,Vendor_Main.class));
                        finish();
                        break;
                }
                return true;
            }
        });

        Intent intent = getIntent();
        String VendName = intent.getStringExtra("Vendor Name");
        VendName = (VendName.replace(" ",""));
        VendorRecView = findViewById(R.id.MainVendorList);

        ApiBaseUrl base = new ApiBaseUrl();
        String BaseUrl =  base.getBaseUrl();

        VendorServiceList = new ArrayList<VendorTypeControls>();
        DecList = new ArrayList<VendorTypeControls>();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(20,TimeUnit.SECONDS)
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

                        for (int i = 0; i < response.body().getData().size(); i++) {

                            String ImageendPoints = response.body().getData().get(i).getImage();
                            if (ImageendPoints.contains(".jpg,")){
                                String[] images = ImageendPoints.split(",");
                                for (int k = 0; k < images.length; k++) {
                                    images[k] = images[k].replace("\\ ", "/");
                                }
                                sImage = BaseUrl +"/"+ images[0];
                            }else{
                                ImageendPoints = ImageendPoints.replace("\\ ","/");
                                sImage= BaseUrl + "/" + ImageendPoints;
                            }
                            Log.i("urlCaterer", sImage);

//                        sImage = response.body().getData().get(i).getImage();
                            Tcode = response.body().getData().get(i).getCode();
                            Tname = response.body().getData().get(i).getName();
                            distance = response.body().getData().get(i).getDistance();
                            TPrice = response.body().getData().get(i).getPrice();
                            VendorServiceList.add(new VendorTypeControls(sImage,Tcode,Tname,distance,TPrice));

                        }
                        CatAdapter = new CatererAdapter(MainVendorListActivity.this,VendorServiceList);
                        VendorRecView.setLayoutManager(new GridLayoutManager(MainVendorListActivity.this,2));
                        VendorRecView.setAdapter(CatAdapter);

                        //SkinkitWave.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        dataLayout.setVisibility(View.VISIBLE);

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                //filterList(newText);
                                ArrayList<VendorTypeControls> filterVendor = new ArrayList<VendorTypeControls>();
                                for (VendorTypeControls model : VendorServiceList)
                                {
                                    Log.i("model value", model.getName());
                                    if(model.getName().toLowerCase().contains(newText.toLowerCase()))
                                    {
                                        filterVendor.add(model);

                                    }

                                }
                                if(filterVendor.isEmpty())
                                {
                                    Toast.makeText(MainVendorListActivity.this, "No Such type of vendors", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    CatAdapter.setFilteredVendors(filterVendor);
                                }
                                return true;
                            }
                        });
                    }
                    else{
                        Toast.makeText(MainVendorListActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }
                    //Log.i("response","response size" + response.body().getData().size());

                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("response_f","no response");
                }
            });
        }
        Api api_photo =retrofit.create(Api.class);
        Call<ResponseVendorType> call_photo = api_photo.getPhotographerType(latitude,longitude);

        if(VendName.equals("Photographer"))
        {

            call_photo.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {

                                String ImageendPoints = response.body().getData().get(i).getImage();
                                if (ImageendPoints.contains(".jpg,")){
                                    String[] images = ImageendPoints.split(",");
                                    for (int k = 0; k < images.length; k++) {
                                        images[k] = images[k].replace("\\ ", "/");
                                    }
                                    sImage = BaseUrl +"/"+ images[0];
                                }else{
                                    ImageendPoints = ImageendPoints.replace("\\ ","/");
                                    sImage= BaseUrl + "/" + ImageendPoints;
                                }
                                Log.i("urlPhotographer", sImage);

//                            sImage = response.body().getData().get(i).getImage();
                                Tcode = response.body().getData().get(i).getCode();
                                Tname = response.body().getData().get(i).getName();
                                distance = response.body().getData().get(i).getDistance();
                                TPrice = response.body().getData().get(i).getPrice();
                                VendorServiceList.add(new VendorTypeControls(sImage,Tcode,Tname,distance,TPrice));

                            }
                            photoAdapter = new PhotoAdapter(MainVendorListActivity.this,VendorServiceList);
                            VendorRecView.setLayoutManager(new GridLayoutManager(MainVendorListActivity.this,2));
                            VendorRecView.setAdapter(photoAdapter);

                            //SkinkitWave.setVisibility(View.GONE);
                            shimmerFrameLayout.stopShimmerAnimation();
                            shimmerFrameLayout.setVisibility(View.INVISIBLE);
                            dataLayout.setVisibility(View.VISIBLE);

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    ArrayList<VendorTypeControls> filterVendor = new ArrayList<VendorTypeControls>();
                                    for (VendorTypeControls model : VendorServiceList)
                                    {
                                        if(model.getName().toLowerCase().contains(newText.toLowerCase()))
                                        {
                                            filterVendor.add(model);
                                        }

                                    }
                                    if(filterVendor.isEmpty())
                                    {
                                        Toast.makeText(MainVendorListActivity.this, "No Such type of vendors", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        photoAdapter.setFilteredVendors(filterVendor);
                                    }
                                    return true;
                                }
                            });


                        }
                        if(response.body().getData() == null)
                        {
                            SkinkitWave.setVisibility(View.GONE);
                            Log.i("error", "response is null");
                            Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(MainVendorListActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("response_f",t.getMessage());
                    Toast.makeText(getApplicationContext(),"Check your connection and Try again",Toast.LENGTH_LONG).show();

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

                        for (int i = 0; i < response.body().getData().size(); i++) {

                            String ImageendPoints = response.body().getData().get(i).getImage();
                            if (ImageendPoints.contains(".jpg,")){
                                String[] images = ImageendPoints.split(",");
                                for (int k = 0; k < images.length; k++) {
                                    images[k] = images[k].replace("\\ ", "/");
                                }
                                sImage = BaseUrl +"/"+ images[0];
                            }else{
                                ImageendPoints = ImageendPoints.replace("\\ ","/");
                                sImage= BaseUrl + "/" + ImageendPoints;
                            }
                            Log.i("urlEventPlacer", sImage);

//                        sImage = response.body().getData().get(i).getImage();
                            Tcode = response.body().getData().get(i).getCode();
                            Tname = response.body().getData().get(i).getName();
                            distance = response.body().getData().get(i).getDistance();
                            TPrice = response.body().getData().get(i).getPrice();
                            VendorServiceList.add(new VendorTypeControls(sImage,Tcode,Tname,distance,TPrice));

                        }
                        EvntAdapter = new EventPlacerAdapter(MainVendorListActivity.this,VendorServiceList);
                        VendorRecView.setLayoutManager(new GridLayoutManager(MainVendorListActivity.this,2));
                        VendorRecView.setAdapter(EvntAdapter);

                        //SkinkitWave.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        dataLayout.setVisibility(View.VISIBLE);

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                ArrayList<VendorTypeControls> filterVendor = new ArrayList<VendorTypeControls>();
                                for (VendorTypeControls model : VendorServiceList)
                                {
                                    if(model.getName().toLowerCase().contains(newText.toLowerCase()))
                                    {
                                        filterVendor.add(model);
                                    }

                                }
                                if(filterVendor.isEmpty())
                                {
                                    Toast.makeText(MainVendorListActivity.this, "No Such type of vendors", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    EvntAdapter.setFilteredVendors(filterVendor);
                                }
                                return true;
                            }
                        });
                    }
                    else{
                        Toast.makeText( MainVendorListActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("response_f",t.getMessage());
                    Toast.makeText(getApplicationContext(),"Check your connection and Try again",Toast.LENGTH_LONG).show();

                }
            });
        }

        Api api_deco = retrofit.create(Api.class);
        Call<ResponseVendorType> call1 = api_deco.getDecoratorType(latitude,longitude);
        if (VendName.equals("Decorator"))
        {

            call1.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    Log.i("Decorator response", String.valueOf(response.body().getData().size()));
                    if (response.isSuccessful()) {

                        for (int i = 0; i < response.body().getData().size(); i++) {

                            String ImageendPoints = response.body().getData().get(i).getImage();
                            if (ImageendPoints.contains(".jpg,")){
                                String[] images = ImageendPoints.split(",");
                                for (int k = 0; k < images.length; k++) {
                                    images[k] = images[k].replace("\\ ", "/");
                                }
                                sImage = BaseUrl +"/"+ images[0];
                            }else{
                                ImageendPoints = ImageendPoints.replace("\\ ","/");
                                sImage= BaseUrl + "/" + ImageendPoints;
                            }
                            Log.i("urlDecorator", sImage);
//                        sImage = response.body().getData().get(i).getImage();
                            Tcode = response.body().getData().get(i).getCode();
                            Tname = response.body().getData().get(i).getName();
                            distance = response.body().getData().get(i).getDistance();
                            TPrice = response.body().getData().get(i).getPrice();
                            VendorServiceList.add(new VendorTypeControls(sImage,Tcode,Tname,distance,TPrice));

                        }
                        decAdapter = new DecoratorAdapter(MainVendorListActivity.this,VendorServiceList);
                        VendorRecView.setLayoutManager(new GridLayoutManager(MainVendorListActivity.this,2));
                        VendorRecView.setAdapter(decAdapter);

                        //SkinkitWave.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        dataLayout.setVisibility(View.VISIBLE);

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                //filterList(newText);
                                ArrayList<VendorTypeControls> filterVendor = new ArrayList<VendorTypeControls>();
                                for (VendorTypeControls model : VendorServiceList)
                                {
                                    //Log.i("model value", model.getName());
                                    if(model.getName().toLowerCase().contains(newText.toLowerCase()))
                                    {
                                        filterVendor.add(model);

                                    }

                                }
                                if(filterVendor.isEmpty())
                                {
                                    Toast.makeText(MainVendorListActivity.this, "No Such type of vendors", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //adpater = new TransporterAdapter(MainVendorListActivity.this,filterVendor);
                                    decAdapter.setFilteredVendors(filterVendor);
                                    //VendorRecView.setAdapter(adapterfilter);
                                }
                                return true;
                            }
                        });
                    }
                    else{
                        Toast.makeText(MainVendorListActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("Decorator response", t.getMessage());
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

                                String ImageendPoints = response.body().getData().get(i).getImage();
                                if (ImageendPoints.contains(".jpg,")){
                                    String[] images = ImageendPoints.split(",");
                                    for (int k = 0; k < images.length; k++) {
                                        images[k] = images[k].replace("\\ ", "/");
                                    }
                                    sImage = BaseUrl + "/" + images[0];
                                }else{
                                    ImageendPoints = ImageendPoints.replace("\\ ","/");
                                    sImage= BaseUrl + "/" + ImageendPoints;
                                }
                                Log.i("urlTransporter", sImage);
                                // isse print kraker check karlena mujhe bata dena Abhi yehi kam Decorator per
                                //sImage = response.body().getData().get(i).getImage(); ye nhi
                                Tcode = response.body().getData().get(i).getCode();
                                Tname = response.body().getData().get(i).getName();
                                distance = response.body().getData().get(i).getDistance();
                                TPrice = response.body().getData().get(i).getPrice();
                                VendorServiceList.add(new VendorTypeControls(sImage,Tcode,Tname,distance,TPrice));

                            }
                            adpater = new TransporterAdapter(MainVendorListActivity.this,VendorServiceList);
                            VendorRecView.setLayoutManager(new GridLayoutManager(MainVendorListActivity.this,2));
                            VendorRecView.setAdapter(adpater);

//                        SkinkitWave.setVisibility(View.GONE);
                            shimmerFrameLayout.stopShimmerAnimation();
                            shimmerFrameLayout.setVisibility(View.INVISIBLE);
                            dataLayout.setVisibility(View.VISIBLE);

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    ArrayList<VendorTypeControls> filterVendor = new ArrayList<VendorTypeControls>();
                                    for (VendorTypeControls model : VendorServiceList)
                                    {
                                        Log.i("model value", model.getName());
                                        if(model.getName().toLowerCase().contains(newText.toLowerCase()))
                                        {
                                            filterVendor.add(model);

                                        }

                                    }
                                    if(filterVendor.isEmpty())
                                    {
                                        Toast.makeText(MainVendorListActivity.this, "No Such type of vendors", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        adpater.setFilteredVendors(filterVendor);
                                    }
                                    return true;
                                }
                            });

                        }
                        if(response.body().getData() == null)
                        {
                            Log.i("error", "No data found");
                        }
                    }
                    else{
                        Toast.makeText(MainVendorListActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("response_f","no response");
                    Toast.makeText(getApplicationContext(),"Check your connection and Try again",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void filterList(String text) {
        ArrayList<VendorTypeControls> filterVendor = new ArrayList<VendorTypeControls>();
        for (VendorTypeControls model : VendorServiceList)
        {
            if(model.getTransportationName().toLowerCase().contains(text.toLowerCase()) || model.getLocation().toLowerCase().contains(text.toLowerCase()))
            {
                filterVendor.add(model);
            }
        }
        if(filterVendor.isEmpty())
        {
            Toast.makeText(this, "No Such type of vendors", Toast.LENGTH_SHORT).show();
        }
        else{
            adpater.setFilteredVendors(filterVendor);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu,menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };
        menu.findItem(R.id.searchview).setOnActionExpandListener(onActionExpandListener);
        searchView = (SearchView) menu.findItem(R.id.searchview).getActionView();
        searchView.setQueryHint("Search here... ");
        return true;
    }
    @Override
    public void onBackPressed() {
        // Call the super method to allow the default back button behavior
        super.onBackPressed();

        // Finish the current activity
        finish();
    }

}
