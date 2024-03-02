package com.example.user_app.ImportantClass;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.user_app.R;
import com.example.user_app.Retrofit.ApiBaseUrl;
import com.example.user_app.SingleVendor;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImagePagerAdapter extends PagerAdapter {
    private Context context;
    private String[] imageUrls;
    ApiBaseUrl Api = new ApiBaseUrl();
    String BaseUrl = Api.getBaseUrl();

    public ImagePagerAdapter(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_image, container, false);
        SubsamplingScaleImageView imageView = itemView.findViewById(R.id.imageView);

        // Load the image into the ImageView
        String imageUrl = BaseUrl+"/"+imageUrls[position];
        File imageFile = new File(context.getFilesDir(), "image_" + position + ".jpg");

        Picasso.get()
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            FileOutputStream outputStream = new FileOutputStream(imageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            imageView.setImage(ImageSource.uri(Uri.fromFile(imageFile)));
                            // Image saved successfully to outputFile
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Error saving the image
                        }



                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });


//        try {
//            imageView.setImage(ImageSource.uri(imageFile.getCanonicalPath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            Log.i("imageUrlAdapter",imageFile.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        container.addView(itemView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((SubsamplingScaleImageView) object);
    }


}
