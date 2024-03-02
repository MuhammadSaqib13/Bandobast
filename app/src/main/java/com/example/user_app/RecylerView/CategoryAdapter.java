package com.example.user_app.RecylerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_app.R;
import com.example.user_app.Retrofit.VendorListControls;
import com.example.user_app.listeners.MyClickListener;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private int categoryViewType;
    private ArrayList<VendorListControls> arrayList;
    private MyClickListener listener;

    public CategoryAdapter(Context context, int categoryViewType, ArrayList<VendorListControls> arrayList) {
        this.context = context;
        this.categoryViewType = categoryViewType;
        this.arrayList = arrayList;
    }

    public CategoryAdapter(Context context, int categoryViewType, ArrayList<VendorListControls> arrayList, MyClickListener listener) {
        this.context = context;
        this.categoryViewType = categoryViewType;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    private int selection = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // 1 for capsule
        // 2 for list

        return new ViewHolder(layoutInflater.inflate(R.layout.item_capsule,null));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, @SuppressLint("RecyclerView") int position) {
        VendorListControls obj = arrayList.get(position);

        h.menuTv.setText(obj.getName());

        if (categoryViewType == 1) {
            int sdk = Build.VERSION.SDK_INT;
            if (selection == position) {
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    h.ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.oval_selected));
                } else {
                    h.ll.setBackground(ContextCompat.getDrawable(context, R.drawable.oval_selected));
                }
                h.menuTv.setTextColor(ContextCompat.getColor(context, R.color.black));

            } else {
                if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                    h.ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.oval));
                } else {
                    h.ll.setBackground(ContextCompat.getDrawable(context, R.drawable.oval));
                }
                h.menuTv.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
        }

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryViewType == 1) {
                    selection = position;
                    notifyDataSetChanged();
                }
                if (listener != null) {
                    listener.onClick(obj);
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuTv;
        ImageView moreIv;
        RelativeLayout ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moreIv = itemView.findViewById(R.id.moreIv);
            menuTv = itemView.findViewById(R.id.menuTv);
            ll = itemView.findViewById(R.id.ll);
        }
    }

}

