package com.example.user_app.RecylerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_app.R;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.example.user_app.SingleVendor;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DecoHorizotalAdapter extends RecyclerView.Adapter<DecoHorizotalAdapter.ViewHolder> {
    private final Context context;
    //private final List<HallModel> Halllist;

    private final List<VendorTypeControls> Vendortype_list;
    private static String sImage;

    //    public HallAdapter(Context context, List<HallModel> halllist) {
//        this.context = context;
//        Halllist = halllist;
//    }
    public DecoHorizotalAdapter(Context context, List<VendorTypeControls> vendortype_list) {
        this.context = context;
        Vendortype_list = vendortype_list;
    }
    @NonNull
    @Override
    public DecoHorizotalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.single_item_horizontal,null);
        DecoHorizotalAdapter.ViewHolder holder =new DecoHorizotalAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DecoHorizotalAdapter.ViewHolder holder, int position) {

        holder.setLocationData(Vendortype_list.get(position));
//        holder.HallNameView.setText(model.getName());
//        holder.HallAmntView.setText("PKR "+model.getPrice());
//        holder.HallDescView.setText("For " +model.getPersonRange()+" Persons");
//
//        holder._Code.setText(model.getCode());
//        ApiBaseUrl Base = new ApiBaseUrl();
//
//        //for image setting
//        String BaseUrl = Base.getBaseUrl();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Api api =retrofit.create(Api.class);
//        Call<ResponseVendorType> call = api.getEventPlacerType();
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
//                        byte[] bytes = Base64.decode(sImage, Base64.DEFAULT);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//
//                        holder.HallImageView.setImageBitmap(bitmap);
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
                intent.putExtra("VendorType","Decorator");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return Vendortype_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView HallImageView;
        TextView _Code,HallNameView, HallDescView, HallAmntView;
        LinearLayout recyclerCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            HallImageView = itemView.findViewById(R.id.hallImg);
            HallNameView = itemView.findViewById(R.id.name);
            HallDescView = itemView.findViewById(R.id.details);
            HallAmntView = itemView.findViewById(R.id.amnt_details);
            _Code = itemView.findViewById(R.id.lbl_Code);
            recyclerCard = itemView.findViewById(R.id.recycler_Card);
        }
        void setLocationData(VendorTypeControls hallModel){
            sImage = hallModel.getImage();
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


            Picasso.get().load(sImage).into(HallImageView);
//            final int desiredHeight = 300;
//            Picasso.get().load(sImage).into(HallImageView, new Callback() {
//                @Override
//                public void onSuccess() {
//                    // Get the dimensions of the loaded image
//                    int imageWidth = HallImageView.getDrawable().getIntrinsicWidth();
//                    int imageHeight = HallImageView.getDrawable().getIntrinsicHeight();
//
//                    // Calculate the proportional width based on the desired height
//                    int proportionalWidth = (int) ((float) imageWidth / imageHeight * desiredHeight);
//
//                    // Set the layout parameters of the ImageView to match the proportional width and desired height
//                    ViewGroup.LayoutParams layoutParams = HallImageView.getLayoutParams();
//                    layoutParams.width = proportionalWidth;
//                    layoutParams.height = desiredHeight;
//                    HallImageView.setLayoutParams(layoutParams);
//
//                    // Set the ImageView's visibility to visible
//                    HallImageView.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    // Handle any errors that occur during image loading
//                    e.printStackTrace();
//
//                }
//            });

//            HallImageView.setImageBitmap(resizedBitmap);
            HallNameView.setText(hallModel.getName());
            _Code.setText(hallModel.getCode());
            Log.i("Code","Code"+_Code.getText().toString());
            HallDescView.setText("PKR: " +hallModel.getPrice());
            HallAmntView.setText("PKR: "+hallModel.getPrice());
        }

    }
}
