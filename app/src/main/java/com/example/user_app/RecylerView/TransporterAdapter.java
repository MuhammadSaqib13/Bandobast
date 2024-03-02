package com.example.user_app.RecylerView;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_app.MainVendorListActivity;
import com.example.user_app.R;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.example.user_app.SingleVendor;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Struct;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransporterAdapter extends RecyclerView.Adapter<TransporterAdapter.TransporterViewHolder> implements LocationListener {
    private final Context context;
    private List<VendorTypeControls> Vendortype_list;
    private static String sImage;
    LocationListener locationTrack;
    private static double Lat = 0.0;
    private  static double Long = 0.0;


    public TransporterAdapter(Context context, List<VendorTypeControls> vendortype_list) {
        this.context = context;
        Vendortype_list = vendortype_list;
    }

    public void setFilteredVendors(List<VendorTypeControls> filteredVendors){
        Vendortype_list = filteredVendors;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TransporterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(com.example.user_app.R.layout.specific_vendor_list,null);
        TransporterViewHolder holder =new TransporterViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull TransporterViewHolder holder, int position) {
        holder.setLocationData(Vendortype_list.get(position));
//        VendorTypeControls model = Vendortype_list.get(position);
//        //holder.img_Vendor.setImageResource(com.example.user_app.R.drawable.transport_bus);
//        holder.Heading.setText(model.getName());
//        holder.vndLoc.setText(model.getDistance()+" Kms");
//
//        //for image setting
//       // String BaseUrl = "https://89e5-111-88-194-39.eu.ngrok.io/Transporter/";
//
//        ApiBaseUrl base = new ApiBaseUrl();
//        String BaseUrl = base.getBaseUrl();
//
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        holder.vendAmnt.setText("PKR: "+model.getPrice()+" per Km Onwards");
//        holder._Code.setText(model.getCode());
//
//        locationTrack =  new LocationListener() {
//            @Override
//            public void onLocationChanged(@NonNull Location location) {
//                //Toast.makeText(MainVendorListActivity.this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
//                try {
//                    Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
//                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                    String address = addresses.get(0).getAddressLine(0);
//                    Lat = location.getLatitude();
//                    Long = location.getLongitude();
//
//                    //ye already double hai ok check karo ok
//
//
//                    Log.i("Latitute", String.valueOf(location.getLatitude()));
//                    Log.i("Longitude", String.valueOf(location.getLongitude()));
//
//                    //String Lat_long="Latitude: " + location.getLatitude()+" \nLongitude: "+location.getLongitude();
//                    //Log.i("Lat", String.valueOf(location.getLatitude()));
//
//
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onLocationChanged(@NonNull List<Location> locations) {
//                LocationListener.super.onLocationChanged(locations);
//            }
//
//            @Override
//            public void onFlushComplete(int requestCode) {
//                LocationListener.super.onFlushComplete(requestCode);
//            }
//
//            @Override
//            public void onProviderEnabled(@NonNull String provider) {
//                LocationListener.super.onProviderEnabled(provider);
//            }
//
//            @Override
//            public void onProviderDisabled(@NonNull String provider) {
//                LocationListener.super.onProviderDisabled(provider);
//            }
//        };
//        Api api =retrofit.create(Api.class);
//        Call<ResponseVendorType> call = api.getVendorType();
//
//        call.enqueue(new Callback<ResponseVendorType>() {
//            @Override
//            public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
//                // HeaderText.setText(response.body().getData().get(0).getTransportationName());
//                for (int i = 0; i < response.body().getData().size(); i++) {
//                    if (holder._Code.getText().equals(response.body().getData().get(i).getCode())) {
//
//                        sImage = response.body().getData().get(i).getImage();
////                        byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
////                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//
//                        byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
//                        InputStream inputStream  = new ByteArrayInputStream(imagebytes);
//                        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//
//                        int width = bitmap.getWidth();
//                        int height = bitmap.getHeight();
//                        float scaleWidth = ((float) 120) / width;
//                        float scaleHeight = ((float) 80) / height;
//
//                        Matrix matrix = new Matrix();
//                        // RESIZE THE BIT MAP
//                        matrix.postScale(scaleWidth, scaleHeight);
//
//                        // "RECREATE" THE NEW BITMAP
//                        Bitmap resizedBitmap = Bitmap.createBitmap(
//                                bitmap, 0, 0, width, height, matrix, false);
//
//                        holder.img_Vendor.setImageBitmap(resizedBitmap);
//
//                    }
//
//                }
//
//            }
//            public void onFailure(Call<ResponseVendorType> call, Throwable t) {
//
//            }
//        });

        holder.recyclerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleVendor.class);
                intent.putExtra("Shop Code",holder._Code.getText().toString());
                intent.putExtra("VendorType","Transporter");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Vendortype_list.size();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Lat = location.getLatitude();
            Long = location.getLongitude();

            //ye already double hai ok check karo ok


            Log.i("Latitute", String.valueOf(location.getLatitude()));
            Log.i("Longitude", String.valueOf(location.getLongitude()));

            String Lat_long="Latitude: " + location.getLatitude()+" \nLongitude: "+location.getLongitude();
            //Log.i("Lat", String.valueOf(location.getLatitude()));



        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    class TransporterViewHolder extends RecyclerView.ViewHolder{
        ImageView img_Vendor;
        CardView recyclerCard;
        TextView Heading,vndLoc,vendAmnt, _Code;
        Drawable pr_drawable, loc_drawable;

        public TransporterViewHolder(@NonNull View itemView) {
            super(itemView);
            _Code = itemView.findViewById(com.example.user_app.R.id.lbl_Code);
            recyclerCard = itemView.findViewById(com.example.user_app.R.id.recycler_Card);
            img_Vendor = itemView.findViewById(com.example.user_app.R.id.imgVendor);
            Heading = itemView.findViewById(com.example.user_app.R.id.mainHeadText);
            vndLoc = itemView.findViewById(com.example.user_app.R.id.VendLocation);
            vendAmnt = itemView.findViewById(com.example.user_app.R.id.vend_amnt_range);
            loc_drawable = ContextCompat.getDrawable(itemView.getContext(), com.example.user_app.R.drawable.ic_outline_location_on_24);
        }
        void setLocationData(VendorTypeControls transmodel){
            sImage = transmodel.getImage();
//            byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
//            InputStream inputStream  = new ByteArrayInputStream(imagebytes);
//            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            float scaleWidth = ((float) 120) / width;
//            float scaleHeight = ((float) 80) / height;
//
//            Matrix matrix = new Matrix();
//            // RESIZE THE BIT MAP
//            matrix.postScale(scaleWidth, scaleHeight);
//
//            // "RECREATE" THE NEW BITMAP
//            Bitmap resizedBitmap = Bitmap.createBitmap(
//                    bitmap, 0, 0, width, height, matrix, false);

            Picasso.get().load(sImage).into(img_Vendor);
 //           img_Vendor.setImageBitmap(bitmap);
            Heading.setText(transmodel.getName());
            _Code.setText(transmodel.getCode());
            vndLoc.setText(transmodel.getDistance()+" Kms");
            vendAmnt.setText("PKR: "+transmodel.getPrice()+" per km");
        }
    }
}
