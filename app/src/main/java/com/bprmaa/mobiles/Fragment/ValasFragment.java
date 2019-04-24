package com.bprmaa.mobiles.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bprmaa.mobiles.Activity.ListActivity;
import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.App.ResponModel;
import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValasFragment extends Fragment {

    private RecyclerView recycle_view;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItem = new ArrayList<>();
    private SharedPref sharedPref;
    private LinearLayout div, div2, tbl_layout, tbl_layout_history;
    private ScrollView scrollView;
    private ProgressDialog pd;
    private TextView list, name, judul;
    private String kategori;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recycleview_valas, container, false);

        tbl_layout = (LinearLayout) view.findViewById(R.id.tabel_layout_commodity);
        tbl_layout_history = (LinearLayout) view.findViewById(R.id.tabel_layout_history);
        div = view.findViewById(R.id.dp_linear_data_commodity);
        div2 = view.findViewById(R.id.dp_linear_data_History);
        scrollView = (ScrollView) view.findViewById(R.id.layout_view_valas);
        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
        recycle_view.setHasFixedSize(true);
        sharedPref = new SharedPref(getActivity());
        pd = new ProgressDialog(getActivity());
        list = (TextView) view.findViewById(R.id.tv_list_recycler_view);
        judul = (TextView) view.findViewById(R.id.tv_judul);

        callMetode();
        return view;
    }

    private void callMetode() {
        ListActivity sa = (ListActivity) getActivity();
        String getValue = sa.getDataValue();

        if (getValue == "kurs") {
            getKurs();
            list.setText("List Kurs Valas");
            tbl_layout.setVisibility(View.VISIBLE);
        } else if (getValue == "commodity") {
            getCommodity();
            list.setText("List Commodity");
            judul.setText("Nama Barang");
            tbl_layout.setVisibility(View.VISIBLE);
        }
    }

    private void getCommodity() {

        pd.setMessage("Geting data News...");
        pd.setCancelable(false);
        pd.show();
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListCommodity(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();
                Log.d("RETRO", "response : " + response.body().getPesan());

                List<DataModel> l = response.body().getResult();

                for (DataModel d : l) {

                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View rootView = layoutInflater.inflate(R.layout.row_data_valas, null);

                    final TextView tvName = (TextView) rootView.findViewById(R.id.row_dt_name);
                    final TextView tvSimbol = (TextView) rootView.findViewById(R.id.row_dt_simbol);
                    final TextView tvBeli = (TextView) rootView.findViewById(R.id.row_dt_beli);
                    tvBeli.setSelected(true);
                    final TextView tvJual = (TextView) rootView.findViewById(R.id.row_dt_jual);
                    tvJual.setSelected(true);

                    for (int i = 0; i < l.size(); i++) {

                        if (d.getBuy() < d.getOld_buy()) {
                            tvBeli.setTextColor(getResources().getColor(R.color.merah));
                        } else if (d.getBuy() > d.getOld_buy()) {
                            tvBeli.setTextColor(getResources().getColor(R.color.green));
                        }

                        if (d.getSell() < d.getOld_sell()) {
                            tvJual.setTextColor(getResources().getColor(R.color.merah));
                        } else if (d.getSell() > d.getOld_sell()) {
                            tvJual.setTextColor(getResources().getColor(R.color.green));
                        }

                        tvName.setText(d.getName());
                        tvSimbol.setText(d.getSymbol());
                        tvBeli.setText(d.getBeli());
                        tvJual.setText(d.getJual());
                    }
                    div.addView(rootView);
                }
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {
                pd.dismiss();
                Log.d("RETRO", "FAILID " + "Gagal Merespon");
            }
        });
    }

    private void getKurs() {
        pd.setMessage("Geting data News...");
        pd.setCancelable(false);
        pd.show();
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListKurs(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();
                Log.d("RETRO", "response : " + response.body().getPesan());

                List<DataModel> l = response.body().getResult();

                for (DataModel d : l) {

                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View rootView = layoutInflater.inflate(R.layout.row_data_valas, null);

                    final TextView tvName = (TextView) rootView.findViewById(R.id.row_dt_name);
                    final TextView tvSimbol = (TextView) rootView.findViewById(R.id.row_dt_simbol);
                    final TextView tvBeli = (TextView) rootView.findViewById(R.id.row_dt_beli);
                    tvBeli.setSelected(true);
                    final TextView tvJual = (TextView) rootView.findViewById(R.id.row_dt_jual);
                    tvJual.setSelected(true);


                    for (int i = 0; i < l.size(); i++) {

                        if (d.getBuy() < d.getOld_buy()) {
                            tvBeli.setTextColor(getResources().getColor(R.color.merah));
                        } else if (d.getBuy() > d.getOld_buy()) {
                            tvBeli.setTextColor(getResources().getColor(R.color.green));
                        } else {
                            tvBeli.setTextColor(getResources().getColor(R.color.yellow));
                        }

                        if (d.getSell() < d.getOld_sell()) {
                            tvJual.setTextColor(getResources().getColor(R.color.merah));
                        } else if (d.getSell() > d.getOld_sell()) {
                            tvJual.setTextColor(getResources().getColor(R.color.green));
                        } else {
                            tvJual.setTextColor(getResources().getColor(R.color.yellow));
                        }

                        tvName.setText(d.getName());
                        tvSimbol.setText(d.getSymbol());
                        tvBeli.setText(d.getBeli());
                        tvJual.setText(d.getJual());
                    }
                    div.addView(rootView);
                }

//                ItemClickSupport.addTo(recycle_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        showSelectedProduct(mItem.get(position));
//                    }
//                });
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {
                pd.dismiss();
                Log.d("RETRO", "FAILID " + "Gagal Merespon");
            }
        });
    }

}
