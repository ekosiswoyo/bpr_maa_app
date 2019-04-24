package com.bprmaa.mobiles.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.bprmaa.mobiles.Activity.DetailCareerActivity;
import com.bprmaa.mobiles.Activity.ListActivity;
import com.bprmaa.mobiles.Adapter.CareerAdapter;
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

public class SelengkapnyaFragment extends Fragment {

    private RecyclerView recycle_view;
    private RecyclerView.Adapter mAdapter;
    private List<DataModel> mItem = new ArrayList<>();
    private SharedPref sharedPref;
    private LinearLayout divCommodity, divHistory, divKurs, tblLayoutCommodity, tblLayoutHistory, tblLayoutKurs, ket_layout;
    private ProgressDialog pd;
    private TextView list, name, judul;
    private String kategori;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recycleview, container, false);

        tblLayoutCommodity = (LinearLayout) view.findViewById(R.id.tabel_layout_commodity);
        tblLayoutHistory = (LinearLayout) view.findViewById(R.id.tabel_layout_history);
        tblLayoutKurs = (LinearLayout) view.findViewById(R.id.tabel_layout_kurs);
        ket_layout = (LinearLayout) view.findViewById(R.id.layout_keterangan);
        divCommodity = view.findViewById(R.id.dp_linear_data_commodity);
        divHistory = view.findViewById(R.id.dp_linear_data_History);
        divKurs = view.findViewById(R.id.dp_linear_data_kurs);

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

        if (getValue == "promo") {
            getPromo();
            list.setText("List Promo");
            kategori = "promo";
        } else if (getValue == "lelang") {
            getLelang();
            list.setText("List Lelang");
            kategori = "lelang";
        } else if (getValue == "news") {
            getNews();
            list.setText("List News");
            kategori = "news";
        } else if (getValue == "kurs") {
            getKurs();
            list.setText("List Kurs Valas");
            tblLayoutKurs.setVisibility(View.VISIBLE);
            ket_layout.setVisibility(View.VISIBLE);
        } else if (getValue == "commodity") {
            getCommodity();
            list.setText("List Commodity");
            judul.setText("Nama Barang");
            tblLayoutCommodity.setVisibility(View.VISIBLE);
//            ket_layout.setVisibility(View.VISIBLE);
        } else if (getValue == "layanan") {
            getLayanan();
            list.setText("List Layanan");
            kategori = "layanan";
        } else if (getValue == "career") {
            getCareer();
            list.setText("List Career");
        } else if (getValue == "history") {
            getHistory();
            list.setText("Riwayat Pengajuan");
            tblLayoutHistory.setVisibility(View.VISIBLE);
        }
    }

    private void getNews() {
        pd.setMessage("Geting data News...");
        pd.setCancelable(false);
        pd.show();
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListNews(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();

                if (response.isSuccessful()) {
                    Log.d("RETRO", "response : " + response.body().getPesan());

                    mItem = response.body().getResult();
                    mAdapter = new ProductAdapter(getActivity(), mItem);
                    recycle_view.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycle_view.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    ItemClickSupport.addTo(recycle_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            showSelectedProduct(mItem.get(position));
                        }
                    });
                }else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
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

    private void getHistory() {
        pd.setMessage("Geting data News...");
        pd.setCancelable(false);
        pd.show();
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getHistoryKredit(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
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
                        divHistory.addView(rootView);
                    }
                } else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
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

    private void getCommodity() {

        pd.setMessage("Geting data News...");
        pd.setCancelable(false);
        pd.show();
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListCommodity(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();

                if (response.isSuccessful()) {
                    Log.d("RETRO", "response : " + response.body().getPesan());

                    List<DataModel> l = response.body().getResult();

                    for (DataModel d : l) {

                        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View rootView = layoutInflater.inflate(R.layout.row_data_commodity, null);

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
                        divCommodity.addView(rootView);
                    }
                }else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
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

    private void getKurs() {
        pd.setMessage("Geting data News...");
        pd.setCancelable(false);
        pd.show();

        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListKurs(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();

                if (response.isSuccessful()) {
                    Log.d("RETRO", "response : " + response.body().getPesan() + " Update : " + response.body().getUpdated_at());

                    TextView tvUpdateAt = view.findViewById(R.id.tv_tglUpdate);
                    tvUpdateAt.setText(response.body().getUpdated_at());

                    List<DataModel> l = response.body().getResult();
                    for (DataModel d : l) {

                        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View rootView = layoutInflater.inflate(R.layout.row_data_valas, null);

                        final TextView tvName = (TextView) rootView.findViewById(R.id.row_dt_name);
//                    final TextView tvSimbol = (TextView) rootView.findViewById(R.id.row_dt_simbol);
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

                            tvName.setText(d.getSymbol());
                            tvBeli.setText(d.getBeli());
                            tvJual.setText(d.getJual());
                        }
                        divKurs.addView(rootView);
                    }
                }else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
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
                SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops")
                        .setContentText("Gagal Memuat data");
                dialogKoneksi.show();
            }
        });
    }

    private void getLayanan() {
        pd.setMessage("Geting data News...");
        pd.setCancelable(false);
        pd.show();
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListLayanan(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();

                if (response.isSuccessful()) {
//                    Log.d("RETRO", "response : " + response.body().getPesan());
                    mItem = response.body().getResult();
                    mAdapter = new ProductAdapter(getActivity(), mItem);
                    recycle_view.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycle_view.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    ItemClickSupport.addTo(recycle_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            showSelectedProduct(mItem.get(position));
                        }
                    });
                }else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
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

    private void getPromo() {
        pd.setMessage("Geting data Product...");
        pd.setCancelable(false);
        pd.show();
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListPromo(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();

                if (response.isSuccessful()) {

//                    Log.d("RETRO", "response : " + response.body().getPesan());
                    mItem = response.body().getResult();
                    mAdapter = new ProductAdapter(getActivity(), mItem);
                    recycle_view.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycle_view.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    ItemClickSupport.addTo(recycle_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            showSelectedProduct(mItem.get(position));
                        }
                    });
                }else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
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

    private void getLelang() {
        pd.setMessage("Geting data Lelang...");
        pd.setCancelable(false);
        pd.show();

        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListLelang(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();

                if (response.isSuccessful()){
                    Log.d("RETRO", "response : " + response.body().getPesan());

                    mItem = response.body().getResult();
                    mAdapter = new ProductAdapter(getActivity(), mItem);
                    recycle_view.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    recycle_view.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    ItemClickSupport.addTo(recycle_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            showSelectedProduct(mItem.get(position));
                        }
                    });
                }else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
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

    private void getCareer() {
        pd.setMessage("Geting data Career...");
        pd.setCancelable(false);
        pd.show();
//        Runnable progressRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                pd.dismiss();
//                Toast.makeText(getActivity(), "After 3000 This show up", Toast.LENGTH_SHORT).show();
//            }
//        };
//        Handler pdCanceller = new Handler();
//        pdCanceller.postDelayed(progressRunnable, 4000);

        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListCareer(sharedPref.getUserInfo("token")).enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
//                    Log.d("RETRO", "response : " + response.body().getPesan());

                    mItem = response.body().getResult();
                    mAdapter = new CareerAdapter(getActivity(), mItem);
                    recycle_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recycle_view.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    ItemClickSupport.addTo(recycle_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            showSelectedCareer(mItem.get(position));
                        }
                    });
                }else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
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

    private void showSelectedProduct(DataModel mItem) {

        // Intent Ke List Activity -> Detail Fragment
        Intent intent = new Intent(getActivity(), ListActivity.class);
        intent.putExtra("jenis", "detail");
        intent.putExtra("katagori", kategori);
        SharedPref sharedPref = new SharedPref(getActivity());
        sharedPref.setProductInfo(mItem.getId(), mItem.getName(), mItem.getEmbeded(), mItem.getShort_desc(), mItem.getImage());
        startActivity(intent);
    }

    private void showSelectedCareer(DataModel i) {

        sharedPref.setLowonganInfo(i.getId(), i.getName(), i.getLokasi(), i.getJenis(), i.getKualifikasi(), i.getFasilitas());
        Intent intent = new Intent(getActivity(), DetailCareerActivity.class);
        startActivity(intent);
    }

}

