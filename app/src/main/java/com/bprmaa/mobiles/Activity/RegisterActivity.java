package com.bprmaa.mobiles.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.MainActivity;
import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edt_nama, edt_email, edt_noTlp, edt_konf_password, edt_password;
    CardView btn_register;
    ProgressDialog pd;
    String result = "";

    AlertDialog alertDialog;
    LayoutInflater inflater;
    Button btn_ok, btn_no;
    View layout;
    TextView tv_title, tv_pesan;
    private String tlp = null;
    private String message, respon, name, email, phone, token, customer, foto, alamat, tgl_lahir, sPassword, jenis_kelamin, pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        tlp = getIntent().getStringExtra("tlp");
        if (tlp != null) {
            edt_noTlp.setText(tlp);
            edt_noTlp.setEnabled(false);
        }

        //Call Function
        startRegister();
    }

    private void startRegister() {

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edt_nama.getText().toString().isEmpty()) {
                    edt_nama.setError("Kolom Nama Tidak Boleh Kosong");
                } else if (edt_email.getText().toString().isEmpty()) {
                    edt_email.setError("Kolom Email Tidak Boleh Kosong");
                } else if (edt_noTlp.getText().toString().isEmpty()) {
                    edt_noTlp.setError("Kolom NoTlp Tidak Boleh Kosong");
                } else if (edt_password.getText().toString().isEmpty()) {
                    edt_password.setError("Kolom Password Tidak Boleh Kosong");
                } else if (edt_password.getText().toString().trim()
                        .length() < 6) {
                    edt_password.setError("Kolom Password Minimal 6 Karakter");
                } else {
                    pd.setMessage("Sending data...");
                    pd.setCancelable(false);
                    pd.show();
                    String sNama = edt_nama.getText().toString();
                    String sEmail = edt_email.getText().toString();
                    String sNoTlp = edt_noTlp.getText().toString();
                    String sPassword = edt_password.getText().toString();
//                String sKonfPassword = edt_konf_password.getText().toString();

                    ApiService api = ApiConfig.getInstanceRetrofit();
                    api.sendDataReg(sNama, sEmail, sNoTlp, sPassword).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            pd.dismiss();

                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    String pesan = jsonObject.optString("pesan");
                                    String kode = jsonObject.optString("kode");
                                    JSONObject userInfo = jsonObject.getJSONObject("result");
                                    result = userInfo.optString("message");
                                    token = userInfo.optString("token");
                                    name = userInfo.optString("name");
                                    email = userInfo.optString("email");
                                    phone = userInfo.optString("phone");
                                    foto = userInfo.optString("foto");
                                    alamat = "null";
                                    jenis_kelamin = "null";
                                    tgl_lahir = "null";

                                    if (kode.equals("1")) {
                                        Log.d("Respons", " " + result);
                                        tv_title.setVisibility(View.GONE);
                                        btn_no.setVisibility(View.GONE);
                                        tv_pesan.setText(result);
                                        alertDialog.show();
                                        btn_ok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                alertDialog.dismiss();
                                                logedIn();
                                            }
                                        });
                                    } else if (kode.equals("0")) {
                                        Log.d("Respons", " " + result);
                                        tv_title.setVisibility(View.GONE);
                                        btn_no.setVisibility(View.GONE);
                                        tv_pesan.setText(result);
                                        alertDialog.show();
                                        btn_ok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                edt_email.setError("Kolom Email Tidak Valid");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void initView() {

        alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        inflater = RegisterActivity.this.getLayoutInflater();
        layout = inflater.inflate(R.layout.dialog_alert, null);
        tv_title = (TextView) layout.findViewById(R.id.tv_title_dialog);
        tv_pesan = (TextView) layout.findViewById(R.id.tv_pesan_dialog);
        btn_ok = (Button) layout.findViewById(R.id.btn_ok_dialog);
        btn_no = (Button) layout.findViewById(R.id.btn_no_dialog);
        alertDialog.setView(layout);
        alertDialog.setCancelable(false);

        pd = new ProgressDialog(this);
        btn_register = (CardView) findViewById(R.id.btn_register);
        edt_nama = (EditText) findViewById(R.id.edt_nama);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_noTlp = (EditText) findViewById(R.id.edt_noTlp);
        edt_password = (EditText) findViewById(R.id.edt_password);
    }

    private void logedIn() {

        // Set Login Status
        SharedPref s = new SharedPref(RegisterActivity.this);
        Log.d("ResponRegistrasi", "" + token + " | " + name + " | " + email + " | " + phone + " | " + alamat + " | " + jenis_kelamin + " | " + tgl_lahir);
        s.setUserInfo(token, name, email, sPassword, phone, foto, alamat, jenis_kelamin, tgl_lahir);
        s.savePrefBoolean(SharedPref.SUDAH_LOGIN, true);

        // Start ListActivity
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
