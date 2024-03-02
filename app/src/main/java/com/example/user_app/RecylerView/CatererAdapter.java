package com.example.user_app.RecylerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.example.user_app.SingleVendor;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CatererAdapter extends RecyclerView.Adapter<CatererAdapter.CatererViewHolder> {
    private final Context context;
    private List<VendorTypeControls> Vendortype_list;
    String sImage;



    public CatererAdapter(Context context, List<VendorTypeControls> vendortype_list) {
        this.context = context;
        Vendortype_list = vendortype_list;
    }
    public void setFilteredVendors(List<VendorTypeControls> filteredVendors){
        Vendortype_list = filteredVendors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatererAdapter.CatererViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(com.example.user_app.R.layout.specific_vendor_list,null);
        CatererAdapter.CatererViewHolder holder =new CatererAdapter.CatererViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull CatererAdapter.CatererViewHolder holder, int position) {
        holder.setLocationData(Vendortype_list.get(position));

//        holder.Heading.setText(model.getName());
//        holder.vndLoc.setText(model.getLocation());
//
//        ApiBaseUrl Base = new ApiBaseUrl();
//
//        //for image setting
//        //String BaseUrl = "https://89e5-111-88-194-39.eu.ngrok.io/EventPlacer/";
//        String BaseUrl = Base.getBaseUrl();
//
//        holder.vendAmnt.setText("PKR: "+model.getPrice());
//        holder._Code.setText(model.getCode());
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Api api =retrofit.create(Api.class);
//        Call<ResponseVendorType> call = api.getCatererType();
//
//        call.enqueue(new Callback<ResponseVendorType>() {
//            @Override
//            public void onResponse(Call<ResponseVendorType> call, Response<ResponseVendorType> response) {
//                // HeaderText.setText(response.body().getData().get(0).getTransportationName());
//                Log.i("Adapter","image setting");
//                for (int i = 0; i < response.body().getData().size(); i++) {
//                    if (holder._Code.getText().equals(response.body().getData().get(i).getCode())) {
//
//                        sImage = response.body().getData().get(i).getImage();
//                        byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
//                        InputStream inputStream  = new ByteArrayInputStream(imagebytes);
//
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
//                Log.i("Adapter",t.getMessage());
//
//            }
//        });

        holder.recyclerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleVendor.class);
                intent.putExtra("Shop Code",holder._Code.getText().toString());
                intent.putExtra("VendorType","Caterer");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Vendortype_list.size();
    }

    class CatererViewHolder extends RecyclerView.ViewHolder{
        ImageView img_Vendor;
        CardView recyclerCard;
        TextView Heading,vndLoc,vendAmnt, _Code;

        public CatererViewHolder(@NonNull View itemView) {
            super(itemView);
            _Code = itemView.findViewById(com.example.user_app.R.id.lbl_Code);
            recyclerCard = itemView.findViewById(com.example.user_app.R.id.recycler_Card);
            img_Vendor = itemView.findViewById(com.example.user_app.R.id.imgVendor);
            Heading = itemView.findViewById(com.example.user_app.R.id.mainHeadText);
            vndLoc = itemView.findViewById(com.example.user_app.R.id.VendLocation);
            vendAmnt = itemView.findViewById(com.example.user_app.R.id.vend_amnt_range);
        }
        void setLocationData(VendorTypeControls catmodel){
            sImage = catmodel.getImage();
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

//            img_Vendor.setImageBitmap(bitmap);
            Picasso.get().load(sImage).into(img_Vendor);

            Heading.setText(catmodel.getName());
            _Code.setText(catmodel.getCode());
            vndLoc.setText(catmodel.getDistance()+" Kms");
            vendAmnt.setText("PKR: "+catmodel.getPrice()+" per Person");
        }
    }
}