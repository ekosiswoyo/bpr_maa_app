package com.bprmaa.mobiles.Tentang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bprmaa.mobiles.Adapter.AdapterLokasiKantor;
import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.App.DataModelArray;
import com.bprmaa.mobiles.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LokasiKantorFragment extends Fragment {

    ApiService api = ApiConfig.getInstanceRetrofit();
    private static final String TAG = "LokasiKantorFragment";
    private View view;

    private ArrayList<DataModel> mItem = new ArrayList<>();
    private ArrayList<DataModel> mItemKas = new ArrayList<>();
    private ArrayList<DataModel> mItemPusat = new ArrayList<>();

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    CardView cv_kantor_pusat, majapahit, ungaran, ngalian, kedungmundu, pati, boyolali, gang_tengah, ambarawa;
    String nama, alamat, phone;
    private RecyclerView rv;
    private RecyclerView rvKas;
    private ProgressBar pd1;
    private ProgressBar pd2;
    private TextView tvName;
    private TextView tvAlamat;
    private TextView tvPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loksi_kantor, container, false);
        initView(view);

        cv_kantor_pusat = view.findViewById(R.id.cv_kantor_pusat);
        majapahit = view.findViewById(R.id.cv_cabang_majapahit);
        ungaran = view.findViewById(R.id.cv_cabang_ungaran);
        ngalian = view.findViewById(R.id.cv_cabang_ngalian);
        kedungmundu = view.findViewById(R.id.cv_cabang_kedungmundu);
        pati = view.findViewById(R.id.cv_cabang_pati);
        boyolali = view.findViewById(R.id.cv_cabang_boyolali);
        gang_tengah = view.findViewById(R.id.cv_cabang_gang);
        ambarawa = view.findViewById(R.id.cv_cabang_ambarawa);


        cv_kantor_pusat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.id/maps/place/BPR+Mandiri+Artha+Abadi.+PT/@-6.981904,110.4010713,17z/data=!3m1!4b1!4m5!3m4!1s0x2e708b490c482d5b:0x54ed8abe58afe2b1!8m2!3d-6.981904!4d110.40326"));
                startActivity(intent);
            }
        });

        getLokasi();

        return view;
    }

    private void getLokasi() {
        api.getListKantor().enqueue(new Callback<DataModelArray>() {
            @Override
            public void onResponse(Call<DataModelArray> call, Response<DataModelArray> response) {
                if (response.isSuccessful()) {

                    pd1.setVisibility(View.GONE);
                    pd2.setVisibility(View.GONE);

                    mItem = (ArrayList<DataModel>) response.body().getResult().getCabang();
                    mAdapter = new AdapterLokasiKantor(mItem, getActivity());
                    rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    rv.setAdapter(mAdapter);

                    mItemKas = (ArrayList<DataModel>) response.body().getResult().getKas();
                    mAdapter = new AdapterLokasiKantor(mItemKas, getActivity());
                    rvKas.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    rvKas.setAdapter(mAdapter);

                    mItemPusat = (ArrayList<DataModel>) response.body().getResult().getPusat();

                    for (int i = 0; i < mItemPusat.size(); i++) {
                        nama = mItemPusat.get(0).getName();
                        alamat = mItemPusat.get(0).getAlamat();
                        phone = mItemPusat.get(0).getTelp();
                    }
                    tvName.setText(nama);
                    tvAlamat.setText(alamat);
                    tvPhone.setText(phone);

                }
            }

            @Override
            public void onFailure(Call<DataModelArray> call, Throwable t) {

            }
        });
    }

    void getLokasii() {

        majapahit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.id/maps/place/BPR+MAA,+Kantor+Cabang+Majapahit/@-7.0084262,110.461558,17z/data=!3m1!4b1!4m5!3m4!1s0x2e708ce63261366b:0x5c435b69491a634c!8m2!3d-7.0084262!4d110.4637467"));
                startActivity(intent);
            }
        });
        ungaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.id/maps/place/BPR+MAA,+Kantor+Cabang+Ungaran/@-7.1359343,110.4024384,17z/data=!4m8!1m2!2m1!1sBPR+MAA+UNGARAN!3m4!1s0x2e7088a0880dff43:0xa1a14d2eb99abf6a!8m2!3d-7.138211!4d110.405426"));
                startActivity(intent);
            }
        });
        ngalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.id/maps/place/BPR+MAA/@-7.0009912,110.3575761,15z/data=!4m8!1m2!2m1!1sBPR+MAA+NGALIAN!3m4!1s0x2e708ab8dccfb951:0x47d79561f505b4f7!8m2!3d-6.9927068!4d110.3524116"));
                startActivity(intent);
            }
        });
        kedungmundu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.id/maps/place/BPR+MAA+KC.Kedungmundu/@-7.0126399,110.4480979,15z/data=!4m2!3m1!1s0x0:0x336e301d0da607d5?ved=2ahUKEwiU_PnGm-DeAhWJOY8KHb-IAIsQ_BIwD3oECAYQCA"));
                startActivity(intent);
            }
        });
        pati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.id/maps/place/BPR+MAA.+Kantor+Cabang+Pati/@-6.7493667,111.0267455,17z/data=!3m1!4b1!4m5!3m4!1s0x2e70d24c6d78d043:0xdc65dd6e57662610!8m2!3d-6.7493667!4d111.0289342"));
                startActivity(intent);
            }
        });
        boyolali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.id/maps/place/BPR+MAA,+Kantor+Cabang+Boyolali/@-7.5201221,110.5950683,17z/data=!3m1!4b1!4m5!3m4!1s0x2e7a6eb0847c3483:0x6468e4252c0cfe9!8m2!3d-7.5201221!4d110.597257"));
                startActivity(intent);
            }
        });
        gang_tengah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.id/maps/place/BPR+MAA+Gang+Tengah/@-6.977625,110.4268391,17.25z/data=!4m8!1m2!2m1!1sBPR+MAA+gang+tengah!3m4!1s0x2e708d35c39ed655:0xb78ab1e0ce5bf841!8m2!3d-6.9777585!4d110.4267749"));
                startActivity(intent);
            }
        });
        ambarawa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.co.id/maps/place/BPR+MAA/@-7.2609467,110.3987503,17z/data=!3m1!4b1!4m5!3m4!1s0x2e708b49f108eb09:0xf49357bac2cb7474!8m2!3d-7.2609467!4d110.400939"));
                startActivity(intent);
            }
        });

    }

    private void initView(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rvKas = (RecyclerView) view.findViewById(R.id.rv_kas);
        pd1 = (ProgressBar) view.findViewById(R.id.pd_1);
        pd2 = (ProgressBar) view.findViewById(R.id.pd_2);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvAlamat = (TextView) view.findViewById(R.id.tv_alamat);
        tvPhone = (TextView) view.findViewById(R.id.tv_phone);
    }
}
