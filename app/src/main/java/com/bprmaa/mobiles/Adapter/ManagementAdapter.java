package com.bprmaa.mobiles.Adapter;

import android.content.Context;
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

public class ManagementAdapter extends RecyclerView.Adapter<ManagementAdapter.ListViewHolder> {
    private List<DataModel> listProduct;
    private Context context;
    private boolean value = false;
    DataModel p;

    public ManagementAdapter(Context context, List<DataModel> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_management, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {

        p = listProduct.get(position);
        holder.tvJabatan.setText(p.getJabatan());
        Glide.with(context)
                .load(p.getFoto())
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvJabatan;

        public ListViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_foto);
            tvJabatan = (TextView) itemView.findViewById(R.id.tv_jabatan);
        }
    }
}

