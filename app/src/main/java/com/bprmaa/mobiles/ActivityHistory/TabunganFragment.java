package com.bprmaa.mobiles.ActivityHistory;

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
import android.widget.TextView;

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

public class TabunganFragment extends Fragment {

    private RecyclerView recycle_view;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItem = new ArrayList<>();
    private SharedPref sharedPref;
    private LinearLayout div,div2, tbl_layout, tbl_layout_history;
    private ProgressDialog pd;
    private TextView list, name, judul;
    private String kategori;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycleview, container, false);

        tbl_layout = (LinearLayout) view.findViewById(R.id.tabel_layout_commodity);
        tbl_layout_history = (LinearLayout) view.findViewById(R.id.tabel_layout_history);
        div = view.findViewById(R.id.dp_linear_data_commodity);
        div2 = view.findViewById(R.id.dp_linear_data_History);

        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
        recycle_view.setHasFixedSize(true);
        sharedPref = new SharedPref(getActivity());
        pd = new ProgressDialog(getActivity());
        list = (TextView) view.findViewById(R.id.tv_list_recycler_view);
        judul = (TextView) view.findViewById(R.id.tv_judul);

        getHistory();
        tbl_layout_history.setVisibility(View.VISIBLE);

        return view;
    }

    private void getHistory() {
        pd.setMessage("Geting data...");
        pd.setCancelable(false);
        pd.show();
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getHistoryTabungan(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();
                Log.d("RETRO", "response : " + response.body().getPesan());

                List<DataModel> l = response.body().getResult();

                for (DataModel d : l) {

                    Log.d("RETRO", "response : " + d.getDescription());

                    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View rootView = layoutInflater.inflate(R.layout.row_data_history, null);

                    final TextView tvTgl = (TextView) rootView.findViewById(R.id.row_dt_tgl);
                    final TextView tvDesc = (TextView) rootView.findViewById(R.id.row_dt_desc);

                    for (int i = 0; i < l.size(); i++) {
                        tvTgl.setText(d.getTanggal());
                        tvDesc.setText(d.getDescription());
                    }
                    div2.addView(rootView);
                }
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {
                pd.dismiss();
                Log.d("RETRO", "FAILID " + "Gagal Merespon");
            }
        });
    }

}
