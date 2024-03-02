package com.example.user_app.RecylerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.user_app.R;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.example.user_app.SingleVendor;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private Context context;
    private List<VendorTypeControls> items;
    String sImage;

    ImagePopup imagePopup ;

    public GalleryAdapter(Context context, List<VendorTypeControls> items) {
        this.context = context;
        this.items = items;

        imagePopup = new ImagePopup(context);

        imagePopup.setWindowHeight(600); // Optional
        imagePopup.setWindowWidth(1000); // Optional
        imagePopup.setBackgroundColor(Color.parseColor("#88000000"));  // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(false);  // Optional
        imagePopup.setImageOnClickClose(false);// Optional

    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Bind data to views here
        holder.setImageData(items.get(position));

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sImage = items.get(position).getImage();
                imagePopup.initiatePopupWithPicasso(sImage);
                imagePopup.viewPopup();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView img ;
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your views here
            img= itemView.findViewById(R.id.set_image);
        }
        void setImageData(VendorTypeControls items){
            sImage = items.getImage();
//            byte[] imagebytes = Base64.decode(sImage,Base64.DEFAULT);
//            InputStream inputStream  = new ByteArrayInputStream(imagebytes);
//            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

//            img.setImageBitmap(bitmap);
            Picasso.get().load(sImage).into(img);
        }
    }
}
