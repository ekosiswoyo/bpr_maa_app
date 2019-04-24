package com.bprmaa.mobiles;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import com.bprmaa.mobiles.Activity.GeneralActivity;
import com.bprmaa.mobiles.Activity.ListActivity;
import com.bprmaa.mobiles.Activity.OTPActivity;
import com.bprmaa.mobiles.Activity.RegisterActivity;
import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.Fragment.ContactFragment;
import com.bprmaa.mobiles.Fragment.ProfileFragment;
import com.bprmaa.mobiles.Helper.BottomNavigationViewHelper;
import com.bprmaa.mobiles.Product.ProductActivity;
import com.bprmaa.mobiles.Storage.SharedPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karan.churi.PermissionManager.PermissionManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView navigation;

    private Menu menu;
    private MenuItem menuItem, kredit;
    private Intent intent;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;

    boolean sBtnInfo = true;
    boolean history_k, history_t = false;
    private String posisi = "0";
    private int chacked;

    private SharedPref s;
    private ProgressDialog pd;
    private PermissionManager pm;

    private AlertDialog alertLogin;
    private LayoutInflater inflater;
    private Button btn_ok, btn_no;
    private View layout;
    private TextView tv_title, tv_pesan, tv_lupa_password, tv_registrasi, tvSelamat;
    private EditText edt_email, edt_password;
    private View view_line;

    MenuItem menuKredit;

    private String message, respon, name, email, phone, token, customer, foto, alamat, tgl_lahir, sPassword, jenis_kelamin, result, pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if (getIntent().getStringExtra("payload") != null) {

            if (getIntent().getStringExtra("payload").equals("product")) {
                startActivity(new Intent(MainActivity.this, ProductActivity.class));
            }
        }

        bottomNavAn();
        navigationView();
        getIntentValueJenis();
        buttonMain();
        askPermission();

    }

    private void askPermission() {
        if (s.getPertamaMasuk()) {
            pm = new PermissionManager() {
            };
            pm.checkAndRequestPermissions(this);
        }
    }

    private void initView() {

        alertLogin = new AlertDialog.Builder(MainActivity.this).create();
        inflater = MainActivity.this.getLayoutInflater();
        layout = inflater.inflate(R.layout.dialog_login, null);
        tv_title = (TextView) layout.findViewById(R.id.tv_title_dialog);
        tv_pesan = (TextView) layout.findViewById(R.id.tv_pesan_dialog);
        tv_lupa_password = (TextView) layout.findViewById(R.id.tv_lupa_password);
        tv_registrasi = (TextView) layout.findViewById(R.id.tv_registrasi);
        edt_email = (EditText) layout.findViewById(R.id.edt_email_dialog);
        edt_password = (EditText) layout.findViewById(R.id.edt_password_dialog);
        view_line = layout.findViewById(R.id.view);

        btn_no = (Button) layout.findViewById(R.id.btn_batal_dialog);
        btn_ok = (Button) layout.findViewById(R.id.btn_Login_dialog);
        alertLogin.setView(layout);
        alertLogin.setCancelable(false);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        s = new SharedPref(this);
        pd = new ProgressDialog(this);

    }

    private void callFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_home, fragment)
                .commit();
    }

    private void getIntentValueJenis() {
        posisi = getIntent().getStringExtra("a");
        if (getIntent().getStringExtra("a") != null) {
            if (posisi.equals("a")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("a", "restart");

//                finish();
                startActivity(intent);
            }
            if (posisi.equals("b")) {
                callFragment(new HomeFragment());
            }
            if (posisi.equals("c")) {
                Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                intent.putExtra("d", "d");
                startActivity(intent);
//                alertLogin();
//                callFragment(new HomeFragment());
            }
            if (posisi.equals("restart")){
                chacked = 2;
                menuItem = menu.getItem(2);
                menuItem.setChecked(true);
                callFragment(new ProfileFragment());
            } else{
                callFragment(new HomeFragment());
            }
        } else {
            callFragment(new HomeFragment());
        }
    }

    //Button Navigation
    private void bottomNavAn() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        menu = bottomNavigationView.getMenu();
        menuItem = menu.getItem(chacked);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ic_home:

                        if (chacked == 0) {
                            Log.d("", "checked");
                        } else {
                            chacked = 0;
                            Log.d("", "do something");
                            menuItem = menu.getItem(0);
                            menuItem.setChecked(true);
                            callFragment(new HomeFragment());
                        }

                        break;
                    case R.id.ic_contact:
                        chacked = 1;
                        menuItem = menu.getItem(1);
                        menuItem.setChecked(true);
                        callFragment(new ContactFragment());
                        break;
                    case R.id.ic_profile:

                        if (s.getSudahLogin() == false) {
//                            alertLogin();
                            Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                            intent.putExtra("d", "d");
                            startActivity(intent);
                        } else {
                            chacked = 2;
                            menuItem = menu.getItem(2);
                            menuItem.setChecked(true);
                            callFragment(new ProfileFragment());
                        }
                        break;
                }
                return false;
            }
        });
    }

    // Navigation View
    private void navigationView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvSelamat = (TextView) header.findViewById(R.id.tv_nama_selamat);
        tvSelamat.setText("Hai, " + s.getUserInfo("name"));

