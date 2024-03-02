package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class UserPackage3 extends AppCompatActivity {
    Button btn_Next;
    ImageView img_back;

    TextInputEditText Guest_Text;

    String Cat_rate, Trp_rate, Phot_rate, Decor_rate, Bnqut_rate, EventType, txt_guests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_package3);

        img_back = findViewById(R.id.btn_back);
        btn_Next = findViewById(R.id.btn_Next);
        Guest_Text = findViewById(R.id.guest_text);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPackage3.this,UserPackage2.class));
                finish();
            }
        });

        Intent intent = getIntent();
        Cat_rate = intent.getStringExtra("Catering Rate");
        Decor_rate = intent.getStringExtra("Decorator Rate");
        Bnqut_rate = intent.getStringExtra("Banquet Rate");
        Phot_rate = intent.getStringExtra("Photographer Rate");
        Trp_rate = intent.getStringExtra("Transport Rate");
        EventType = intent.getStringExtra("EventTypeId");
        String EventSelect = intent.getStringExtra("SelectedEvent");

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_guests = Guest_Text.getText().toString();

                Intent intent1 = new Intent(UserPackage3.this, UserPackage4.class);
                intent1.putExtra("Catering Rate",Cat_rate);
                intent1.putExtra("Decorator Rate",Decor_rate);
                intent1.putExtra("Banquet Rate",Bnqut_rate);
                intent1.putExtra("Transport Rate",Trp_rate);
                intent1.putExtra("Photographer Rate",Phot_rate);
                intent1.putExtra("EventTypeId",EventType);
                intent1.putExtra("Guests",txt_guests);
                intent1.putExtra("SelectedEvent",EventSelect);


                startActivity(intent1);
                finish();
            }
        });
    }
}