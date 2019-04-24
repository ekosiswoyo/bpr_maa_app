package com.bprmaa.mobiles.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class LihatFotoProfilActivity extends AppCompatActivity {

    private PhotoView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_foto_profil);
        initView();

        SharedPref s = new SharedPref(this);

        Glide.with(getApplicationContext())
                .load(getIntent().getStringExtra("gambar"))
                .into(image);

        s.getPrefString("IMAGE", "apa");

        Log.d("Respon", "message get gambar: "+ s.getPrefString("IMAGE", "apa") );
    }

    private void initView() {
        image = (PhotoView) findViewById(R.id.image);
    }
}
