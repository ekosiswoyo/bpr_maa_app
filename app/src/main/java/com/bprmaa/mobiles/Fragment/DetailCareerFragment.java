package com.bprmaa.mobiles.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bprmaa.mobiles.MainActivity;
import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCareerFragment extends Fragment {

    private LinearLayout dcDivApply, dcDivForm, lyt_pemberitahuan;
    private EditText edtNama, edtEmail, edtNoTlp, edtPosisi, edtDeskripsi;
    private Button btn_submit, btn_back;
    private ProgressDialog pd;
    private TextView tv_name   , tv_kualifikasi , tv_fasilitas;

    private SharedPref sharedPref;

    public DetailCareerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_career, container, false);
        initView(view);

        sharedPref = new SharedPref(getActivity());

        tv_name.setText(sharedPref.getLowonganInfo("name"));
        tv_kualifikasi.setText(Html.fromHtml(sharedPref.getLowonganInfo("kualifikasi")).toString());
        tv_fasilitas.setText(Html.fromHtml(sharedPref.getLowonganInfo("fasilitas")).toString());
        edtEmail.setText(sharedPref.getUserInfo("email"));
        edtNoTlp.setText(sharedPref.getUserInfo("phone"));
        edtPosisi.setText(sharedPref.getLowonganInfo("name"));

        mainButton();
//        submitCareer();
        return view;
    }

    private void initView(View view) {

        tv_name = (TextView) view.findViewById(R.id.tv_nama_lowongan);
        tv_kualifikasi = (TextView) view.findViewById(R.id.tv_kualifikasi_lowongan);
        tv_fasilitas = (TextView) view.findViewById(R.id.tv_fasilitas_lowongan);

        dcDivApply = view.findViewById(R.id.dc_div_apply);
        dcDivForm = view.findViewById(R.id.dc_div_form);
        lyt_pemberitahuan = view.findViewById(R.id.lyt_pemberitahuan);
        edtNama = view.findViewById(R.id.edt_nama_career);
        edtEmail = view.findViewById(R.id.edt_email_career);
        edtNoTlp = view.findViewById(R.id.edt_noTlp_career);
        edtDeskripsi = view.findViewById(R.id.edt_deskripsi_lowongan);

        btn_submit = view.findViewById(R.id.btn_submit);
        btn_back = view.findViewById(R.id.btn_back);
        pd = new ProgressDialog(getActivity());
    }

//    private void submitCareer(){
//
//        btn_submit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                pd.setMessage("Sending data...");
//                pd.setCancelable(false);
//                pd.show();
//                String sNama = edtNama.getText().toString();
//                String sEmail = edtEmail.getText().toString();
//                String sNoTlp = edtNoTlp.getText().toString();
//                String sPosisi = "1";
//                String sDesc = edtDeskripsi.getText().toString();
//
//                ApiService api = ApiConfig.getInstanceRetrofit();
//                api.sendDataCareer(sNama, sNama, sEmail, sNoTlp, sPosisi, sDesc).enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        pd.dismiss();
//                        Log.d("Respon: ", "Berhasil" + response.body());
//
//                        if (response.isSuccessful()){
//                            try {
//                                JSONObject j = new JSONObject(response.body().string());
//                                String kode = j.getString("kode");
//                                Log.d("Respon: ", "Berhasil : " + kode);
//
//                                if (kode.equals("1")){
//                                    dcDivForm.setVisibility(View.GONE);
//                                    dcDivApply.setVisibility(View.GONE);
//                                    lyt_pemberitahuan.setVisibility(View.VISIBLE);
//
//                                }else {
//
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }else{
//                            Log.d("Respon: ", "Gagal Mengiri Data");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        pd.dismiss();
//                        Log.d("Respon: ", "Gagal" + t.getMessage());
//                    }
//                });
//            }
//        });
//
//    }

    private void mainButton(){
        pd.hide();
        dcDivApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dcDivForm.setVisibility(View.VISIBLE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
    }

}
