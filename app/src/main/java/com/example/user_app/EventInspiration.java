package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EventInspiration extends AppCompatActivity {

    FloatingActionButton fb_Nxt;
    TextView txt_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_inspiration);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isUserAlreadyUsedApp", true);
        editor.apply();

        fb_Nxt = findViewById(R.id.fb_Next);
        txt_skip = findViewById(R.id.txt_Skip);

        fb_Nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventInspiration.this, VendorRecommendation.class));
                finish();
            }
        });

        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventInspiration.this, NewMainActivity.class));
                finish();
            }
        });


    }
}