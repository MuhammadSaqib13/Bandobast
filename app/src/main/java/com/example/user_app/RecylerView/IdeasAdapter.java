package com.example.user_app.RecylerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_app.ImportantClass.LocationHelper;
import com.example.user_app.R;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.example.user_app.listeners.MyClickListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdeasAdapter extends RecyclerView.Adapter<IdeasAdapter.ViewHolder>{
    Context context;
    private ArrayList<VendorTypeControls> arrayList;
    MyClickListener listener;
    String sImage;
    double latitude, longitude;


    public IdeasAdapter(Context context, ArrayList<VendorTypeControls> arrayList, MyClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.image_vendortype, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        VendorTypeControls obj = arrayList.get(position);
        //String vndcal = obj.getName();
        ApiBaseUrl base = new ApiBaseUrl();
        String BaseUrl = base.getBaseUrl();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LocationHelper locationHelper = new LocationHelper(context);
        LatLng latLng = locationHelper.getLatitudeLongitude();

        if (latLng != null) {
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            // Use the latitude and longitude as needed
            // Example: Log the values
            Log.i("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
        }

            Api apicat = retrofit.create(Api.class);
            Call<ResponseVendorType> call_cat = apicat.getCatererType(latitude,longitude);
            call_cat.enqueue(new Callback<ResponseVendorType>() {
                @Override
                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        sImage = response.body().getData().get(i).getImage();
                        byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                        h.iv.setImageBitmap(bitmap);
                    }


                }

                @Override
                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
                    Log.i("no",t.getMessage());
                }
            });


//            Api apicat = retrofit.create(Api.class);
//            Call<ResponseVendorType> call_cat = apicat.getVendorType();
//            call_cat.enqueue(new Callback<ResponseVendorType>() {
//                @Override
//                public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
//                    for (int i = 0; i < response.body().getData().size(); i++) {
//                        sImage = response.body().getData().get(i).getImage();
//                        byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//
//                        h.iv.setImageBitmap(bitmap);
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<ResponseVendorType> call, Throwable t) {
//
//                }
//            });


        //h.nameTv.setText(obj.getName());
        //h.descTv.setText(obj.getDesc());

//        if (obj.getHotDeal() == 1) {
//            h.hotDealsCv.setVisibility(View.VISIBLE);
//        } else {
//            h.hotDealsCv.setVisibility(View.GONE);
//        }

//
//        double amtInPer = (obj.getAdvance() / 100);
//        double amt = obj.getPrice() * amtInPer;
//        h.amountTv.setText("Rs." + amt + "\nadvance");
//
//        if (obj.getQuantity() > 0) {
//            h.outOfStockTv.setVisibility(View.GONE);
//        } else {
//            h.outOfStockTv.setVisibility(View.VISIBLE);
//        }

        //Glide.with(context).load(obj.getImageUrl()).placeholder(R.drawable.image_not_found).error(R.drawable.image_not_found).into(h.iv);

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(obj);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);

        }
    }

}
