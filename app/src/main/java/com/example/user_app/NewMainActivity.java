package com.example.user_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_app.ImportantClass.LocationHelper;
import com.example.user_app.RecylerView.HallAdapter;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
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
import android.Manifest;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewMainActivity extends AppCompatActivity {
    FloatingActionButton imgHome;
    BottomNavigationView bottomNavigationView;
    ImageView imgMenu;
    RecyclerView hallsRecView;
    HallAdapter adpater;
    String sImage,TCode,Tname,TPrice,Tlocation;
    //    ArrayList<HallModel> HallList = null;
    ArrayList<VendorTypeControls> HallList = null;

    CardView Vend_card, Recom_Card, Ideas_Card, Event_Card;
    CardView eventGalleryCard, photoGalleryCard,transportGalleryCard,decorGalleryCard ,caterGalleryCard ,ViewMore;

    TextView Txt_venue, Txt_photo, Txt_transport, Txt_Decor,Txt_Catering, Txt_Viewmore;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;

    private SpinKitView SkinkitWave;

    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        if (!isConnected()) {
            // No Internet connection is available
            // Perform your desired tasks here
            showNoInternetDialog();
        } else {
            // Internet connection is available
//            showNoInternetDialog();
        }

        Txt_venue = findViewById(R.id.txt_evnt);
        Txt_photo = findViewById(R.id.txt_photo);
        Txt_transport = findViewById(R.id.txt_transport);
        Txt_Decor = findViewById(R.id.txt_decor);
        Txt_Catering = findViewById(R.id.txt_caterer);
        Txt_Viewmore = findViewById(R.id.txt_others);

        getVendors();

        eventGalleryCard = findViewById(R.id.eventCard);
        photoGalleryCard = findViewById(R.id.PhotographerCard);
        transportGalleryCard = findViewById(R.id.TransporterCard);
        decorGalleryCard = findViewById(R.id.DecoratorCard);
        caterGalleryCard = findViewById(R.id.CatererCard);
        ViewMore = findViewById(R.id.OthersCard);

        eventGalleryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_venue.getText().toString());
                startActivity(intent);

            }
        });

        photoGalleryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_photo.getText().toString());
                startActivity(intent);

            }
        });

        transportGalleryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_transport.getText().toString());
                startActivity(intent);

            }
        });
        decorGalleryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_Decor.getText().toString());
                startActivity(intent);

            }
        });

        caterGalleryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,GalleryActivity.class);
                intent.putExtra("Vendor Name",Txt_Catering.getText().toString());
                startActivity(intent);

            }
        });
        ViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,Vendor_Main.class);
                startActivity(intent);

            }
        });


//        SkinkitWave = findViewById(R.id.spin_kit);
//        SkinkitWave.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isUserAlreadyUsedApp", true);
        editor.apply();

        Vend_card = findViewById(R.id.VendorsCard);
        Recom_Card = findViewById(R.id.RecommendCard);
        Ideas_Card = findViewById(R.id.IdeasCard);
        Event_Card = findViewById(R.id.VenueCard);

        Event_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMainActivity.this,UserPackage2.class));

            }
        });


        Vend_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMainActivity.this,Vendor_Main.class));

            }
        });

        Ideas_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this, GalleryActivity.class);
                intent.putExtra("Vendor Name","Photographer");
                startActivity(intent);

            }
        });

        Recom_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMainActivity.this,Vendor_Main.class));


            }
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permissions already granted, proceed with location operations
            LocationHelper locationHelper = new LocationHelper(getApplicationContext());
            LatLng latLng = locationHelper.getLatitudeLongitude();

            if (latLng != null) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                // Use the latitude and longitude as needed
                // Example: Log the values
                Log.i("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
            }
            // ...
        } else {
            // Permissions not granted, request them from the user
            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);
        }



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
                        startActivity(new Intent(NewMainActivity.this,Vendor_Main.class));
                        break;
                    case R.id.imgIdeas:
                        startActivity(new Intent(NewMainActivity.this,UserPackage2.class));
                        break;
                    case R.id.img_Plan:
                        startActivity(new Intent(NewMainActivity.this,UserPackage2.class));
                        break;
                    case R.id.imgNear:
                        startActivity(new Intent(NewMainActivity.this,Vendor_Main.class));
                        break;
                }
                return true;
            }
        });
