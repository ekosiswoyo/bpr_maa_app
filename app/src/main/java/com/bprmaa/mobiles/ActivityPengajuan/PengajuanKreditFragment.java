package com.bprmaa.mobiles.ActivityPengajuan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.MainActivity;
import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuanKreditFragment extends Fragment {
    private static final String TAG = "PengajuanKreditFragment";

    EditText nama, email, phone, alamat;
    Button btn_submit, btn_ok, btn_no;
    SharedPref s;
    ProgressDialog pd;
    String pesan, message, error_email;
    LinearLayout btn_upload_ktp;
    RelativeLayout layout_popup;
    TextView tv_upload_name, textView;
    ImageView image, btn_close;

    AlertDialog alertDialog;
    LayoutInflater inflater;
    View layout;
    TextView tv_title, tv_pesan;
    View line_view;
    PhotoView popup;

    private String imagePath, h;
    File imageFile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengajuan_kredit, container, false);

        alertDialog = new AlertDialog.Builder(getActivity()).create();
        inflater = getActivity().getLayoutInflater();
        layout = inflater.inflate(R.layout.dialog_alert, null);
        tv_title = (TextView) layout.findViewById(R.id.tv_title_dialog);
        tv_pesan = (TextView) layout.findViewById(R.id.tv_pesan_dialog);
        btn_ok = (Button) layout.findViewById(R.id.btn_ok_dialog);
        btn_no = (Button) layout.findViewById(R.id.btn_no_dialog);
        line_view = (View) layout.findViewById(R.id.line_view);
        alertDialog.setView(layout);
        alertDialog.setCancelable(false);

        nama = (EditText) view.findViewById(R.id.edt_namaLengkap_credit);
        email = (EditText) view.findViewById(R.id.edt_email_credit);
        phone = (EditText) view.findViewById(R.id.edt_phone_credit);
        alamat = (EditText) view.findViewById(R.id.edt_alamat_credit);
        btn_submit = (Button) view.findViewById(R.id.btn_submit_kredit);
        btn_upload_ktp = (LinearLayout) view.findViewById(R.id.btn_upload_ktp);
        tv_upload_name = (TextView) view.findViewById(R.id.tv_name_upload);
        textView = (TextView) view.findViewById(R.id.textView);
        layout_popup = view.findViewById(R.id.layout_popup);
        image = view.findViewById(R.id.image);
        btn_close = view.findViewById(R.id.btn_close);
        popup = (PhotoView) view.findViewById(R.id.img_popup);
        s = new SharedPref(getActivity());
        pd = new ProgressDialog(getActivity());

        submit();
        mainBtn();
        setValue();

        return view;
    }

    private void setValue(){
        nama.setText(s.getUserInfo("name"));
        nama.setEnabled(false);
        alamat.setText(s.getUserInfo("alamat"));
        alamat.setEnabled(false);
        phone.setText(s.getUserInfo("phone"));
        phone.setEnabled(false);
        email.setText(s.getUserInfo("email"));
        email.setEnabled(false);
    }

    private void mainBtn(){
        btn_upload_ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent qq = new Intent(Intent.ACTION_PICK);
//                qq.setType("image/*");
//                startActivityForResult(Intent.createChooser(qq, "Pilih Foto"), 100);

                EasyImage.openCamera(PengajuanKreditFragment.this, 1);

//                alertDialog.show();
//                alertDialog.setCancelable(true);
//                tv_pesan.setVisibility(View.GONE);
//                tv_title.setText("Pilih Media");
//                btn_ok.setText("Buka Galery");
//                btn_no.setText("Ambil Foto");
//                btn_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        EasyImage.openGallery(PengajuanKreditFragment.this, 1);
//                        alertDialog.dismiss();
//                    }
//                });
//                btn_no.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        EasyImage.openCamera(PengajuanKreditFragment.this, 1);
//                        alertDialog.dismiss();
//                    }
//                });

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_popup.setVisibility(View.VISIBLE);
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_popup.setVisibility(View.GONE);
            }
        });
    }

    private void submit() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                pd.setMessage("Sending Data...");
                pd.setCancelable(false);
                pd.show();

                String sToken = s.getUserInfo("token");
                RequestBody sName = RequestBody.create(MediaType.parse("text/plain"), nama.getText().toString());
                RequestBody sEmail = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
                RequestBody sPhone = RequestBody.create(MediaType.parse("text/plain"), phone.getText().toString());
                RequestBody sAlamat = RequestBody.create(MediaType.parse("text/plain"), alamat.getText().toString());


                if (imageFile == null) {
                    pd.dismiss();
                    textView.setError("Silahkan Lampirkan File KTP");
//                    Log.d("Response", "Masukkan File");
                } else {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("foto_ktp", imageFile.getName(), requestFile);

                    ApiService api = ApiConfig.getInstanceRetrofit();
                    api.sendDataCredit(sToken, sName, sEmail, sPhone, sAlamat, body).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                pd.dismiss();
                                try {
                                    JSONObject j = new JSONObject(response.body().string());
                                    String kode = j.getString("kode");
                                    if (kode.equals("1")) {
                                        tv_title.setVisibility(View.GONE);
                                        btn_no.setVisibility(View.GONE);
                                        line_view.setVisibility(View.GONE);
                                        tv_pesan.setVisibility(View.VISIBLE);
                                        tv_pesan.setText("Terimakasih, pengajuan kredit anda akan kami proses.");
                                        btn_ok.setText("OK");
                                        alertDialog.show();
                                        btn_ok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                alertDialog.dismiss();
                                                getActivity().finish();
                                                startActivity(new Intent(getActivity(), MainActivity.class));
                                            }
                                        });

                                    } else if (kode.equals("0")) {
                                        tv_title.setVisibility(View.GONE);
                                        btn_no.setVisibility(View.GONE);
                                        tv_pesan.setVisibility(View.VISIBLE);
                                        btn_ok.setText("OK");
                                        tv_pesan.setText("Mohon maaf anda sudah melakukan pengajuan kredit, mohon tunggu proses dari kami");
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

                                Log.d("Respon", "Berhasil");
                            } else {
                                pd.dismiss();
//                                Toast.makeText(getActivity(), "Gagal Mengirim Data", Toast.LENGTH_SHORT).show();
                                email.setError("Masukkan Email yang Benar");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            pd.dismiss();
                            tv_title.setVisibility(View.GONE);
                            btn_no.setVisibility(View.GONE);
                            tv_pesan.setVisibility(View.VISIBLE);
                            btn_ok.setText("OK");
                            tv_pesan.setText(getString(R.string.data_kosong));
                            alertDialog.show();
                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFiles, EasyImage.ImageSource source, int type) {
                imageFile = imageFiles;

                Glide.with(PengajuanKreditFragment.this).load(imageFile)
                        .override(550, 550)
                        .into(image);

                Glide.with(PengajuanKreditFragment.this).load(imageFile)
                        .override(550, 550)
                        .into(popup);

            }
        });
    }

}

