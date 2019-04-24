package com.bprmaa.mobiles.Product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.bprmaa.mobiles.Activity.ListActivity;
import com.bprmaa.mobiles.Adapter.ProductAdapter;
import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.App.ResponModel;
import com.bprmaa.mobiles.Helper.ItemClickSupport;
import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductKreditFragment extends Fragment {
    private static final String TAG = "ProductKreditFragment";

    private RecyclerView recycle_view;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItem = new ArrayList<>();
    private SharedPref s;
    private ProgressDialog pd;

    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycleview, container, false);

        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
        s = new SharedPref(getActivity());

        // Call Funtion
        getProductKredit();
        return view;
    }

    private void getProductKredit(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Geting data Product...");
        pd.setCancelable(false);
        pd.show();

        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getProduct(s.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();
                if (response.isSuccessful()){
                    Log.d("RETRO", "Response : " + response.body().getPesan());

                    mItem = response.body().getKredit();
                    mAdapter = new ProductAdapter(getActivity(), mItem);
                    recycle_view.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    recycle_view.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycle_view.setHasFixedSize(true);

                    ItemClickSupport.addTo(recycle_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            showSelectedProduct(mItem.get(position));
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {
                pd.dismiss();
                SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops")
                        .setContentText("Gagal Memuat data");
                dialogKoneksi.show();
            }
        });
    }

    private void showSelectedProduct(DataModel i) {

        s.setProductInfo(i.getId(), i.getName(), i.getEmbeded(), i.getShort_desc(), i.getImage());
        intent = new Intent(getActivity(), ListActivity.class);
        intent.putExtra("jenis", "detail");
        intent.putExtra("katagori", "product");
        startActivity(intent);
    }
}
