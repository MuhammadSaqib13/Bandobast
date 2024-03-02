package com.example.user_app;

import android.app.Dialog;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.user_app.ImportantClass.ImagePagerAdapter;
import com.example.user_app.ImportantClass.LocationHelper;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;
import android.Manifest;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleVendor extends AppCompatActivity{
    TextView HeaderText, Location, Price, Vehicletyp, Desc, EventTxt, EventTypetxt,  ServType, TextType, TextServ, ViewMap, Share, ImgTxt;
    ImageView imgView, img1,img2,img3,img4;
    String sImage, sPhone;
    String[] images = null;
    LinearLayout img34_layout, img12_layout;
    LinearLayout dataLayout;
    Dialog dialog;
    ShimmerFrameLayout shimmerFrameLayout;
    LocationListener locationTrack;
    private static double Lat = 0.0;
    private static double Long = 0.0;

//
    private static final int REQUEST_CALL_PERMISSION = 1;
    CardView call_btn;
    double latitude, longitude;
    private ActivityResultLauncher<Intent> mapLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_vendor);

        mapLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // No result handling is required in this case
                }
        );




        final ImagePopup imagePopup = new ImagePopup(SingleVendor.this);

        imagePopup.setWindowHeight(600); // Optional
        imagePopup.setWindowWidth(1000); // Optional
        imagePopup.setBackgroundColor(Color.parseColor("#88000000"));  // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(false);  // Optional
        imagePopup.setImageOnClickClose(false);// Optional
//
        Location = findViewById(R.id.location);
        Price = findViewById(R.id.price);
        Vehicletyp = findViewById(R.id.veh_category);
        ServType = findViewById(R.id.serv_category);
        Desc = findViewById(R.id.description);
        dataLayout = findViewById(R.id.data_view);
        dataLayout.setVisibility(View.GONE);
        shimmerFrameLayout= findViewById(R.id.shimmer_view);
        shimmerFrameLayout.startShimmerAnimation();
        TextType = findViewById(R.id.txt_type);
        TextServ = findViewById(R.id.txt_service);
        ViewMap = findViewById(R.id.vw_map);
        Share = findViewById(R.id.txt_share);
        ImgTxt = findViewById(R.id.txt_noImg);
        EventTxt = findViewById(R.id.txt_events);
        EventTypetxt = findViewById(R.id.event_txt);


        img1= findViewById(R.id.Port_img1);
        img2 = findViewById(R.id.Port_img2);
        img3 = findViewById(R.id.Port_img3);
        img4 = findViewById(R.id.Port_img4);
        img34_layout = findViewById(R.id.img3_img4);
        img12_layout = findViewById(R.id.img1_img2);







