package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;

public class TimeManagement extends AppCompatActivity {
    FloatingActionButton fb_prev, fb_next;
    TextView txt_skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_management);

        fb_prev = findViewById(R.id.fb_Prev);
        fb_next = findViewById(R.id.fb_Next);
        txt_skip = findViewById(R.id.txt_Skip);

        fb_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimeManagement.this, BudgetOptimization.class));
                finish();
            }
        });

        txt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimeManagement.this, NewMainActivity.class));
                finish();
            }
        });
    }
}