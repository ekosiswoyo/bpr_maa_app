package com.bprmaa.mobiles.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bprmaa.mobiles.Activity.ListActivity;
import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.CardViewViewHolder>{

    private Context context;
    DataModel p;

    public ArrayList<DataModel> getListPresident() {
        return listProduct;
    }
    public void setListPresident(ArrayList<DataModel> dataModels) {
        this.listProduct = dataModels;
    }

    private ArrayList<DataModel>listProduct;

    public InfoAdapter(Context context) {
        this.context = context;
    }


    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_info, parent, false);
        CardViewViewHolder viewHolder = new CardViewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, final int position) {

        p = listProduct.get(position);
        Glide.with(context)
                .load(p.getImage())
                .override(350, 550)
                .into(holder.imgPhoto);

        holder.tvName.setText(p.getName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ListActivity.class);
                intent.putExtra("jenis", listProduct.get(position).getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvName, tvRemarks;
        LinearLayout layout;
        public CardViewViewHolder(View itemView) {
            super(itemView);
            imgPhoto = (ImageView)itemView.findViewById(R.id.img_item_photo);
            tvName = (TextView)itemView.findViewById(R.id.tv_item_name);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