// Inside the onCreate() method or any other relevant method
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            // Permission is already granted, proceed with call functionality
            setupCallButton("+923363250316");
        }




        HeaderText = findViewById(R.id.txt_full_name);
        imgView = findViewById(R.id.banner_img);

        Intent intent = getIntent();
        String VndCode = intent.getStringExtra("Shop Code");
        String VndType = intent.getStringExtra("VendorType");
        boolean fromPackage = intent.getBooleanExtra("FromPackage",false);
        String photographer = intent.getStringExtra("Photographer");
        String banquet = intent.getStringExtra("Banquet");
        String caterer = intent.getStringExtra("Caterer");

        //String VendName = intent.getStringExtra("Vendor Name");
        //HeaderText.setText(VndName);
        //String BaseUrl = "https://89e5-111-88-194-39.eu.ngrok.io/";

        LocationHelper locationHelper = new LocationHelper(getApplicationContext());
        LatLng latLng = locationHelper.getLatitudeLongitude();

        if (latLng != null) {
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            // Use the latitude and longitude as needed
            // Example: Log the values
            Log.i("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
        }

        ApiBaseUrl apiBaseUrl = new ApiBaseUrl();
        String BaseUrl = apiBaseUrl.getBaseUrl();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        Api api =retrofit.create(Api.class);

        if(VndType.equals("Transporter"))
        {
            Log.i("inTransporter","Single Transporter");
            Call<ResponseVendorType> call = api.getVendorType(latitude,longitude);
            call.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    // HeaderText.setText(response.body().getData().get(0).getTransportationName());
                    if (response.isSuccessful()) {
                        Log.i("Response Successfull","Single Transporter");
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            Log.i("For Loop","Single Transporter");
                            if(VndCode.equals(response.body().getData().get(i).getCode()))
                            {
                                Log.i("ifCondition","Single Transporter"+response.body().getData().get(i).getCode());
                                HeaderText.setText(response.body().getData().get(i).getName());
                                Location.setText(response.body().getData().get(i).getLocation());
                                Price.setText("PKR: "+response.body().getData().get(i).getPrice()+" per Km");
                                Vehicletyp.setText(response.body().getData().get(i).getVehicleType());
                                ServType.setText(response.body().getData().get(i).getServiceType());
                                Desc.setText(response.body().getData().get(i).getDescription());

                                ViewMap.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //String location = "New York City"; // Specify the desired location

                                        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+latitude+ "," +longitude+ "&daddr="+24.812923+ ","+ 67.108284);
                                        int zoomLevel = 20; // Adjust the zoom level as needed

//                                        Uri gmmIntentUri = Uri.parse("geo:24.812923,67.108284?z=" + zoomLevel + "&q=24.812923,67.108284");
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name for Google Maps

                                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                            mapLauncher.launch(mapIntent);
                                        } else {
                                            Toast.makeText(SingleVendor.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

//                                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ViewMap.getLayoutParams();
//                                int newMargin = -50; // Set your desired margin value here
//                                layoutParams.leftMargin = newMargin;
//                                ViewMap.setLayoutParams(layoutParams);
//
//                                ViewGroup.MarginLayoutParams sharelayoutParams = (ViewGroup.MarginLayoutParams) Share.getLayoutParams();
//                                int shareMargin = 50; // Set your desired margin value here
//                                sharelayoutParams.leftMargin = shareMargin;
//                                Share.setLayoutParams(sharelayoutParams);
                                //sImage = response.body().getData().get(i).getImage();

                                String ImageendPoints = response.body().getData().get(i).getImage();
                                if (ImageendPoints.contains(".jpg,")){
                                    images = ImageendPoints.split(",");
                                    sImage = BaseUrl +"/"+ images[0];
                                    Picasso.get().load(sImage).into(imgView);
                                    for (int j = 1; j < images.length; j++) {
                                        if (images[j] == null) {
                                            img34_layout.setVisibility(View.GONE);
                                            break;
                                        }
                                        else {
                                            Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                            Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                            img34_layout.setVisibility(View.GONE);

                                        }
                                    }
                                }
                                else{
                                    img12_layout.setVisibility(View.GONE);
                                    img34_layout.setVisibility(View.GONE);
                                    ImgTxt.setVisibility(View.VISIBLE);
                                    sImage= BaseUrl + "/" + ImageendPoints;
                                    Picasso.get().load(sImage).into(imgView);
                                }
//                            byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
//                            InputStream inputStream  = new ByteArrayInputStream(imagebytes);
//                            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);




                            }
                        }
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        dataLayout.setVisibility(View.VISIBLE);

                        Log.i("sImage", sImage);

                        imgView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imagePopup.initiatePopupWithPicasso(sImage);
                                imagePopup.viewPopup();
                            }
                        });

                        img1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //images[1] = images[1].replace("\\", "/");
                                //openLightbox(images,1);
                                imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[0]);
                                imagePopup.viewPopup();

