package com.example.user_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user_app.RecylerView.HallAdapter;
import com.example.user_app.RecylerView.HallModel;
import com.example.user_app.RecylerView.VendorAdapter;
import com.example.user_app.RecylerView.VendorModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class Vendor_Main extends AppCompatActivity {
    //Spinner spinner;
    SearchView searchView;
    String[] location = new String[]{"Korangi","Gulshan Iqbal","Defence","Gulistane Johar"};
    ArrayList<String> getLocation = new ArrayList<String>();
    RecyclerView VendorRecView;
    VendorAdapter adpater;
    ArrayList<VendorModel> VendorList = null;
    private Toolbar toolbar;
    FloatingActionButton imgHome;
    ImageView Homebtn;
    TextView Hometxt;
    BottomNavigationView bottomNavigationView;


    //CardView cardRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_main);

        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("VENDOR CATEGORY");


//        cardRecycler = findViewById(R.id.card_Venue);
//        cardRecycler.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Vendor_Main.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });

        imgHome = findViewById(R.id.fb_home);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Vendor_Main.this,NewMainActivity.class));
                finish();

            }
        });
        bottomNavigationView =findViewById(R.id.bottm_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.imgIdeas:
                        startActivity(new Intent(Vendor_Main.this,UserPackage2.class));
                        finish();
                        break;
                    case R.id.img_Plan:
                        startActivity(new Intent(Vendor_Main.this,NewMainActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        });

        VendorRecView = findViewById(R.id.VendorList);
        VendorList = new ArrayList<VendorModel>();
        VendorList.add(new VendorModel("Event Placer","Lawn, Banquet halls, Farmhouse", R.drawable.hall1));
        VendorList.add(new VendorModel("Photographer","Photographer, cinematographer",R.drawable.photography));
        VendorList.add(new VendorModel("Transporter","Car, Bus, Coaster etc",R.drawable.transport));
        VendorList.add(new VendorModel("Decorator","Decorations, Wedding Planner",R.drawable.decor));
        VendorList.add(new VendorModel("Caterer","Catering, Cake, Bartenders",R.drawable.catering));


        adpater = new VendorAdapter(this,VendorList);
        VendorRecView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        VendorRecView.setAdapter(adpater);


//        searchView = findViewById(R.id.searchbar);
//        searchView.clearFocus();




//        spinner = findViewById(R.id.loc1);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Vendor_Main.this, R.layout.locationlists,location);
//        adapter.setDropDownViewResource(R.layout.locationlists);
//
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu,menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };
        menu.findItem(R.id.searchview).setOnActionExpandListener(onActionExpandListener);
        searchView = (SearchView) menu.findItem(R.id.searchview).getActionView();
        searchView.setQueryHint("Search here... ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        return true;
    }
    private void filterList(String text) {
        ArrayList<VendorModel> filterVendor = new ArrayList<VendorModel>();
        for (VendorModel model : VendorList)
        {
            if(model.getName().toLowerCase().contains(text.toLowerCase()))
            {
                filterVendor.add(model);

            }
        }
        if(filterVendor.isEmpty())
        {
            Toast.makeText(this, "No Such type of vendors", Toast.LENGTH_SHORT).show();
        }
        else{
            adpater.setFilteredVendors(filterVendor);
        }
    }
    @Override
    public void onBackPressed() {
        // Call the super method to allow the default back button behavior
        super.onBackPressed();
        // Finish the current activity
        finish();
    }
}