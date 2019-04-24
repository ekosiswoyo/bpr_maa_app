package com.bprmaa.mobiles.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bprmaa.mobiles.Activity.EditProfileActivity;
import com.bprmaa.mobiles.MainActivity;
import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    CardView btn_logout, btn_seting;
    TextView name, email, phone, alamat, jenis_kelamin, tgl_lahir;
    SharedPref sharedPref;
    ImageView foto;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);

        // Call Fragment
        mainButton();
        setText();
        return view;
    }

//    @Override
//    public void onStart() {
//        if (sharedPref.getSudahLogin() == false) {
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//        }
//        super.onStart();
//    }

    void initView(View view) {
        sharedPref = new SharedPref(getActivity());
        foto = (ImageView) view.findViewById(R.id.img_foto);
        btn_logout = (CardView) view.findViewById(R.id.btn_logout_profile);
        btn_seting = (CardView) view.findViewById(R.id.btn_setting_profile);
        name = (TextView) view.findViewById(R.id.tv_username_profile);
        email = (TextView) view.findViewById(R.id.tv_email_profil);
        phone = (TextView) view.findViewById(R.id.tv_noTlp_profil);
        alamat = (TextView) view.findViewById(R.id.tv_alamat_profile);
        tgl_lahir = (TextView) view.findViewById(R.id.tv_tgl_lahir_profil);
        jenis_kelamin = (TextView) view.findViewById(R.id.tv_jenis_kelamin_profil);
    }

    void mainButton() {
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.clearAll();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        btn_seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });
    }

    void setText() {

        if (sharedPref.getUserInfo("foto").equals(null)){
            Log.d("ResponPhoto", "Nilai NULL");
        }

        if (sharedPref.getUserInfo("foto").equals("null")){
            Log.d("ResponPhoto", "Foto Null");
            Glide.with(getActivity()).load(R.drawable.avatar)
                    .override(170, 170)
                    .into(foto);
        } else {
            Glide.with(getActivity()).load(sharedPref.getUserInfo("foto"))
                    .override(170, 170)
                    .into(foto);
        }

        if(sharedPref.getUserInfo("jenis_kelamin").equals("null")){
            jenis_kelamin.setHint("Jenis Kelamin");
        } else {
            jenis_kelamin.setText(sharedPref.getUserInfo("jenis_kelamin"));
        }

        if(sharedPref.getUserInfo("tgl_lahir").equals("null")){
            tgl_lahir.setHint("Tgl Lahir");
        }

        if (sharedPref.getUserInfo("alamat").equals("null")){
            Log.d("ResponsAlamat", "Nilainya Null");
            alamat.setHint("Alamat");
        } else {
            alamat.setText(sharedPref.getUserInfo("alamat"));
        }

        name.setText(sharedPref.getUserInfo("name"));
        email.setText(sharedPref.getUserInfo("email"));
        phone.setText(sharedPref.getUserInfo("phone"));

        String date_before = sharedPref.getUserInfo("tgl_lahir");
        String date_after = formateDateFromstring("dd-MM-yyyy", "dd MMMM yyyy", date_before);
        tgl_lahir.setText(date_after);

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

}


