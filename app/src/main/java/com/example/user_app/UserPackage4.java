package com.example.user_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class UserPackage4 extends AppCompatActivity {

    Button btn_Next;
    ImageView img_back;

    TextInputEditText AmountText;
    String Cat_rate, Trp_rate, Phot_rate, Decor_rate, Bnqut_rate, EventType, txt_guests, txt_amnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_package4);

        img_back = findViewById(R.id.btn_back);
        btn_Next = findViewById(R.id.btn_Next);
        AmountText = findViewById(R.id.amount_text);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPackage4.this,UserPackage3.class));
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
        txt_guests = intent.getStringExtra("Guests");
        String EventSelect = intent.getStringExtra("SelectedEvent");

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_amnt = AmountText.getText().toString();

                Intent intent1 = new Intent(UserPackage4.this, PackageActivity.class);
                intent1.putExtra("Catering Rate",Cat_rate);
                intent1.putExtra("Decorator Rate",Decor_rate);
                intent1.putExtra("Banquet Rate",Bnqut_rate);
                intent1.putExtra("Transport Rate",Trp_rate);
                intent1.putExtra("Photographer Rate",Phot_rate);
                intent1.putExtra("EventTypeId",EventType);
                intent1.putExtra("Guests",txt_guests);
                intent1.putExtra("Amount",txt_amnt);
                intent1.putExtra("SelectedEvent",EventSelect);


//                Log.i("Catering Rate", Cat_rate);
//                Log.i("Decorator Rate", Decor_rate);
//                Log.i("Banquet Rate", Bnqut_rate);
//                Log.i("Transport Rate", Trp_rate);
//                Log.i("Photographer Rate", Phot_rate);
////                Log.i("EventTypeId", EventType);
//                Log.i("Guests", txt_guests);
//                Log.i("Amount", txt_amnt);

                startActivity(intent1);
                finish();




            }
        });

    }
}