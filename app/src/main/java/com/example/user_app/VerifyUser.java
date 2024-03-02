package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import xcode.api.phoneverification.OnVerifyCallback;
import xcode.api.phoneverification.PhoneVerification;
import xcode.api.phoneverification.VerificationException;

public class VerifyUser extends AppCompatActivity {
    ImageView btnBack;
    CardView btnStart;
    EditText codetext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);
        codetext = findViewById(R.id.edttxt_code);

        btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyUser.this,MainActivity.class);
                startActivity(intent);
                finish();
//                PhoneVerification.verifyCode(codetext.getText().toString(), new OnVerifyCallback() {
//                    @Override
//                    public void onSuccessful() {
//                        // Verification successful, OTP match !
//                        startActivity(intent);
//                        finish();
//                    }
//
//                    @Override
//                    public void onFailed(VerificationException e) {
//                        // Verification failed, we need to see the exception
//                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                });

            }
        });
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyUser.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}