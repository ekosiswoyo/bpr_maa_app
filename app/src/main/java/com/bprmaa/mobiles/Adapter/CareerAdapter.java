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

public class CareerAdapter extends RecyclerView.Adapter<CareerAdapter.ListViewHolder>{
    private List<DataModel> listCareer;
    private Context context;

    public CareerAdapter(Context context, List<DataModel> listCareer){
        this.context = context;
        this.listCareer = listCareer;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_career, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        DataModel p = listCareer.get(position);

        holder.tvName.setText(p.getName());
        holder.tvLokasi.setText(p.getLokasi());


    }

    @Override
    public int getItemCount() {
        return listCareer.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvLokasi;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tv_name_career);
            tvLokasi = (TextView)itemView.findViewById(R.id.tv_lokasi_career);

        }
    }
}
