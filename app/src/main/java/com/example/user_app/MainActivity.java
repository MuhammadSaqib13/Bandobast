package com.example.user_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_app.ImportantClass.LocationHelper;
import com.example.user_app.RecylerView.HallAdapter;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.ResponseVendorTypeList;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    String[] location = new String[]{"Korangi","Gulshan Iqbal","Defence","Gulistane Johar"};
    ArrayList<String> getLocation = new ArrayList<String>();
    LocationManager locationManager;
    ImageView imgMenu;
    CardView venuCard;
    FloatingActionButton imgHome;
    CardView PhotographyCard;
    CardView TransportCard;
    CardView DecorCard;
    CardView catering_Card;
    CardView ViewMore;

    double latitude, longitude;

    TextView Txt_venue;
    TextView Txt_photo;
    TextView Txt_transport;
    TextView Txt_Decor;
    TextView Txt_Catering;
    TextView Txt_Viewmore;

    ImageView ImgVendor;
    TextView txt_viewlist;
    BottomNavigationView bottomNavigationView;

    RecyclerView hallsRecView;
    HallAdapter adpater;
    String sImage,Tname,TPrice,Tlocation;
//    ArrayList<HallModel> HallList = null;
    ArrayList<VendorTypeControls> HallList = null;


    private SpinKitView SkinkitWave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationHelper locationHelper = new LocationHelper(getApplicationContext());
        LatLng latLng = locationHelper.getLatitudeLongitude();

        if (latLng != null) {
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            // Use the latitude and longitude as needed
            // Example: Log the values
            Log.i("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
        }

        Txt_venue = findViewById(R.id.txt_venue);
        Txt_photo = findViewById(R.id.txt_photo);
        Txt_transport = findViewById(R.id.txt_transport);
        Txt_Decor = findViewById(R.id.txt_decor);
        Txt_Catering = findViewById(R.id.txt_caterer);
        Txt_Viewmore = findViewById(R.id.txt_others);

        txt_viewlist = findViewById(R.id.txt_viewlist);
        txt_viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,IdeasActivity.class));
            }
        });

        Txt_Viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Vendor_Main.class));
            }
        });
        SkinkitWave = findViewById(R.id.spin_kit);
        SkinkitWave.setVisibility(View.VISIBLE);


        imgHome = findViewById(R.id.fb_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imgMenu = findViewById(R.id.img_dialog);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });

//        SkinkitWave = findViewById(R.id.spin_kit);
//        SkinkitWave.setVisibility(View.VISIBLE);


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
                        startActivity(new Intent(MainActivity.this,Vendor_Main.class));
                        break;
                    case R.id.imgIdeas:
                        startActivity(new Intent(MainActivity.this,UserPackage1.class));
                        break;
                    case R.id.img_Plan:
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        break;
                    case R.id.imgNear:
                        startActivity(new Intent(MainActivity.this,Vendor_Main.class));
                        break;
                }
                return true;
            }
        });
        //String BaseUrl = "https://89e5-111-88-194-39.eu.ngrok.io/ListOfViewService/";

        ApiBaseUrl base = new ApiBaseUrl();
        String BaseUrl = base.getBaseUrl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api =retrofit.create(Api.class);
        Call<ResponseVendorTypeList> call = api.getVendorList();

        call.enqueue(new Callback<ResponseVendorTypeList>() {
            @Override
            public void onResponse(Call<ResponseVendorTypeList> call, Response<ResponseVendorTypeList> response) {
                Log.i("list vendors", String.valueOf(response.body().getData().get(0).getName()));
                Txt_Catering.setText(response.body().getData().get(1).getName());
                Txt_Decor.setText(response.body().getData().get(2).getName());
                Txt_venue.setText(response.body().getData().get(3).getName());
                Txt_photo.setText(response.body().getData().get(4).getName());
                Txt_transport.setText(response.body().getData().get(5).getName());
            }

            @Override
            public void onFailure(Call<ResponseVendorTypeList> call, Throwable t) {
               Log.i("list vendors", t.getMessage());


            }
        });



        hallsRecView = findViewById(R.id.venue_scroll);
        HallList = new ArrayList<VendorTypeControls>();
        getEventPlacer();
//        HallList.add(new VendorTypeControls("Cornish Marque","Nasir Jump","Rs. 120,000","Image"));
//        HallList.add(new VendorTypeControls("Oasis Banquet","Korangi Crossing","Rs. 130,000","Image"));
//        HallList.add(new VendorTypeControls("Cornish Marque","Nasir Jump","Rs. 120,000","Image"));
//        HallList.add(new VendorTypeControls("Oasis Banquet","Korangi Crossing","Rs. 130,000","Image"));




        //spinner = findViewById(R.id.loc);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.locationlists,location);
        //adapter.setDropDownViewResource(R.layout.locationlists);


        //spinner.setAdapter(adapter);


        venuCard = findViewById(R.id.VenueCard);
        PhotographyCard = findViewById(R.id.PhotographerCard);
        TransportCard = findViewById(R.id.TransporterCard);

        DecorCard = findViewById(R.id.DecoratorCard);
        catering_Card = findViewById(R.id.CatererCard);
        ViewMore = findViewById(R.id.OthersCard);

        venuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_venue.getText().toString());
                startActivity(intent);

            }
        });

        PhotographyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_photo.getText().toString());
                startActivity(intent);

            }
        });

        TransportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_transport.getText().toString());
                startActivity(intent);

            }
        });
        DecorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_Decor.getText().toString());
                startActivity(intent);

            }
        });

        catering_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_Catering.getText().toString());
                startActivity(intent);

            }
        });
        ViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Vendor_Main.class);
                startActivity(intent);

            }
        });

    }

    private void showBottomDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.layout_bottom_sheet,
                        (LinearLayout) findViewById(R.id.bottomSheetContainer)
                );

        bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Share...", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
    public void getEventPlacer(){
        ApiBaseUrl Base = new ApiBaseUrl();
        String BaseUrl = Base.getBaseUrl();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api =retrofit.create(Api.class);
        Call<ResponseVendorType> call = api.getDecoratorType(latitude,longitude);
        call.enqueue(new Callback<ResponseVendorType>() {
            @Override
            public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                for (int i = 0; i < response.body().getData().size(); i++) {
                    sImage = response.body().getData().get(i).getImage();
                    Tname = response.body().getData().get(i).getName();
                    Tlocation = response.body().getData().get(i).getPrice();
                    TPrice = response.body().getData().get(i).getPrice();

                    HallList.add(new VendorTypeControls(Tname,TPrice,Tlocation,sImage));

                }
                adpater = new HallAdapter(MainActivity.this,HallList);
                hallsRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                hallsRecView.setAdapter(adpater);
                SkinkitWave.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                Log.i("decorator",t.getMessage());
;            }
        });
    }
}