//                                Log.i("imageUrl",BaseUrl +"/"+ images[1]);
                            }
                        });
                        img2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[1]);
                                imagePopup.viewPopup();
                                //openLightbox(images,2);
                            }
                        });

                    }
                    else{
                        Toast.makeText(SingleVendor.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("Failed","Single transporter Failed");

                }
            });

        }
        if (VndType.equals("Decorator")) {
            Log.i("inDecorator","Single Decorator");
            Call<ResponseVendorType> call = api.getDecoratorType(latitude,longitude);
            call.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    // HeaderText.setText(response.body().getData().get(0).getTransportationName());
                    if (response.isSuccessful()) {

                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if(VndCode.equals(response.body().getData().get(i).getCode()))
                            {
                                HeaderText.setText(response.body().getData().get(i).getName());
                                TextServ.setText("Event Type");
                                TextType.setText("Setup Type");
//                                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ViewMap.getLayoutParams();
//                                int newMargin = 100; // Set your desired margin value here
//                                layoutParams.leftMargin = newMargin;
//                                ViewMap.setLayoutParams(layoutParams);

                                ViewMap.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //String location = "New York City"; // Specify the desired location

                                        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+latitude+ "," +longitude+ "&daddr="+24.812923+ ","+ 67.108284);
                                        int zoomLevel = 20; // Adjust the zoom level as needed

//                                        Uri gmmIntentUri = Uri.parse("geo:24.812923,67.108284?z=" + zoomLevel + "&q=24.812923,67.108284");
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name for Google Maps

                                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                            mapLauncher.launch(mapIntent);
                                        } else {
                                            Toast.makeText(SingleVendor.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

//                                ViewGroup.MarginLayoutParams sharelayoutParams = (ViewGroup.MarginLayoutParams) Share.getLayoutParams();
//                                int shareMargin = 60; // Set your desired margin value here
//                                sharelayoutParams.leftMargin = shareMargin;
//                                Share.setLayoutParams(sharelayoutParams);

                                Location.setText(response.body().getData().get(i).getLocation());
                                Price.setText("PKR: "+response.body().getData().get(i).getPrice());
                                Vehicletyp.setText(response.body().getData().get(i).getSetupRequirement());
                                ServType.setText(response.body().getData().get(i).getEvent());
                                Desc.setText(response.body().getData().get(i).getDescription());

                                Log.i("HeaderTextDecorator",HeaderText.getText().toString());

                                String ImageendPoints = response.body().getData().get(i).getImage();
                                if (ImageendPoints.contains(".jpg,")){
                                    images = ImageendPoints.split(",");
                                    sImage = BaseUrl +"/"+ images[0];
                                    Picasso.get().load(sImage).into(imgView);
                                    for (int j = 1; j < images.length; j++) {
                                        if (images[j] == null) {
                                            img34_layout.setVisibility(View.GONE);
                                            break;
                                        }
                                        else {
                                            Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                            Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);

                                        }
                                    }
                                }else{
                                    img12_layout.setVisibility(View.GONE);
                                    img34_layout.setVisibility(View.GONE);
                                    ImgTxt.setVisibility(View.VISIBLE);
                                    sImage= BaseUrl + "/" + ImageendPoints;
                                    Picasso.get().load(sImage).into(imgView);
                                }

//                            sImage = response.body().getData().get(i).getImage();


//                            byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
//                            InputStream inputStream  = new ByteArrayInputStream(imagebytes);
//                            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);



//                            imgView.setImageBitmap(bitmap);
//                            Picasso.get().load(sImage).into(imgView);


                            }
                            else{
                                Log.i("else","No Record Found for This Vendor Code"+VndCode);
                            }
                        }
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.INVISIBLE);
                        dataLayout.setVisibility(View.VISIBLE);

                        imgView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imagePopup.initiatePopupWithPicasso(sImage);
                                imagePopup.viewPopup();
                            }
                        });

                    }
                    else{
                        Toast.makeText(SingleVendor.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Toast.makeText(SingleVendor.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
        }
        if (VndType.equals("Caterer")) {
            Log.i("inCaterer","Single Caterer");

            if(fromPackage)
            {
                try {
                    JSONObject catererObject = new JSONObject(caterer);
                    HeaderText.setText(catererObject.get("restaurant").toString());
                    TextServ.setText("Delivery Charges ");
                    TextType.setText("Menu Name ");
                    EventTxt.setVisibility(View.VISIBLE);
                    EventTypetxt.setText(catererObject.get("eventId").toString());
                    EventTypetxt.setVisibility(View.VISIBLE);

                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ViewMap.getLayoutParams();
                    int newMargin = 60; // Set your desired margin value here
                    layoutParams.leftMargin = newMargin;
                    ViewMap.setLayoutParams(layoutParams);

                    ViewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String location = "New York City"; // Specify the desired location

                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+latitude+ "," +longitude+ "&daddr="+24.812923+ ","+ 67.108284);
                            int zoomLevel = 20; // Adjust the zoom level as needed

//                            Uri gmmIntentUri = Uri.parse("geo:24.812923,67.108284?z=" + zoomLevel + "&q=24.812923,67.108284");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name for Google Maps

                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                mapLauncher.launch(mapIntent);
                            } else {
                                Toast.makeText(SingleVendor.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



//                    ViewGroup.MarginLayoutParams sharelayoutParams = (ViewGroup.MarginLayoutParams) Share.getLayoutParams();
//                    int shareMargin = 40; // Set your desired margin value here
//                    sharelayoutParams.leftMargin = shareMargin;
//                    Share.setLayoutParams(sharelayoutParams);

                    Location.setText(catererObject.get("location").toString());
                    Price.setText("PKR: "+String.valueOf(catererObject.getInt("price"))+" "+catererObject.get("priceCategoryId"));
                    Vehicletyp.setText(catererObject.get("menuId").toString());
                    ServType.setText(String.valueOf(catererObject.getInt("deliveryCharges")));
                    Desc.setText(catererObject.get("description").toString());

                    setupCallButton(catererObject.get("contact").toString());

                    String ImageendPoints = catererObject.get("image").toString();
                    if (ImageendPoints.contains(".jpg,")){
                        images = ImageendPoints.split(",");
                        for (int k = 0; k < images.length; k++) {
                            images[k] = images[k].replace("\\\\", "/");
                        }
                        sImage = BaseUrl +"/"+ images[0];
                        Picasso.get().load(sImage).into(imgView);
                        for (int j = 1; j < images.length; j++) {
                            if(images.length <= 2){
                                if (images[j] == null) {
                                    img34_layout.setVisibility(View.GONE);
                                    break;
                                }
                                else {
                                    Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                    Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                    img34_layout.setVisibility(View.GONE);
                                }
                            }
                            else{
                                Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                Picasso.get().load(BaseUrl + "/" + images[2]).into(img3);
                                Picasso.get().load(BaseUrl + "/" + images[3]).into(img4);

                            }
                        }
                    }else{
                        img12_layout.setVisibility(View.GONE);
                        img34_layout.setVisibility(View.GONE);
                        ImgTxt.setVisibility(View.VISIBLE);
                        sImage= BaseUrl + "/" + ImageendPoints;
                        Picasso.get().load(sImage).into(imgView);
                    }

                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    dataLayout.setVisibility(View.VISIBLE);
                    imgView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.initiatePopupWithPicasso(sImage);
                            imagePopup.viewPopup();
                        }
                    });

                    img1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[0]);
                            imagePopup.viewPopup();
                        }
                    });
                    img2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[1]);
                            imagePopup.viewPopup();
                        }
                    });





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else{

                Call<ResponseVendorType> call = api.getCatererType(latitude,longitude);
                call.enqueue(new Callback<ResponseVendorType>() {
                    @Override
                    public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                        // HeaderText.setText(response.body().getData().get(0).getTransportationName());
                        if (response.isSuccessful()) {

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if(VndCode.equals(response.body().getData().get(i).getCode()))
                                {
                                    HeaderText.setText(response.body().getData().get(i).getName());
                                    TextServ.setText("Event Type");
                                    TextType.setText("Menu");

                                    Location.setText(response.body().getData().get(i).getLocation());
                                    Price.setText("PKR: "+response.body().getData().get(i).getPrice()+ " "+response.body().getData().get(i).getPriceCategory());
                                    Vehicletyp.setText(response.body().getData().get(i).getMenu());
                                    ServType.setText(response.body().getData().get(i).getEvent());
                                    Desc.setText(response.body().getData().get(i).getDescription());

                                    Log.i("HeaderTextCateror",HeaderText.getText().toString());

                                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ViewMap.getLayoutParams();
                                    int newMargin = 60; // Set your desired margin value here
                                    layoutParams.leftMargin = newMargin;
                                    ViewMap.setLayoutParams(layoutParams);


                                    ViewMap.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //String location = "New York City"; // Specify the desired location

                                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+latitude+ "," +longitude+ "&daddr="+24.812923+ ","+ 67.108284);
                                            int zoomLevel = 20; // Adjust the zoom level as needed

//                                            Uri gmmIntentUri = Uri.parse("geo:24.812923,67.108284?z=" + zoomLevel + "&q=24.812923,67.108284");
                                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                            mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name for Google Maps

                                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                mapLauncher.launch(mapIntent);
                                            } else {
                                                Toast.makeText(SingleVendor.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

//                                    ViewGroup.MarginLayoutParams sharelayoutParams = (ViewGroup.MarginLayoutParams) Share.getLayoutParams();
//                                    int shareMargin = 40; // Set your desired margin value here
//                                    sharelayoutParams.leftMargin = shareMargin;
//                                    Share.setLayoutParams(sharelayoutParams);

                                    String ImageendPoints = response.body().getData().get(i).getImage();
                                    if (ImageendPoints.contains(".jpg,")){
                                        images = ImageendPoints.split(",");
                                        sImage = BaseUrl +"/"+ images[0];
                                        Picasso.get().load(sImage).into(imgView);
                                        for (int j = 1; j < images.length; j++) {
                                            if (images[j] == null) {
                                                img34_layout.setVisibility(View.GONE);
                                                break;
                                            }
                                            else {
                                                Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                                Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                                img34_layout.setVisibility(View.GONE);

                                            }
                                        }
                                    }else{
                                        img12_layout.setVisibility(View.GONE);
                                        img34_layout.setVisibility(View.GONE);
                                        ImgTxt.setVisibility(View.VISIBLE);
                                        sImage= BaseUrl + "/" + ImageendPoints;
                                        Picasso.get().load(sImage).into(imgView);
                                    }
//                            sImage = response.body().getData().get(i).getImage();

//                            byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
//                            InputStream inputStream  = new ByteArrayInputStream(imagebytes);
//                            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//
//
//                            imgView.setImageBitmap(bitmap);


                                }
                                else{
                                    Log.i("else","No Record Found for This Vendor Code"+VndCode);
                                }
                            }
                            shimmerFrameLayout.stopShimmerAnimation();
                            shimmerFrameLayout.setVisibility(View.INVISIBLE);
                            dataLayout.setVisibility(View.VISIBLE);

                            imgView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imagePopup.initiatePopupWithPicasso(sImage);
                                    imagePopup.viewPopup();
                                }
                            });

                            img1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[0]);
                                    imagePopup.viewPopup();
                                }
                            });
                            img2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[1]);
                                    imagePopup.viewPopup();
                                }
                            });

                        }
                        else{
                            Toast.makeText(SingleVendor.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                        Toast.makeText(SingleVendor.this,t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }

        }
        if (VndType.equals("EventPlacer")) {
            Log.i("inEventPlacer","Single EventPlacer");
            if(fromPackage)
            {
                try {
                    JSONObject banquetObject = new JSONObject(banquet);
                    HeaderText.setText(banquetObject.get("banquet").toString());
                    TextServ.setText("Timing ");
                    TextType.setText("Person Range ");
                    EventTxt.setVisibility(View.VISIBLE);
                    EventTypetxt.setText(banquetObject.get("eventId").toString());
                    EventTypetxt.setVisibility(View.VISIBLE);

//                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ViewMap.getLayoutParams();
//                    int newMargin = 100; // Set your desired margin value here
//                    layoutParams.leftMargin = newMargin;
//                    ViewMap.setLayoutParams(layoutParams);


                    ViewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String location = "New York City"; // Specify the desired location

                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+latitude+ "," +longitude+ "&daddr="+24.812923+ ","+ 67.108284);
                            int zoomLevel = 20; // Adjust the zoom level as needed

//                            Uri gmmIntentUri = Uri.parse("geo:24.812923,67.108284?z=" + zoomLevel + "&q=24.812923,67.108284");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name for Google Maps

                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                mapLauncher.launch(mapIntent);
                            } else {
                                Toast.makeText(SingleVendor.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

//                    ViewGroup.MarginLayoutParams sharelayoutParams = (ViewGroup.MarginLayoutParams) Share.getLayoutParams();
//                    int shareMargin = 60; // Set your desired margin value here
//                    sharelayoutParams.leftMargin = shareMargin;
//                    Share.setLayoutParams(sharelayoutParams);

                    Location.setText(banquetObject.get("location").toString());
                    Price.setText("PKR: "+String.valueOf(banquetObject.getInt("price")));
                    Vehicletyp.setText(banquetObject.get("personRange").toString());
                    ServType.setText(banquetObject.get("shiftId").toString());
                    Desc.setText(banquetObject.get("description").toString());

                    setupCallButton(banquetObject.get("contact").toString());

                    String ImageendPoints = banquetObject.get("image").toString();
                    if (ImageendPoints.contains(".jpg,")){
                        images = ImageendPoints.split(",");
                        for (int k = 0; k < images.length; k++) {
                            images[k] = images[k].replace("\\\\", "/");
                        }
                        sImage = BaseUrl +"/"+ images[0];
                        Picasso.get().load(sImage).into(imgView);
                        for (int j = 1; j < images.length; j++) {
                            if(images.length <= 2){
                                if (images[j] == null) {
                                    img34_layout.setVisibility(View.GONE);
                                    break;
                                }
                                else {
                                    Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                    Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                    img34_layout.setVisibility(View.GONE);
                                }
                            }
                            else{
                                Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                Picasso.get().load(BaseUrl + "/" + images[2]).into(img3);
                                Picasso.get().load(BaseUrl + "/" + images[3]).into(img4);

                            }
                        }
                    }else{
                        img12_layout.setVisibility(View.GONE);
                        img34_layout.setVisibility(View.GONE);
                        ImgTxt.setVisibility(View.VISIBLE);
                        sImage= BaseUrl + "/" + ImageendPoints;
                        Picasso.get().load(sImage).into(imgView);
                    }

                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    dataLayout.setVisibility(View.VISIBLE);
                    imgView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.initiatePopupWithPicasso(sImage);
                            imagePopup.viewPopup();
                        }
                    });

                    img1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[0]);
                            imagePopup.viewPopup();
                        }
                    });
                    img2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[1]);
                            imagePopup.viewPopup();
                        }
                    });





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            else{

                Call<ResponseVendorType> call = api.getEventPlacerType(latitude,longitude);
                call.enqueue(new Callback<ResponseVendorType>() {
                    @Override
                    public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                        // HeaderText.setText(response.body().getData().get(0).getTransportationName());
                        if (response.isSuccessful()) {

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if(VndCode.equals(response.body().getData().get(i).getCode()))
                                {
                                    HeaderText.setText(response.body().getData().get(i).getName());
                                    TextServ.setText("Event Type");
                                    TextType.setText("Shift");

//                                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ViewMap.getLayoutParams();
//                                    int newMargin = 100; // Set your desired margin value here
//                                    layoutParams.leftMargin = newMargin;
//                                    ViewMap.setLayoutParams(layoutParams);

                                    ViewMap.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //String location = "New York City"; // Specify the desired location

                                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+latitude+ "," +longitude+ "&daddr="+24.812923+ ","+ 67.108284);
                                            int zoomLevel = 20; // Adjust the zoom level as needed

//                                            Uri gmmIntentUri = Uri.parse("geo:24.812923,67.108284?z=" + zoomLevel + "&q=24.812923,67.108284");
                                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                            mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name for Google Maps

                                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                mapLauncher.launch(mapIntent);
                                            } else {
                                                Toast.makeText(SingleVendor.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

//                                    ViewGroup.MarginLayoutParams sharelayoutParams = (ViewGroup.MarginLayoutParams) Share.getLayoutParams();
//                                    int shareMargin = 60; // Set your desired margin value here
//                                    sharelayoutParams.leftMargin = shareMargin;
//                                    Share.setLayoutParams(sharelayoutParams);

                                    Location.setText(response.body().getData().get(i).getLocation());
                                    Price.setText("PKR: "+response.body().getData().get(i).getPrice());
                                    Vehicletyp.setText(response.body().getData().get(i).getShift());
                                    ServType.setText(response.body().getData().get(i).getEvent());
                                    Desc.setText(response.body().getData().get(i).getDescription());

                                    Log.i("HeaderTextEventPlacer",HeaderText.getText().toString());

                                    String ImageendPoints = response.body().getData().get(i).getImage();
                                    if (ImageendPoints.contains(".jpg,")){
                                        images = ImageendPoints.split(",");
                                        for (int k = 0; k < images.length; k++) {
                                            images[k] = images[k].replace("\\ ", "/");
                                        }
                                        sImage = BaseUrl +"/"+ images[0];
                                        Picasso.get().load(sImage).into(imgView);
                                        Log.i("EventPlacerUrl",sImage);
                                        for (int j = 1; j <= images.length; j++) {
                                            if(images.length <= 2) {
                                                if (images[j] == null) {
                                                    img34_layout.setVisibility(View.GONE);
                                                    break;
                                                } else {
                                                    Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                                    Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                                    img34_layout.setVisibility(View.GONE);

                                                }
                                            }
                                            else{
                                                Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                                Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                                Picasso.get().load(BaseUrl + "/" + images[2]).into(img3);
                                                Picasso.get().load(BaseUrl + "/" + images[3]).into(img4);
                                            }
                                        }
                                    }else{
                                        img12_layout.setVisibility(View.GONE);
                                        img34_layout.setVisibility(View.GONE);
                                        sImage= BaseUrl + "/" + ImageendPoints;
                                        ImgTxt.setVisibility(View.VISIBLE);
                                        Picasso.get().load(sImage).into(imgView);
                                    }
//                            sImage = response.body().getData().get(i).getImage();
//
//                            byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
//                            InputStream inputStream  = new ByteArrayInputStream(imagebytes);
//                            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);


//                            imgView.setImageBitmap(bitmap);



                                }
                                else{
                                    Log.i("else","No Record Found for This Vendor Code"+VndCode);
                                }
                            }
                            shimmerFrameLayout.stopShimmerAnimation();
                            shimmerFrameLayout.setVisibility(View.INVISIBLE);
                            dataLayout.setVisibility(View.VISIBLE);
//                        for (int i = 0; i < images.length; i++) {
//                            images[i] = images[i].replace("\\", "/");
//                        }
//                        //Picasso.setSingletonInstance(new Picasso.Builder(SingleVendor.this).build());
//                        imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[0]);

                            imgView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imagePopup.initiatePopupWithPicasso(sImage);
                                    imagePopup.viewPopup();
                                }
                            });

                            img1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //images[1] = images[1].replace("\\", "/");
                                    //openLightbox(images,1);
                                    imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[0]);
                                    imagePopup.viewPopup();

                                    Log.i("imageUrl",BaseUrl +"/"+ images[1]);
                                }
                            });
                            img2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[1]);
                                    imagePopup.viewPopup();
                                    //openLightbox(images,2);
                                }
                            });
                            img3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //openLightbox(images,3);
                                    imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[2]);
                                    imagePopup.viewPopup();
                                }
                            });
                            img4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //openLightbox(images,4);
                                    imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[3]);
                                    imagePopup.viewPopup();
                                }
                            });
                        }
                        else{
                            Toast.makeText(SingleVendor.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                        Toast.makeText(SingleVendor.this,t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }

        }
        if (VndType.equals("Photographer")) {
            Log.i("inPhotographer","Single Photographer");

            if(fromPackage){
                try {
                    JSONObject photographerObject = new JSONObject(photographer);
                    HeaderText.setText(photographerObject.get("name").toString());
                    TextServ.setText("Service ");
                    TextType.setText("Quality Level ");
                    EventTxt.setVisibility(View.VISIBLE);
                    EventTypetxt.setText(photographerObject.get("eventId").toString());
                    EventTypetxt.setVisibility(View.VISIBLE);

//                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ViewMap.getLayoutParams();
//                    int newMargin = 100; // Set your desired margin value here
//                    layoutParams.leftMargin = newMargin;
//                    ViewMap.setLayoutParams(layoutParams);

                    ViewMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String location = "New York City"; // Specify the desired location

                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+latitude+ "," +longitude+ "&daddr="+24.812923+ ","+ 67.108284);
                            int zoomLevel = 20; // Adjust the zoom level as needed

//                            Uri gmmIntentUri = Uri.parse("geo:24.812923,67.108284?z=" + zoomLevel + "&q=24.812923,67.108284");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name for Google Maps

                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                mapLauncher.launch(mapIntent);
                            } else {
                                Toast.makeText(SingleVendor.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

//                    ViewGroup.MarginLayoutParams sharelayoutParams = (ViewGroup.MarginLayoutParams) Share.getLayoutParams();
//                    int shareMargin = 60; // Set your desired margin value here
//                    sharelayoutParams.leftMargin = shareMargin;
//                    Share.setLayoutParams(sharelayoutParams);

                    Location.setText(photographerObject.get("location").toString().replace("unnamed road, ",""));
                    Price.setText("PKR: "+String.valueOf(photographerObject.getInt("price")));
                    Vehicletyp.setText(photographerObject.get("levelCategoryId").toString());
                    ServType.setText(photographerObject.get("serviceTypeId").toString());
                    Desc.setText(photographerObject.get("description").toString());

                    setupCallButton(photographerObject.get("contact").toString());

                    String ImageendPoints = photographerObject.get("image").toString();
                    if (ImageendPoints.contains(".jpg,")){
                        images = ImageendPoints.split(",");
                        for (int k = 0; k < images.length; k++) {
                            images[k] = images[k].replace("\\\\", "/");
                        }
                        sImage = BaseUrl +"/"+ images[0];
                        Picasso.get().load(sImage).into(imgView);
                        for (int j = 1; j < images.length; j++) {
                            if(images.length <= 2){
                                if (images[j] == null) {
                                    img34_layout.setVisibility(View.GONE);
                                    break;
                                }
                                else {
                                    Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                    Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                    img34_layout.setVisibility(View.GONE);
                                }
                            }
                            else{
                                Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                Picasso.get().load(BaseUrl + "/" + images[2]).into(img3);
                                Picasso.get().load(BaseUrl + "/" + images[3]).into(img4);

                            }
                        }
                    }else{
                        img12_layout.setVisibility(View.GONE);
                        img34_layout.setVisibility(View.GONE);
                        ImgTxt.setVisibility(View.VISIBLE);
                        sImage= BaseUrl + "/" + ImageendPoints;
                        Picasso.get().load(sImage).into(imgView);
                    }

                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    dataLayout.setVisibility(View.VISIBLE);
                    imgView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.initiatePopupWithPicasso(sImage);
                            imagePopup.viewPopup();
                        }
                    });

                    img1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[0]);
                            imagePopup.viewPopup();
                        }
                    });
                    img2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[1]);
                            imagePopup.viewPopup();
                        }
                    });





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            else{

                Call<ResponseVendorType> call = api.getPhotographerType(latitude,longitude);
                call.enqueue(new Callback<ResponseVendorType>() {
                    @Override
                    public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                        // HeaderText.setText(response.body().getData().get(0).getTransportationName());
                        if (response.isSuccessful()) {

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if(VndCode.equals(response.body().getData().get(i).getCode()))
                                {
                                    HeaderText.setText(response.body().getData().get(i).getName());
                                    TextServ.setText("Service Type");
                                    TextType.setText("Level ");

//                                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ViewMap.getLayoutParams();
//                                    int newMargin = 100; // Set your desired margin value here
//                                    layoutParams.leftMargin = newMargin;
//                                    ViewMap.setLayoutParams(layoutParams);

                                    ViewMap.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //String location = "New York City"; // Specify the desired location

                                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+latitude+ "," +longitude+ "&daddr="+24.812923+ ","+ 67.108284);
                                            int zoomLevel = 20; // Adjust the zoom level as needed

//                                            Uri gmmIntentUri = Uri.parse("geo:24.812923,67.108284?z=" + zoomLevel + "&q=24.812923,67.108284");
                                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                            mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name for Google Maps

                                            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                //startActivity(mapIntent);
                                                mapLauncher.launch(mapIntent);
                                            } else {
                                                Toast.makeText(SingleVendor.this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

//                                    ViewGroup.MarginLayoutParams sharelayoutParams = (ViewGroup.MarginLayoutParams) Share.getLayoutParams();
//                                    int shareMargin = 60; // Set your desired margin value here
//                                    sharelayoutParams.leftMargin = shareMargin;
//                                    Share.setLayoutParams(sharelayoutParams);

                                    Location.setText(response.body().getData().get(i).getLocation());
                                    Price.setText("PKR: "+response.body().getData().get(i).getPrice());
                                    Vehicletyp.setText(response.body().getData().get(i).getLevelCategory());
                                    ServType.setText(response.body().getData().get(i).getServiceType());
                                    Desc.setText(response.body().getData().get(i).getDescription());

                                    Log.i("HeaderTextEventPlacer",HeaderText.getText().toString());

                                    String ImageendPoints = response.body().getData().get(i).getImage();
                                    if (ImageendPoints.contains(".jpg,")){
                                        images = ImageendPoints.split(",");
                                        sImage = BaseUrl +"/"+ images[0];
                                        Picasso.get().load(sImage).into(imgView);
                                        for (int j = 1; j < images.length; j++) {
                                            if(images.length <= 2){
                                                if (images[j] == null) {
                                                    img34_layout.setVisibility(View.GONE);
                                                    break;
                                                }
                                                else {
                                                    Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                                    Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                                    img34_layout.setVisibility(View.GONE);
                                                }
                                            }
                                            else{
                                                Picasso.get().load(BaseUrl + "/" + images[0]).into(img1);
                                                Picasso.get().load(BaseUrl + "/" + images[1]).into(img2);
                                                Picasso.get().load(BaseUrl + "/" + images[2]).into(img3);
                                                img4.setVisibility(View.GONE);

                                            }
                                        }
                                    }else{
                                        img12_layout.setVisibility(View.GONE);
                                        img34_layout.setVisibility(View.GONE);
                                        ImgTxt.setVisibility(View.VISIBLE);
                                        sImage= BaseUrl + "/" + ImageendPoints;
                                        Picasso.get().load(sImage).into(imgView);
                                    }

//                            sImage = response.body().getData().get(i).getImage();
//
//                            byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
//                            InputStream inputStream  = new ByteArrayInputStream(imagebytes);
//                            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);


//                            imgView.setImageBitmap(bitmap);
//                                Picasso.get().load(sImage).into(imgView);


                                }
                                else{
                                    Log.i("else","No Record Found for This Vendor Code"+VndCode);
                                }
                            }
                            shimmerFrameLayout.stopShimmerAnimation();
                            shimmerFrameLayout.setVisibility(View.INVISIBLE);
                            dataLayout.setVisibility(View.VISIBLE);

                            imgView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imagePopup.initiatePopupWithPicasso(sImage);
                                    imagePopup.viewPopup();
                                }
                            });
                            img1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[0]);
                                    imagePopup.viewPopup();
                                }
                            });
                            img2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    imagePopup.initiatePopupWithPicasso(BaseUrl +"/"+ images[1]);
                                    imagePopup.viewPopup();
                                }
                            });
                        }
                        else{
                            Toast.makeText(SingleVendor.this,"Kindly wait server is down or Your Internet is not Working",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                        Toast.makeText(SingleVendor.this,t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, proceed with call functionality
                setupCallButton("+923363250316");
            } else {
                // Permission is denied, handle accordingly (e.g., show a message or disable call functionality)
                Toast.makeText(SingleVendor.this, "Call permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setupCallButton(String phoneNumber)
    {
        call_btn = findViewById(R.id.btn_call);
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SingleVendor.this);
                builder.setMessage("Do you want to make the call?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User confirmed, initiate the call
//                                String phoneNumber = "+923363250316"; // Replace with the desired phone number
                                Uri callUri = Uri.parse("tel:" + phoneNumber);
                                Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
                                startActivity(callIntent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the call
                                dialog.dismiss();
                            }
                        });
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
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:0123456789"));
//                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Call the super method to allow the default back button behavior
        super.onBackPressed();

        // Finish the current activity
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Dismiss the dialog if it is showing
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