//        hallsRecView = findViewById(R.id.venue_scroll);
//        HallList = new ArrayList<VendorTypeControls>();
//        getEventPlacer();

    }

    public void getVendors()
    {
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
                if(response.isSuccessful()) {
                    Log.i("list vendors", String.valueOf(response.body().getData().get(0).getName()));
                    Txt_Catering.setText(response.body().getData().get(0).getName());
                    Txt_Decor.setText(response.body().getData().get(1).getName());
                    Txt_venue.setText(response.body().getData().get(2).getName());
                    Txt_photo.setText(response.body().getData().get(3).getName());
                    Txt_transport.setText(response.body().getData().get(4).getName());
                }
                else
                {
                    Toast.makeText(NewMainActivity.this,"Kindly wait server is down",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseVendorTypeList> call, Throwable t) {
                Log.i("list vendors", t.getMessage());
                Toast.makeText(NewMainActivity.this,"Kindly wait server is down",Toast.LENGTH_LONG).show();


            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Internet connection is required to use this app.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showEnableWifiDialog();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showEnableWifiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable Wi-Fi");
        builder.setMessage("Would you like to enable Wi-Fi now?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enableWifi();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //finish(); // Close the app or handle it as per your requirement
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void enableWifi() {
        // Open Wi-Fi settings for the user to enable it
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Check if the permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, proceed with location operations
                LocationHelper locationHelper = new LocationHelper(getApplicationContext());
                LatLng latLng = locationHelper.getLatitudeLongitude();

                if (latLng != null) {
                    latitude = latLng.latitude;
                    longitude = latLng.longitude;

                    // Use the latitude and longitude as needed
                    // Example: Log the values
                    Log.i("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                }
                // ...
            } else {
                // Permissions denied, handle accordingly (e.g., show a message)
                showLocationPermissionAlert();

                // ...
            }
        }
    }
    private void showLocationPermissionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Location Permission")
                .setMessage("You can't get nearest vendors if you don't allow the location. Do you agree with it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NewMainActivity.this, "Location permission not granted.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(NewMainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                LOCATION_PERMISSION_REQUEST_CODE);
                    }
                })
                .setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setTextColor(0xFF00FF00); // Green color (ARGB format)

                Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                negativeButton.setTextColor(0xFFFF0000); // Red color (ARGB format)
            }
        });

        alertDialog.show();
    }
    private void showBottomDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,R.style.BottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.layout_bottom_sheet,
                        (LinearLayout) findViewById(R.id.bottomSheetContainer)
                );

        bottomSheetView.findViewById(R.id.buttonShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewMainActivity.this, "Share...", Toast.LENGTH_SHORT).show();
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

//    public void getEventPlacer(){
//        ApiBaseUrl Base = new ApiBaseUrl();
//        String BaseUrl = Base.getBaseUrl();
//        if (BaseUrl.equals("")) {
//            Toast.makeText(this, "API URL is invalid",Toast.LENGTH_LONG).show();
//        }
//        else {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(BaseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            Api api = retrofit.create(Api.class);
//            Call<ResponseVendorType> call = api.getDecoratorType(latitude, longitude);
//            call.enqueue(new Callback<ResponseVendorType>() {
//                @Override
//                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
//                    if (response.isSuccessful()) {
//                        for (int i = 0; i < response.body().getData().size(); i++) {
//
//                            String ImageendPoints = response.body().getData().get(i).getImage();
//                            if (ImageendPoints.contains(".jpg,")) {
//                                String[] images = ImageendPoints.split(",");
//                                sImage = images[0];
//                            } else {
//                                sImage = BaseUrl + "/" + ImageendPoints;
//                            }
//
//                            //sImage = response.body().getData().get(i).getImage();
//                            TCode = response.body().getData().get(i).getCode();
//                            Tname = response.body().getData().get(i).getName();
//                            Tlocation = response.body().getData().get(i).getPrice();
//                            TPrice = response.body().getData().get(i).getPrice();
//
//                            HallList.add(new VendorTypeControls(TCode, Tname, TPrice, Tlocation, sImage));
//
//                        }
//                        adpater = new HallAdapter(NewMainActivity.this, HallList);
//                        hallsRecView.setLayoutManager(new LinearLayoutManager(NewMainActivity.this, LinearLayoutManager.HORIZONTAL, false));
//                        hallsRecView.setAdapter(adpater);
//                        SkinkitWave.setVisibility(View.GONE);
//
//                    }
//                    else{
//                        Toast.makeText(NewMainActivity.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
//                    Log.i("decorator", t.getMessage());
//                }
//            });
//        }
//    }
}