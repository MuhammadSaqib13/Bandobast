package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(SplashActivity.this, NewMainActivity.class);
//                startActivity(intent);
//                finish();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                boolean isUserAlreadyUsedApp = sharedPreferences.getBoolean("isUserAlreadyUsedApp", false);

                if (isUserAlreadyUsedApp) {
                    // User has already used the application, navigate to NewMainActivity
                    Intent intent = new Intent(SplashActivity.this, NewMainActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Call finish() to prevent the user from going back to the SplashActivity
                } else {
                    // User is using the application for the first time, show EventInspirationActivity
                    Intent intent = new Intent(SplashActivity.this, EventInspiration.class);
                    startActivity(intent);
                    finish(); // Optional: Call finish() to prevent the user from going back to the SplashActivity
                }
            }
        }, 3000);
    }
}