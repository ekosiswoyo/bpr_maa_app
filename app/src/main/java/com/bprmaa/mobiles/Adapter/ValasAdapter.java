package com.bprmaa.mobiles.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.R;

public class ValasAdapter extends RecyclerView.Adapter<ValasAdapter.ListViewHolder>{
    private List<DataModel> listProduct;
    private Context context;

    public ValasAdapter(Context context, List<DataModel> listProduct){
        this.context = context;
        this.listProduct = listProduct;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_test, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

//        String url = "http://103.82.242.173/maa/storage/files/20181115151131_1.png";

        DataModel p = listProduct.get(position);

        holder.tvName.setText(p.getName());
        holder.tvSymbol.setText(p.getSymbol());
        holder.tvJual.setText(p.getJual());
        holder.tvBeli.setText(p.getBeli());
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvName, tvSymbol, tvJual, tvBeli;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tv_item_name);
            tvSymbol = (TextView)itemView.findViewById(R.id.tv_item_symbol);
            tvJual = (TextView)itemView.findViewById(R.id.tv_item_jual);
            tvBeli = (TextView)itemView.findViewById(R.id.tv_item_beli);
        }
    }
}
