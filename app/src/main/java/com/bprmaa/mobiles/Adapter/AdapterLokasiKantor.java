package com.bprmaa.mobiles.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;

import java.util.ArrayList;
import java.util.Random;


public class AdapterLokasiKantor extends RecyclerView.Adapter<AdapterLokasiKantor.Holdr> {

    ArrayList<DataModel> data;
    Activity context;
    int b;
    SharedPref s;

    public AdapterLokasiKantor(ArrayList<DataModel> data, Activity context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Holdr onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return new Holdr(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_produk_trading, null));
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.list_lokasi_kantor, viewGroup, false);
        return new Holdr(view);
    }

    @Override
    public void onBindViewHolder(AdapterLokasiKantor.Holdr holder, final int i) {
        s = new SharedPref(context);

        final DataModel a = data.get(i);
        final int min = 20;
        final int max = 580;
        final int random = new Random().nextInt((max - min) + 1) + min;

        holder.nama.setText(a.getName());
        holder.tvAlamat.setText(a.getAlamat());
        holder.tvPhone.setText("Telp: "+a.getTelp());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?daddr=" + a.getLatitude()+ "," + a.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.apps.maps");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holdr extends RecyclerView.ViewHolder {
        TextView nama, tvAlamat, tvPhone;
        LinearLayout layout;

        public Holdr(final View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.tv_name);
            tvAlamat = (TextView) itemView.findViewById(R.id.tv_alamat);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
