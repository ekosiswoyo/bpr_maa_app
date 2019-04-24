package com.bprmaa.mobiles.Tentang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.biubiubiu.justifytext.library.JustifyTextView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SejarahFragment extends Fragment {

    private static final String TAG = "SejarahFragment";

    JustifyTextView tv_sejarah2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sejarah, container, false);

        tv_sejarah2 = view.findViewById(R.id.tv_sejarah2);

        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getAbout().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject result = jsonObject.getJSONObject("result");
                        String sejarah = result.getString("sejarah");
                        tv_sejarah2.setText(sejarah);

                    } catch (JSONException e) {
                        Log.d("Respons", "Tampilkan ini 2");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.d("Respons", "Tampilkan ini 3");
                        e.printStackTrace();
                    }
                } else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops")
                        .setContentText("Gagal Memuat data");
                dialogKoneksi.show();
            }
        });

        return view;
    }

}
