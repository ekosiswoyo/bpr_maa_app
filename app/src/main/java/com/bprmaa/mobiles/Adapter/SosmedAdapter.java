package com.bprmaa.mobiles.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class SosmedAdapter extends RecyclerView.Adapter<SosmedAdapter.ListViewHolder> {
    private List<DataModel> listProduct;
    private Context context;
    private boolean value = false;
    DataModel p;

    public SosmedAdapter(Context context, List<DataModel> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sosmed, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {

        p = listProduct.get(position);
        holder.tvName.setText(p.getNama());
        Glide.with(context)
                .load(p.getIcon())
//                .override(350, 550)
                .into(holder.imgPhoto);

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = listProduct.get(position).getUrl();

                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
            imgPhoto = itemView.findViewById(R.id.image);
//            tvRemarks = (TextView)itemView.findViewById(R.id.tv_item_remarks);
        }
    }
}