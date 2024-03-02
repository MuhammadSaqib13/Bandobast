package com.example.user_app.RecylerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_app.MainVendorListActivity;
import com.example.user_app.R;
import com.example.user_app.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VendorViewHolder>{
    private final Context context;
    private List<VendorModel> VendorList;
    //RecyclerView.Adapter itemClickListener;

    public VendorAdapter(Context context, List<VendorModel> vendorList) {
        this.context = context;
        VendorList = vendorList;
    }
    public void setFilteredVendors(List<VendorModel> filteredVendors){
        VendorList = filteredVendors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.single_vendor,null);
        VendorViewHolder holder =new VendorViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VendorViewHolder holder, int position) {
        VendorModel model = VendorList.get(position);
        holder.Vend_name.setText(model.getName());
        holder.Vend_desc.setText(model.getDescription());
        holder.img_Vendors.setImageResource(model.getImage());

        //Toast.makeText(context, model.getName(), Toast.LENGTH_LONG).show();

        holder.Vend_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainVendorListActivity.class);
                intent.putExtra("Vendor Name",holder.Vend_name.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.img_Vendors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainVendorListActivity.class);
                intent.putExtra("Vendor Name",holder.Vend_name.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.vendCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainVendorListActivity.class);
                intent.putExtra("Vendor Name",holder.Vend_name.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return VendorList.size();
    }

    class VendorViewHolder extends RecyclerView.ViewHolder{
        CardView vendCard;
        TextView Vend_name, Vend_desc;
        ImageView img_Vendors;

        public VendorViewHolder(@NonNull View itemView) {
            super(itemView);
            vendCard = itemView.findViewById(R.id.card_Venue);
            Vend_name = itemView.findViewById(R.id.Venuemain);
            Vend_desc = itemView.findViewById(R.id.txt_desc);
            this.img_Vendors = itemView.findViewById(R.id.imgHalls);
        }
    }
}
