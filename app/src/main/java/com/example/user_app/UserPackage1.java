package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class UserPackage1 extends AppCompatActivity {

    Button btn_next;
    RatingBar cat_ratingBar,trp_ratingBar,decor_ratingBar,bnqut_ratingBar,photo_ratingBar;
    float cat_rate, trp_rate, decor_rate, bnqut_rate, photo_rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_package1);

        btn_next = findViewById(R.id.btn_Next);
        cat_ratingBar = findViewById(R.id.cat_ratingBar);
        trp_ratingBar = findViewById(R.id.trp_ratingBar);
        decor_ratingBar = findViewById(R.id.decor_ratingBar);
        bnqut_ratingBar = findViewById(R.id.banq_ratingBar);
        photo_ratingBar = findViewById(R.id.photo_ratingBar);

        cat_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                cat_rate = ratingBar.getRating();
            }
        });
        trp_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                trp_rate = ratingBar.getRating();
            }
        });
        decor_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                decor_rate = ratingBar.getRating();
            }
        });
        bnqut_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                bnqut_rate = ratingBar.getRating();
            }
        });
        photo_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                photo_rate = ratingBar.getRating();
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Catering Rate", String.valueOf(cat_rate));
                Log.i("Decor Rate", String.valueOf(decor_rate));
                Log.i("Banquet rate", String.valueOf(bnqut_rate));
                Log.i("Transport Rate", String.valueOf(trp_rate));
                Log.i("Photographer Rate", String.valueOf(photo_rate));

                Intent intent = new Intent(UserPackage1.this, UserPackage2.class);
                intent.putExtra("Catering Rate", String.valueOf(cat_rate));
                intent.putExtra("Decorator Rate", String.valueOf(decor_rate));
                intent.putExtra("Banquet Rate", String.valueOf(bnqut_rate));
                intent.putExtra("Transport Rate", String.valueOf(trp_rate));
                intent.putExtra("Photographer Rate", String.valueOf(photo_rate));

                startActivity(intent);

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
}