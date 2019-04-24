package com.bprmaa.mobiles.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bprmaa.mobiles.Activity.PlayVideoActivity;
import com.bprmaa.mobiles.Adapter.KantorAdapter;
import com.bprmaa.mobiles.Adapter.SosmedAdapter;
import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.App.DataModelArray;
import com.bprmaa.mobiles.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    TextView btnTest;
    String number = "1234567890";

    private RecyclerView recycle_view;
    private RecyclerView recycle_view_pusat;
    private RecyclerView recycle_view_kas;
    private RecyclerView recycle_view_sosmed;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItem = new ArrayList<>();
    private List<DataModel> mItemPusat = new ArrayList<>();
    private List<DataModel> mItemKas = new ArrayList<>();
    private List<DataModel> mItemSosmed = new ArrayList<>();


    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        btnTest = (TextView) view.findViewById(R.id.btn_test);

        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
        recycle_view_pusat = (RecyclerView) view.findViewById(R.id.recycle_view_kantor_pusat);
        recycle_view_kas = (RecyclerView) view.findViewById(R.id.recycle_view_kas);
        recycle_view_sosmed = (RecyclerView) view.findViewById(R.id.recycle_view_sosmed);
        recycle_view.setHasFixedSize(false);
        recycle_view_kas.setHasFixedSize(true);
        recycle_view_pusat.setHasFixedSize(true);
        recycle_view_sosmed.setHasFixedSize(true);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PlayVideoActivity.class));
            }
        });


//
        loadData();
        return view;
    }

    void loadData() {
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListKantor().enqueue(new Callback<DataModelArray>() {
            @Override
            public void onResponse(Call<DataModelArray> call, Response<DataModelArray> response) {
                if (response.isSuccessful()){
                    mItem = response.body().getResult().getCabang();
                    mItemPusat = response.body().getResult().getPusat();
                    mItemKas = response.body().getResult().getKas();
                    mItemSosmed = response.body().getResult().getSocial();
                    recycleView();
                }
            }

            @Override
            public void onFailure(Call<DataModelArray> call, Throwable t) {
                SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops")
                        .setContentText("Gagal Memuat data");
                dialogKoneksi.show();
            }
        });
    }

    void recycleView(){

        mAdapter = new KantorAdapter(getActivity(), mItemPusat);
        recycle_view_pusat.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycle_view_pusat.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mAdapter = new KantorAdapter(getActivity(), mItem);
        recycle_view.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycle_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mAdapter = new KantorAdapter(getActivity(), mItemKas);
        recycle_view_kas.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycle_view_kas.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mAdapter = new SosmedAdapter(getActivity(), mItemSosmed);
        recycle_view_sosmed.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycle_view_sosmed.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }
}
