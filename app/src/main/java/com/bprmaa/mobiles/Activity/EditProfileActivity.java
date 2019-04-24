package com.bprmaa.mobiles.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.MainActivity;
import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView btn_ubah_email, btn_ubah_phone, btn_ubah_tgl, edt_tanggal, edt_email, edt_tlp, btn_ubah_password;
    DatePickerDialog.OnDateSetListener setCalender;
    Button btn_simpan, btn_batal;
    RadioGroup radioGroup;
    RadioButton radioButton, rd_pria, rd_wanita;
    EditText edt_ubah_nama, edt_ubah_alamat;
    ImageView edt_foto, img_foto;
    private String respon, message, name, email, phone, token, customer, foto, alamat, jenis_kelamin, tgl_lahir, password, set_tgl_lahir, email_error, pesan;

    private String imagePath, h;
    File imageFile;
    SharedPref s;
    ProgressDialog pd;

    AlertDialog alertDialog;
    LayoutInflater inflater;
    Button btn_ok, btn_no;
    View layout, line_view;
    TextView tv_pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setToolbar();

        alertDialog = new AlertDialog.Builder(EditProfileActivity.this).create();
        inflater = EditProfileActivity.this.getLayoutInflater();
        layout = inflater.inflate(R.layout.dialog_upload, null);
        btn_no = (Button) layout.findViewById(R.id.btn_no_dialog);
        btn_ok = (Button) layout.findViewById(R.id.btn_ok_dialog);
        img_foto = (ImageView) layout.findViewById(R.id.edt_foto);
        tv_pesan = (TextView) layout.findViewById(R.id.tv_pesan_dialog);
        line_view = (View) layout.findViewById(R.id.line_view);
        alertDialog.setView(layout);
        alertDialog.setCancelable(false);

        edt_foto = (ImageView) findViewById(R.id.edt_foto);
        btn_ubah_email = (TextView) findViewById(R.id.btn_ubah_email);
        btn_ubah_phone = (TextView) findViewById(R.id.btn_ubah_phone);
        btn_ubah_tgl = (TextView) findViewById(R.id.btn_ubah_tgl);
        edt_tanggal = (TextView) findViewById(R.id.edt_tanggal_profile);
        edt_email = (TextView) findViewById(R.id.edt_ubah_email);
        edt_tlp = (TextView) findViewById(R.id.edt_ubah_tlp);
        btn_simpan = (Button) findViewById(R.id.btn_simpan_profil);
        btn_batal = (Button) findViewById(R.id.btn_batal_profil);
        radioGroup = (RadioGroup) findViewById(R.id.rg_jenis_kelamin);
        rd_pria = (RadioButton) findViewById(R.id.rd_pria);
        rd_wanita = (RadioButton) findViewById(R.id.rd_wanita);
        edt_ubah_nama = (EditText) findViewById(R.id.edt_ubah_nama);
        edt_ubah_alamat = (EditText) findViewById(R.id.edt_ubah_alamat);
        btn_ubah_password = findViewById(R.id.btn_ubah_password);

        pd = new ProgressDialog(this);
        s = new SharedPref(this);
        token = s.getUserInfo("token");

        showDialog();
        buttonMain();
        setValue();

