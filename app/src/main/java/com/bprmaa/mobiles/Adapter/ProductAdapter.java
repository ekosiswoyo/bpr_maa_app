package com.bprmaa.mobiles.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ListViewHolder>{
    private List<DataModel> listProduct;
    private Context context;

    public ProductAdapter(Context context, List<DataModel> listProduct){
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

//        String url = "http://103.82.242.173/maa/storage/files/20181115151131_1.png";

        DataModel p = listProduct.get(position);
        Glide.with(context)
                .load(p.getImage())
                .override(350, 550)
                .into(holder.imgPhoto);

        holder.tvName.setText(p.getName());
        holder.tvRemarks.setText(p.getShort_desc());
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvName, tvRemarks;

        public ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = (ImageView)itemView.findViewById(R.id.img_item_photo);
            tvName = (TextView)itemView.findViewById(R.id.tv_item_name);
            tvRemarks = (TextView)itemView.findViewById(R.id.tv_item_remarks);
        }
    }
}
