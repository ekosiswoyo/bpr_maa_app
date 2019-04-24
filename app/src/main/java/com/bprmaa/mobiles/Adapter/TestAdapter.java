package com.bprmaa.mobiles.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.R;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.CardViewViewHolder>{
    private List<DataModel> listProduct;
    private Context context;

    public TestAdapter(Context context, List<DataModel> listProduct){
        this.context = context;
        this.listProduct = listProduct;
    }
    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_test, parent, false);
        CardViewViewHolder viewHolder = new CardViewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {

        DataModel p = listProduct.get(position);
//        Glide.with(context)
//                .load(p.getPath_image())
//                .override(350, 550)
//                .into(holder.imgPhoto);

        holder.tvImage.setText(p.getPath_image());
        holder.tvName.setText(p.getName());
        holder.tvDesc.setText(p.getShort_desc());
        holder.tvSlug.setText(p.getSlug());
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder{
//        ImageView imgPhoto;
        TextView tvImage, tvName, tvDesc, tvSlug;
        public CardViewViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tv_item_name);
        }
    }
}
