package com.example.user_app.RecylerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_app.R;
import com.example.user_app.Retrofit.Api;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.Retrofit.ResponseVendorType;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.flaviofaria.kenburnsview.KenBurnsView;
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

public class NewIdeasAdapter  extends RecyclerView.Adapter<NewIdeasAdapter.NewIdeasViewHolder> {
    private List<VendorTypeControls> ideasModels;
    private static String sImage;

    public NewIdeasAdapter(List<VendorTypeControls> ideasModels) {
        this.ideasModels = ideasModels;
    }
    @NonNull
    @Override
    public NewIdeasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewIdeasViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container,
                        parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewIdeasViewHolder holder, int position) {
        holder.setLocationData(ideasModels.get(position));

    }

    @Override
    public int getItemCount() {
        return ideasModels.size();
    }


    static class NewIdeasViewHolder extends RecyclerView.ViewHolder{

        private KenBurnsView kbvLocation;
        private TextView textTitle, textLocation, textStarRating;

        public NewIdeasViewHolder(@NonNull View itemView) {
            super(itemView);
            kbvLocation = itemView.findViewById(R.id.kbvlocation);
            textTitle = itemView.findViewById(R.id.textTitle);
            textLocation = itemView.findViewById(R.id.textLocation);
            textStarRating = itemView.findViewById(R.id.textStarRating);
        }

        void setLocationData(VendorTypeControls ideasmodel){
            sImage = ideasmodel.getImage();
            byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
            InputStream inputStream  = new ByteArrayInputStream(imagebytes);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = ((float) 120) / width;
            float scaleHeight = ((float) 80) / height;

            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight);

            // "RECREATE" THE NEW BITMAP
            Bitmap resizedBitmap = Bitmap.createBitmap(
                    bitmap, 0, 0, width, height, matrix, false);

            kbvLocation.setImageBitmap(bitmap);
            textTitle.setText(ideasmodel.getName());
            textLocation.setText(ideasmodel.getLocation());
            textStarRating.setText("4.5k");
        }

    }
}
