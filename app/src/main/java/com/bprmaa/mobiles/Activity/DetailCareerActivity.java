package com.bprmaa.mobiles.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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

public class DetailCareerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout dcDivApply, dcDivForm, lyt_pemberitahuan, btn_upload_file;
    private EditText edtNama, edtEmail, edtNoTlp, edtPosisi, edtDeskripsi;
    private Button btn_submit, btn_back;
    private ProgressDialog pd;
    private TextView tv_name, tv_kualifikasi, tv_fasilitas, tv_upload_name;
    private String token;

    private SharedPref s;

    private String imagePath, h;
    File file;

    private int pageNumber = 0;

    private String pdfFileName;
    public ProgressDialog pDialog;
    public static final int FILE_PICKER_REQUEST_CODE = 1;
    private String pdfPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_career);
        initView();
        setToolbar();

        s = new SharedPref(this);
        token = s.getUserInfo("token");

        tv_name.setText(s.getLowonganInfo("name"));
        tv_kualifikasi.setText(Html.fromHtml(s.getLowonganInfo("kualifikasi")).toString());
        tv_fasilitas.setText(Html.fromHtml(s.getLowonganInfo("fasilitas")).toString());
        edtNama.setText(s.getUserInfo("name"));
        edtEmail.setText(s.getUserInfo("email"));
        edtNoTlp.setText(s.getUserInfo("phone"));
        edtDeskripsi.setText("");

        // Call Function
        submitCareer();
        mainButton();
    }

    private void launchPicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withFilter(Pattern.compile(".*\\.pdf$"))
                .withTitle("Select PDF file")
                .start();
    }

    void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Career");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    private void mainButton() {
        pd.hide();
        dcDivApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dcDivForm.setVisibility(View.VISIBLE);
                dcDivApply.setVisibility(View.GONE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailCareerActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btn_upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchPicker();
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(Intent.createChooser(intent, "Pilih File"), 100);
            }
        });
    }

    private void initView() {

        tv_name = (TextView) findViewById(R.id.tv_nama_lowongan);
        tv_kualifikasi = (TextView) findViewById(R.id.tv_kualifikasi_lowongan);
        tv_fasilitas = (TextView) findViewById(R.id.tv_fasilitas_lowongan);
        tv_upload_name = findViewById(R.id.tv_file_name);

        btn_upload_file = findViewById(R.id.btn_upload_file);

        dcDivApply = findViewById(R.id.dc_div_apply);
        dcDivForm = findViewById(R.id.dc_div_form);
        lyt_pemberitahuan = findViewById(R.id.lyt_pemberitahuan);
        edtNama = findViewById(R.id.edt_nama_career);
        edtEmail = findViewById(R.id.edt_email_career);
        edtNoTlp = findViewById(R.id.edt_noTlp_career);
        edtDeskripsi = findViewById(R.id.edt_deskripsi_lowongan);

        btn_submit = findViewById(R.id.btn_submit);
        btn_back = findViewById(R.id.btn_back);
        pd = new ProgressDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            file = new File(path);

            Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
            pdfFileName = getFileName(uri);
            tv_upload_name.setText(pdfFileName);

            if (path != null) {
                Log.d("Path: ", path);
                pdfPath = path;
                Toast.makeText(this, "Picked file: " + path, Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void submitCareer() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Sending data...");
                pd.setCancelable(false);
                pd.show();
                RequestBody sNama = RequestBody.create(MediaType.parse("text/plain"), edtNama.getText().toString());
                RequestBody sEmail = RequestBody.create(MediaType.parse("text/plain"), edtEmail.getText().toString());
                RequestBody sNoTlp = RequestBody.create(MediaType.parse("text/plain"), edtNoTlp.getText().toString());
                RequestBody sId = RequestBody.create(MediaType.parse("text/plain"), s.getLowonganInfo("id"));
                RequestBody sDesc = RequestBody.create(MediaType.parse("text/plain"), edtDeskripsi.getText().toString());

                Map<String, RequestBody> map = new HashMap<>();
                file = new File(pdfPath);
                // Parsing any Media type file
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("path_resume", file.getName(), requestBody);

                ApiService api = ApiConfig.getInstanceRetrofit();
                api.sendDataCareer(token, sId, sNama, sEmail, sNoTlp, sDesc, body).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pd.dismiss();

                        if (response.isSuccessful()) {
                            try {
                                JSONObject j = new JSONObject(response.body().string());
                                String kode = j.getString("kode");
                                String pesan = j.getString("pesan");

                                if (kode.equals("1")) {
                                    dcDivForm.setVisibility(View.GONE);
                                    dcDivApply.setVisibility(View.GONE);
                                    lyt_pemberitahuan.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("Respon: ", "Gagal Mengiri Data");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        pd.dismiss();
                        Log.d("Respon: ", "Gagal: => " + t.getMessage());
                        Toast.makeText(DetailCareerActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
