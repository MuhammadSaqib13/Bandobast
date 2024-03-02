package com.example.user_app.RecylerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_app.R;
import com.example.user_app.Retrofit.VendorTypeControls;
import com.example.user_app.SelectedPackageActivity;
import com.example.user_app.SingleVendor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private final Context context;
    private String[] package_list;
    private Integer[] amount_list;
    private JSONArray jsonArray;
    private JSONObject jsonObject = null;

    public PackageAdapter(Context context, String[] package_list, Integer[] amount_list, JSONArray jsonArray) {
        this.context = context;
        this.package_list = package_list;
        this.amount_list = amount_list;
        this.jsonArray = jsonArray;
    }


    @NonNull
    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.layout_package,null);
        PackageAdapter.ViewHolder holder =new PackageAdapter.ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.img_Vendor.setImageResource(com.example.user_app.R.drawable.transport_bus);
//        holder.setLocationData(Vendortype_list.get(position));
        holder.packText.setText(package_list[position]);
        holder.amnttext.setText("PKR: "+String.valueOf(amount_list[position]));



        holder.packCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonObject = jsonArray.getJSONObject(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    Intent intent = new Intent(context, SelectedPackageActivity.class);
                    intent.putExtra("Banquet",jsonObject.get("banquet").toString());
                    intent.putExtra("Caterer",jsonObject.get("caterer").toString());
                    intent.putExtra("Photographer",jsonObject.get("photographer").toString());
                    context.startActivity(intent);

                    Log.i("Banquet", jsonObject.get("banquet").toString());
                    Log.i("Caterer", jsonObject.get("caterer").toString());
                    Log.i("Photographer", jsonObject.get("photographer").toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        if (package_list != null) {
            return package_list.length;
        } else {
            return 0; // Return 0 if the array is null to indicate there are no items
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView packCard;
        ImageView iv;
        TextView packText, amnttext;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            packCard = itemView.findViewById(R.id.recycler_Card);
            iv = itemView.findViewById(R.id.imageView);
            packText = itemView.findViewById(R.id.txt_package);
            amnttext = itemView.findViewById(R.id.txt_Amount);

        }
    }
}
