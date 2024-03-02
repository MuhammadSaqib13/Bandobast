package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import xcode.api.phoneverification.OnCodeSentCallback;
import xcode.api.phoneverification.PhoneVerification;
import xcode.api.phoneverification.VerificationException;

public class RegisterActivity extends AppCompatActivity {
    CardView btnCont;
    ImageView btnBack;
    EditText phoneNum, fullname,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.txt_fname);
        email = findViewById(R.id.edttxt_email);

        phoneNum = findViewById(R.id.txt_phone);
        btnCont = findViewById(R.id.btn_cont);
        btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkPersmission = ActivityCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.SEND_SMS);

                if (checkPersmission==PackageManager.PERMISSION_GRANTED){
                    Log.i("Persmission","granted");
                    sendOTP();
                }
                if (checkPersmission != PackageManager.PERMISSION_GRANTED) {
                    Log.i("Persmission","Not granted");
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
//                       public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                              int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.SEND_SMS},0);
                    return;
                }




            }
        });
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginUser.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void sendOTP()
    {
        Log.i("sendOTP","function starts");
        if(ActivityCompat.checkSelfPermission(RegisterActivity.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(RegisterActivity.this, VerifyUser.class);
            startActivity(intent);
            finish();
            if(!phoneNum.getText().toString().equals("") && !fullname.getText().toString().equals("") && !email.getText().toString().equals("")) {
//                PhoneVerification.requestVerificationExplicit(RegisterActivity.this, phoneNum.getText().toString(), new OnCodeSentCallback() {
//                    @Override
//                    public void onSuccessful(String OTP) {
//                        // OTP is the code which is successfully sent
//                    }
//
//
//                    @Override
//                    public void onFailed(VerificationException e) {
//                        // OTP is not sent, hence we need to see the reason
//                        e.printStackTrace();
//                    }
//                });
//                Intent intent = new Intent(RegisterActivity.this, VerifyUser.class);
//                startActivity(intent);
//                finish();
            }
//            if(phoneNum.getText().toString().equals("")){
//                Toast.makeText(getApplicationContext(),"Please enter number",Toast.LENGTH_LONG).show();
//                phoneNum.requestFocus();
//            }
//            if(email.getText().toString().equals("")){
//                Toast.makeText(getApplicationContext(),"Please enter your email",Toast.LENGTH_LONG).show();
//                email.requestFocus();
//            }
//            if(fullname.getText().toString().equals("")){
//                Toast.makeText(getApplicationContext(),"Please enter your name",Toast.LENGTH_LONG).show();
//                fullname.requestFocus();
//            }



        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 0:
                if(grantResults.length >=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    sendOTP();
                }
                else{
                    Toast.makeText(getApplicationContext(),"You don't have required pemission",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}