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
import com.bprmaa.mobiles.Storage.UserPreferences;
import com.google.firebase.iid.FirebaseInstanceId;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private CardView btn_register, btn_login, btn_menu;
    private EditText txt_email, txt_password;
    private Intent intent;
    private ProgressDialog pd;
    private String respon, name, email, phone, token, customer, foto, alamat, tgl_lahir, sPassword;
    private String jenis_kelamin = null;
    TextView tv;

    SharedPref sharedPref;
    UserPreferences userPreferences;

    AlertDialog alertDialog;
    LayoutInflater inflater;
    Button btn_ok, btn_no;
    View layout;
    TextView tv_title, tv_pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        sharedPref = new SharedPref(this);
        userPreferences = new UserPreferences(this);

        /** Call Function */
        mainButton();
        login();

    }

    private void initView() {

        alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        inflater = LoginActivity.this.getLayoutInflater();
        layout = inflater.inflate(R.layout.dialog_alert, null);
        tv_title = (TextView) layout.findViewById(R.id.tv_title_dialog);
        tv_pesan = (TextView) layout.findViewById(R.id.tv_pesan_dialog);
        btn_ok = (Button) layout.findViewById(R.id.btn_ok_dialog);
        btn_no = (Button) layout.findViewById(R.id.btn_no_dialog);
        alertDialog.setView(layout);
        alertDialog.setCancelable(false);

        pd = new ProgressDialog(this);
        tv = (TextView) findViewById(R.id.tv_test_login);
        btn_register = (CardView) findViewById(R.id.btn_register);
        btn_login = (CardView) findViewById(R.id.btn_login);
        btn_menu = (CardView) findViewById(R.id.btn_menu);
        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);
    }

    private void login() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Sending data...");
                pd.setCancelable(false);
                pd.show();

                String sUsername = txt_email.getText().toString();
//                String sEmail = txt_email.getText().toString();
                sPassword = txt_password.getText().toString();
                String fcmtoken = FirebaseInstanceId.getInstance().getToken();

                ApiService api = ApiConfig.getInstanceRetrofit();
                api.loginReq(sUsername, fcmtoken, sPassword).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pd.dismiss();
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String pesan = jsonObject.optString("pesan");
                                String kode = jsonObject.optString("kode");
                                String result = jsonObject.optString("result");

                                if (kode.equals("1")) {
                                    JSONObject userInfo = jsonObject.getJSONObject("result");
                                    JSONObject icustomer = userInfo.getJSONObject("customer");
                                    token = userInfo.optString("token");
                                    name = userInfo.optString("name");
                                    email = userInfo.optString("email");
                                    phone = userInfo.optString("phone");
                                    foto = userInfo.optString("foto");
                                    alamat = icustomer.optString("alamat");
                                    jenis_kelamin = icustomer.optString("jenis_kelamin");
                                    tgl_lahir = icustomer.optString("tgl_lahir");

                                    logedIn();

                                } else if (kode.equals("0")) {
                                    Log.d("Respons", " "+ result);

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
                            Toast.makeText(LoginActivity.this, "Gagal Mengambil data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void logedIn() {

        // Set Login Status
        if (jenis_kelamin == "null"){
            jenis_kelamin = "Jenis Kelamin";
        }

        if (alamat == "null"){
            alamat = "alamat";
        }

        Log.d("Respon: ", "" + token + " | " + name + " | " + email + " | " + phone +" | " + alamat+ " | " + jenis_kelamin+ " | " + tgl_lahir);
        sharedPref.setUserInfo(token, name, email, sPassword, phone, foto, alamat, jenis_kelamin, tgl_lahir);
        sharedPref.savePrefBoolean(SharedPref.SUDAH_LOGIN, true);

        // Start ListActivity
        intent = new Intent(LoginActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    private void mainButton() {
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(LoginActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

}
