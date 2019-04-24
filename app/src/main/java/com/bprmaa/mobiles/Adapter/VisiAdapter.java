package com.bprmaa.mobiles.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.R;

import java.util.List;

public class VisiAdapter extends RecyclerView.Adapter<VisiAdapter.ListViewHolder> {
    private List<DataModel> listProduct;
    private Context context;
    private boolean value = false;
    DataModel p;

    public VisiAdapter(Context context, List<DataModel> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_visi, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {

        p = listProduct.get(position);
//        holder.tvName.setText(p.getName());
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvAlamat, tvPhone, tvWebsite;
        LinearLayout layout_info;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
            tvAlamat = (TextView) itemView.findViewById(R.id.tv_alamat);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvWebsite = (TextView) itemView.findViewById(R.id.tv_webiste);
            layout_info = (LinearLayout) itemView.findViewById(R.id.layout_info);
//            tvRemarks = (TextView)itemView.findViewById(R.id.tv_item_remarks);
        }
    }
}

