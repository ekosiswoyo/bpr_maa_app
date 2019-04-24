package com.bprmaa.mobiles.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.R;

import java.util.List;

public class KantorAdapter extends RecyclerView.Adapter<KantorAdapter.ListViewHolder> {
    private List<DataModel> listProduct;
    private Context context;
    private boolean value = false;
    DataModel p;

    public KantorAdapter(Context context, List<DataModel> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kantor, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {

        p = listProduct.get(position);
        holder.tvName.setText(p.getName());
        holder.tvAlamat.setText(p.getAlamat());
        holder.tvPhone.setText(p.getTelp());
        holder.tvWebsite.setText(p.getWebsite());
        holder.tvKepala.setText(p.getKepala());

        holder.tvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = listProduct.get(position).getWebsite();

                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

        holder.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = listProduct.get(position).getTelp();
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+phone));  //String phone
                context.getApplicationContext().startActivity(call);
            }
        });

        holder.tvAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://www.google.com/maps/place/"+listProduct.get(position).getLatitude()+","+listProduct.get(position).getLongitude();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Respons", "Clicked");
                if (value == false) {
                    holder.layout_info.setVisibility(View.VISIBLE);
                    value = true;
                } else if (value == true) {
                    holder.layout_info.setVisibility(View.GONE);
                    value = false;
                }

                if (holder.layout_info.isShown()){

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvAlamat, tvPhone, tvWebsite, tvKepala;
        LinearLayout layout_info;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
            tvAlamat = (TextView) itemView.findViewById(R.id.tv_alamat);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvWebsite = (TextView) itemView.findViewById(R.id.tv_webiste);
            tvKepala = (TextView) itemView.findViewById(R.id.tv_namaKepala);
            layout_info = (LinearLayout) itemView.findViewById(R.id.layout_info);
//            tvRemarks = (TextView)itemView.findViewById(R.id.tv_item_remarks);
        }
    }
}

