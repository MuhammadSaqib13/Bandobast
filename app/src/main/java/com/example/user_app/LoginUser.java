package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginUser extends AppCompatActivity {
    LocationManager locationManager;
    TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        register = findViewById(R.id.txt_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUser.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(LoginUser.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(LoginUser.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
    }
}