//        btn_simpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                gantiFoto();
//            }
//        });
        update();
    }

    private void buttonMain() {
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                intent.putExtra("a", "a");
                startActivity(intent);
            }
        });

        edt_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qq = new Intent(Intent.ACTION_PICK);
                qq.setType("image/*");
                startActivityForResult(Intent.createChooser(qq, "Pilih Foto"), 100);
            }
        });

        btn_ubah_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lupaPassword();
            }
        });
    }

    void update() {
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Updating Data....");
                pd.setCancelable(false);
                pd.show();

                Log.d("Tanggal", "" + tgl_lahir);

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

                String sName = edt_ubah_nama.getText().toString();
                String sAlamat = edt_ubah_alamat.getText().toString();
                String sJenis = radioButton.getText().toString();
                String sEmail = edt_email.getText().toString();
                String sPhone = edt_tlp.getText().toString();

                ApiService api = ApiConfig.getInstanceRetrofit();
                api.update(token, sName, sEmail, sPhone, sAlamat, tgl_lahir, sJenis).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pd.dismiss();
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String pesan = jsonObject.optString("pesan");
                                String kode = jsonObject.optString("kode");
                                JSONObject userInfo = jsonObject.getJSONObject("result");
                                JSONObject icustomer = userInfo.getJSONObject("customer");

                                message = userInfo.optString("message");

                                if (kode.equals("1")) {

                                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(EditProfileActivity.this);
                                    builder.setMessage("Profil Anda Berhasil Diperbaharui")
                                            .setCancelable(false)
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                                                    intent.putExtra("a", "a");
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    finish();
                                                    startActivity(intent);
                                                }
                                            });

                                    android.support.v7.app.AlertDialog alertDialog = builder.create();
                                    alertDialog.show();

                                    token = s.getUserInfo("token");
                                    password = s.getUserInfo("password");
                                    name = userInfo.optString("name");
                                    email = userInfo.optString("email");
                                    phone = userInfo.optString("phone");
                                    foto = userInfo.optString("foto");
                                    alamat = icustomer.optString("alamat");
                                    jenis_kelamin = icustomer.optString("jenis_kelamin");
                                    tgl_lahir = icustomer.optString("tgl_lahir");

                                    s.setUserInfo(token, name, email, password, phone, foto, alamat, jenis_kelamin, tgl_lahir);
                                    Log.d("Respon", "" + token + " | " + name + " | " + email + " | " + phone + " | " + alamat + " | " + jenis_kelamin + " | " + tgl_lahir + " | " + foto);

                                } else {
                                    Toast.makeText(EditProfileActivity.this, "" + email_error, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(EditProfileActivity.this);
                            builder.setMessage("Ubah Email")
                                    .setTitle("Email Sudah di Pakai")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            showDialogEmail();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            android.support.v7.app.AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        pd.dismiss();
                        Log.d("Respon", "Gagal Gais " + t.getMessage());
                        Toast.makeText(EditProfileActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    void gantiFoto() {

        pd.setMessage("Updating Data....");
        pd.setCancelable(false);
        pd.show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("path_foto", imageFile.getName(), requestFile);

        RequestBody sEmail = RequestBody.create(MediaType.parse("text/plain"), edt_email.getText().toString());

        ApiService api = ApiConfig.getInstanceRetrofit();
        api.gantiFotoProfil(token, sEmail, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pd.dismiss();
                if (response.isSuccessful()) {
                    Log.d("Respons", "Berhasil boy");
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        String pesan = jsonObject.optString("pesan");
                        String kode = jsonObject.optString("kode");
                        JSONObject userInfo = jsonObject.getJSONObject("result");
                        JSONObject icustomer = userInfo.getJSONObject("customer");

                        token = s.getUserInfo("token");
                        password = s.getUserInfo("password");
                        name = userInfo.optString("name");
                        email = userInfo.optString("email");
                        phone = userInfo.optString("phone");
                        foto = userInfo.optString("foto");
                        alamat = icustomer.optString("alamat");
                        jenis_kelamin = icustomer.optString("jenis_kelamin");
                        tgl_lahir = icustomer.optString("tgl_lahir");

                        s.setUserInfo(token, name, email, password, phone, foto, alamat, jenis_kelamin, tgl_lahir);
                        Log.d("Respon: ", "" + token + " | " + name + " | " + email + " | " + phone + " | " + alamat + " | " + jenis_kelamin + " | " + tgl_lahir);

                        alertDialog.show();
                        img_foto.setVisibility(View.GONE);
                        btn_no.setVisibility(View.GONE);
                        line_view.setVisibility(View.GONE);
                        btn_ok.setText("ok");
                        tv_pesan.setText("Upload berhasil");

                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                                intent.putExtra("a", "a");
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                startActivity(intent);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("Respon", "gagal Ganti foto");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Log.d("Respon", "gagal t: " + t.getMessage());
                Toast.makeText(EditProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setValue() {

        Log.d("ResponPhoto", s.getUserInfo("foto"));
        if (s.getUserInfo("foto") == "null") {
            Glide.with(EditProfileActivity.this).load(R.drawable.avatar)
                    .override(170, 170)
                    .into(edt_foto);
        } else {
            Glide.with(EditProfileActivity.this).load(s.getUserInfo("foto"))
                    .override(170, 170)
                    .into(edt_foto);
        }

        edt_ubah_nama.setText(s.getUserInfo("name"));

        if (s.getUserInfo("tgl_lahir").equals("null")){
            edt_tanggal.setHint("Atur Tgl Lahir");
        }

        if (s.getUserInfo("alamat").equals("null")){
            edt_ubah_alamat.setHint("Atur Alamat");
        } else{
            edt_ubah_alamat.setText(s.getUserInfo("alamat"));
        }

        String jenis = s.getUserInfo("jenis_kelamin");
        if (jenis.equals("Pria") || jenis.equals("pria")) {
            rd_pria.setChecked(true);
        } else if (jenis.equals("Wanita")) {
            rd_wanita.setChecked(true);
        }

        edt_email.setText(s.getUserInfo("email"));
        edt_tlp.setText(s.getUserInfo("phone"));

        String date_before = s.getUserInfo("tgl_lahir");
        String date_after = formateDateFromstring("dd-MM-yyyy", "dd MMMM yyyy", date_before);
        edt_tanggal.setText(date_after);
    }

    private String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.d("Respon", "ParseException - dateFormat");
        }

        return outputDate;
    }

    void showDialog() {

        //Show Dialog Calender
        btn_ubah_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditProfileActivity.this, setCalender, year, month, day);
                dialog.show();
            }
        });

        setCalender = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int thun, int month, int day) {

                tgl_lahir = thun + "-" + (month + 1) + "-" + day;
                String bulan;
                switch (month + 1) {

                    case 1:
                        bulan = "January";
                        break;
                    case 2:
                        bulan = "February";
                        break;
                    case 3:
                        bulan = "March";
                        break;
                    case 4:
                        bulan = "April";
                        break;
                    case 5:
                        bulan = "Mey";
                        break;
                    case 6:
                        bulan = "June";
                        break;
                    case 7:
                        bulan = "July";
                        break;
                    case 8:
                        bulan = "August";
                        break;
                    case 9:
                        bulan = "September";
                        break;
                    case 10:
                        bulan = "October";
                        break;
                    case 11:
                        bulan = "November";
                        break;
                    default:
                        bulan = "December";
                }

                String tanggal = day + " " + bulan + " " + thun;
                edt_tanggal.setText(tanggal);
                Log.d("Respon", "" + day + " " + bulan + " " + thun);
            }
        };

        //Show Dialog ubah Email
        btn_ubah_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEmail();
            }
        });

        //Show Dialog ubah no Tlp
        btn_ubah_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog ad_ubah_email = new AlertDialog.Builder(EditProfileActivity.this).create();
                LayoutInflater inflater = EditProfileActivity.this.getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_email, null);

                Button btn_simpan = (Button) layout.findViewById(R.id.btn_simpan_dialog);
                Button btn_batal = (Button) layout.findViewById(R.id.btn_batal_dialog);
                final EditText dg_edt_email = (EditText) layout.findViewById(R.id.edt_email_dialog);
                final EditText dg_edt_password = (EditText) layout.findViewById(R.id.edt_password_dialog);
                TextView email_terdaftar = (TextView) layout.findViewById(R.id.tv_email_terdaftar);
                TextView title = (TextView) layout.findViewById(R.id.tv_title_dialog);

                title.setText("Nomor Terdaftar");
                email_terdaftar.setText(s.getUserInfo("phone"));
                dg_edt_email.setHint("Masukkan Nomor Baru");
                dg_edt_email.setInputType(InputType.TYPE_CLASS_NUMBER);

                btn_simpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String email = dg_edt_email.getText().toString();
                        String password = dg_edt_password.getText().toString();

                        if (email.isEmpty()) {
                            dg_edt_email.setError("Kolom Email tidak boleh kosong");
                        } else if (password.isEmpty()) {
                            dg_edt_password.setError("Kolom Password tidak boleh kosong");
                        } else {
                            if (password.equals(s.getUserInfo("password"))) {
                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(EditProfileActivity.this);
                                builder.setMessage("Apakah anda Yakin?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                edt_tlp.setText(dg_edt_email.getText().toString());
                                                ad_ubah_email.dismiss();
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                android.support.v7.app.AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else {
                                dg_edt_password.setError("Password yang Anda Masukkan Salah");
                            }
                        }
                    }
                });

                ad_ubah_email.setView(layout);
                ad_ubah_email.setCancelable(false);
                ad_ubah_email.show();

                btn_batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad_ubah_email.dismiss();
                    }
                });
            }
        });
    }

    void showDialogEmail() {
        final AlertDialog ad_ubah_email = new AlertDialog.Builder(EditProfileActivity.this).create();
        LayoutInflater inflater = EditProfileActivity.this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_email, null);

        Button btn_simpan = (Button) layout.findViewById(R.id.btn_simpan_dialog);
        Button btn_batal = (Button) layout.findViewById(R.id.btn_batal_dialog);
        final EditText dg_edt_email = (EditText) layout.findViewById(R.id.edt_email_dialog);
        final EditText dg_edt_password = (EditText) layout.findViewById(R.id.edt_password_dialog);
        TextView email_terdaftar = (TextView) layout.findViewById(R.id.tv_email_terdaftar);
        email_terdaftar.setText(s.getUserInfo("email"));

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Respon", "Working");
                Log.d("Respon", "" + dg_edt_email.getText().toString());

                String email = dg_edt_email.getText().toString();
                String password = dg_edt_password.getText().toString();

                if (email.isEmpty()) {
                    dg_edt_email.setError("Kolom Email tidak boleh kosong");
                } else if (password.isEmpty()) {
                    dg_edt_password.setError("Kolom Password tidak boleh kosong");
                } else {
                    if (password.equals(s.getUserInfo("password"))) {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(EditProfileActivity.this);
                        builder.setMessage("Apakah anda Yakin?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        edt_email.setText(dg_edt_email.getText().toString());
                                        ad_ubah_email.dismiss();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        android.support.v7.app.AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else {
                        dg_edt_password.setError("Password yang Anda Masukkan Salah");
                    }
                }
            }
        });

        ad_ubah_email.setView(layout);
        ad_ubah_email.setCancelable(false);
        ad_ubah_email.show();

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad_ubah_email.dismiss();
            }
        });
    }

    void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pengaturan Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
        intent.putExtra("a", "a");
        finish();
        startActivity(intent);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
                return;
            }
            Uri selectImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor c = getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
            if (c != null) {
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumn[0]);
                imagePath = c.getString(columnIndex);

                alertDialog();

                h = new File(imagePath).getName();
                c.close();
                imageFile = new File(imagePath);
            } else {
                Toast.makeText(this, "Gambar Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void alertDialog() {
        alertDialog.show();
        tv_pesan.setText("Apa anda Yakin?");
        btn_ok.setText("Upload");
        btn_no.setText("Batal");
        Glide.with(EditProfileActivity.this).load(new File(imagePath))
                .override(170, 170)
                .into(img_foto);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Glide.with(EditProfileActivity.this).load(new File(imagePath))
                        .override(170, 170)
                        .into(edt_foto);
                gantiFoto();
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void lupaPassword() {

        final AlertDialog ubahPassword = new AlertDialog.Builder(EditProfileActivity.this).create();
        LayoutInflater inflater = EditProfileActivity.this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_password, null);

        final Button btn_ubah = (Button) layout.findViewById(R.id.btn_ok);
        final Button btn_batal = (Button) layout.findViewById(R.id.btn_batal);
        final EditText edt_email = (EditText) layout.findViewById(R.id.edt_email_dialog);
        final TextView tvTitle = layout.findViewById(R.id.tv_title);
        final TextView tvPesan = layout.findViewById(R.id.tv_pesan);

        ubahPassword.setView(layout);
        ubahPassword.setCancelable(false);
        ubahPassword.show();

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ubahPassword.dismiss();
            }
        });

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

//                                ubahPassword.dismiss();
                                tvPesan.setVisibility(View.VISIBLE);
                                tvTitle.setVisibility(View.GONE);
                                edt_email.setVisibility(View.GONE);
                                btn_ubah.setVisibility(View.GONE);
                                tvPesan.setText(pesan);
                                btn_batal.setText("OK");
                                btn_batal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ubahPassword.dismiss();
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (!response.isSuccessful()) {
                            tvPesan.setVisibility(View.VISIBLE);
                            tvTitle.setVisibility(View.GONE);
                            edt_email.setVisibility(View.GONE);
                            btn_ubah.setVisibility(View.GONE);
                            tvPesan.setText("Gagal Mereset Password, Silahkan coba lagi");
                            btn_batal.setText("OK");
                            btn_batal.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ubahPassword.dismiss();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        pd.dismiss();
                    }
                });
            }
        });
    }
}



