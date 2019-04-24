package com.bprmaa.mobiles.Tentang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bprmaa.mobiles.Adapter.ManagementAdapter;
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

public class ManajemenFragment extends Fragment {

    private static final String TAG = "ManajemenFragment";

    private RecyclerView recyclerView;
    private List<DataModel> mItem = new ArrayList<>();
    private RecyclerView recyclerViewDireksi;
    private List<DataModel> mItemDireksi = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manajemen, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_komisaris);
        recyclerViewDireksi = (RecyclerView) view.findViewById(R.id.rv_direksi);

        loadData();
        return view;
    }

    void loadData() {
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListManagemen().enqueue(new Callback<DataModelArray>() {
            @Override
            public void onResponse(Call<DataModelArray> call, Response<DataModelArray> response) {

                if (response.isSuccessful()) {
                    mItem = response.body().getResult().getKomisaris();
                    mItemDireksi = response.body().getResult().getDireksi();
                    displayView();
                } else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
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

    void displayView() {

        //Komisaris
        mAdapter = new ManagementAdapter(getActivity(), mItem);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        //Direksi
        mAdapter = new ManagementAdapter(getActivity(), mItemDireksi);
        recyclerViewDireksi.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewDireksi.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