//        menuKredit = header.findViewById(R.id.nav_Kredit);
//        menuKredit.setTitle("Kreditku");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_Profile) {
            chacked = 2;
            if (s.getSudahLogin() == false) {
//                alertLogin();
                Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                intent.putExtra("d", "d");
                startActivity(intent);
            } else {
                menuItem = menu.getItem(2);
                menuItem.setChecked(true);
                callFragment(new ProfileFragment());
            }

        } else if (id == R.id.nav_Main) {
            if (chacked == 0) {
                Log.d("", "checked");
            } else {
                chacked = 0;
                Log.d("", "do something");
                menuItem = menu.getItem(0);
                menuItem.setChecked(true);
                callFragment(new HomeFragment());
            }
        } else if (id == R.id.nav_Corporate) {
            startActivity(new Intent(MainActivity.this, GeneralActivity.class));
        } else if (id == R.id.nav_Product) {
            intent = new Intent(MainActivity.this, ProductActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_Career) {

            if (s.getSudahLogin() == false) {
//                alertLogin();
                Intent intent = new Intent(MainActivity.this, OTPActivity.class);
                intent.putExtra("d", "d");
                startActivity(intent);
            } else {
                intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("jenis", "career");
                startActivity(intent);
            }

        } else if (id == R.id.nav_ContactUs) {
            chacked = 1;
            menuItem = menu.getItem(1);
            menuItem.setChecked(true);
            callFragment(new ContactFragment());
        } else if (id == R.id.nav_Info) {
            intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("jenis", "news");
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return false;
    }

    void alertLogin() {
        alertLogin.show();
        tv_pesan.setVisibility(View.GONE);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_email.getText().toString().isEmpty()) {
                    edt_email.setError("Kolom Email tidak boleh kosong");
                } else if (edt_email.getText().toString().isEmpty()) {
                    edt_email.setError("Kolom Email tidak boleh kosong");
                } else {
                    login();
                }
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertLogin.dismiss();
            }
        });
        tv_lupa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertLupaPass();
            }
        });
    }

    void alertLupaPass() {
        tv_registrasi.setVisibility(View.GONE);
        tv_lupa_password.setVisibility(View.GONE);
        edt_password.setVisibility(View.GONE);
        btn_ok.setText("OK");
        tv_pesan.setVisibility(View.GONE);
        tv_title.setText("Reset Password");
        alertLogin.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alertLogin.dismiss();
                alertReset();
                lupaPassword();
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertLogin.dismiss();
                alertReset();
//                lupaPassword();
            }
        });
    }

    void alertDialog() {

        view_line.setVisibility(View.GONE);
        tv_title.setVisibility(View.GONE);
        tv_registrasi.setVisibility(View.GONE);
        tv_lupa_password.setVisibility(View.GONE);
        edt_email.setVisibility(View.GONE);
        edt_password.setVisibility(View.GONE);
        btn_no.setVisibility(View.GONE);
        btn_ok.setText("OK");
        tv_pesan.setVisibility(View.VISIBLE);
        alertLogin.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertLogin.dismiss();
                alertReset();
            }
        });
    }

    void alertReset() {
        view_line.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.VISIBLE);
        tv_registrasi.setVisibility(View.VISIBLE);
        tv_lupa_password.setVisibility(View.VISIBLE);
        edt_email.setVisibility(View.VISIBLE);
        edt_password.setVisibility(View.VISIBLE);
        btn_no.setVisibility(View.VISIBLE);
    }

    void buttonMain() {
        tv_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                alertLogin.dismiss();
            }
        });
    }

    // Function Button Info
    void login() {
        pd.setMessage("Sending data...");
        pd.setCancelable(false);
        pd.show();

        String sUsername = edt_email.getText().toString();
//        String sEmail = txt_email.getText().toString();
        sPassword = edt_password.getText().toString();
        String fcmtoken = FirebaseInstanceId.getInstance().getToken();

        ApiService api = ApiConfig.getInstanceRetrofit();
        api.loginReq(sUsername, fcmtoken, sPassword).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        pesan = jsonObject.optString("pesan");
                        String kode = jsonObject.optString("kode");
                        result = jsonObject.optString("result");

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
                            alertDialog();
                            tv_pesan.setText("Login Berhasil, Terima kasih");

                        } else if (kode.equals("0")) {
                            Log.d("Respons", " " + result);
                            tv_pesan.setVisibility(View.VISIBLE);
                            edt_email.setError("Email Tidak Cocok");
                            tv_pesan.setText(result);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Gagal Mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logedIn() {

        // Set Login Status
        if (jenis_kelamin == "null") {
            jenis_kelamin = "Jenis Kelamin";
        }

        if (alamat == "null") {
            alamat = "alamat";
        }

        Log.d("Respon: ", "" + token + " | " + name + " | " + email + " | " + phone + " | " + alamat + " | " + jenis_kelamin + " | " + tgl_lahir);
        s.setUserInfo(token, name, email, sPassword, phone, foto, alamat, jenis_kelamin, tgl_lahir);
        s.savePrefBoolean(SharedPref.SUDAH_LOGIN, true);

        // Start ListActivity
//        intent = new Intent(MainActivity.this, MainActivity.class);
//        finish();
//        startActivity(intent);
    }

    private void lupaPassword() {

        pd.setMessage("Sending data...");
        pd.setCancelable(false);
        pd.show();

        String sEmail = edt_email.getText().toString();

        ApiService api = ApiConfig.getInstanceRetrofit();
        api.lupaPassword(sEmail).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();

                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        pesan = jsonObject.optString("result");
                        Log.d("Respons", "berhasil");

                        alertDialog();
                        tv_pesan.setText(pesan);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (!response.isSuccessful()) {
                    alertDialog();
                    tv_pesan.setText("Gagal Mereset Password, Silahkan coba lagi");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}

