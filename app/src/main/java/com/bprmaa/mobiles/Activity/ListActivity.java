package com.bprmaa.mobiles.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.bprmaa.mobiles.Fragment.DetailFragment;
import com.bprmaa.mobiles.Fragment.SelengkapnyaFragment;
import com.bprmaa.mobiles.R;

public class ListActivity extends AppCompatActivity{

    private String posisi, dataValue, valueCatagory, kategori = "";
    public String getDataValue(){
        return dataValue;
    }

    public String getValueCategory(){
        return valueCatagory;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        toolbar();

        // Call Function
        getIntentValueJenis();
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void callFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_home, fragment)
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Get Intent
    private void getIntentValueJenis() {
        posisi = getIntent().getStringExtra("jenis");
        if (getIntent().getStringExtra("jenis") != null) {
            if (posisi.equals("promo") || posisi.equals("Promo")) {
                callFragment(new SelengkapnyaFragment());
                dataValue = "promo";
                getDataValue();
            } else if (posisi.equals("lelang") || posisi.equals("Lelang")) {
                callFragment(new SelengkapnyaFragment());
                dataValue = "lelang";
                getDataValue();
            } else if (posisi.equals("news") || posisi.equals("News")) {
                callFragment(new SelengkapnyaFragment());
                dataValue = "news";
                getDataValue();
            } else if (posisi.equals("kurs") || posisi.equals("Kurs Valas")) {
                callFragment(new SelengkapnyaFragment());
                dataValue = "kurs";
                getDataValue();
            } else if (posisi.equals("commodity") || posisi.equals("Commodity")) {
                callFragment(new SelengkapnyaFragment());
                dataValue = "commodity";
                getDataValue();
            } else if (posisi.equals("layanan") || posisi.equals("Layanan")) {
                callFragment(new SelengkapnyaFragment());
                dataValue = "layanan";
                getDataValue();
            } else if (posisi.equals("career")) {
                callFragment(new SelengkapnyaFragment());
                dataValue = "career";
                getDataValue();
            } else if (posisi.equals("history")) {
                callFragment(new SelengkapnyaFragment());
                dataValue = "history";
                getDataValue();
            } else if (posisi.equals("detail")) {

                // Menampilkan Detail Product
                callFragment(new DetailFragment());
                kategori = getIntent().getStringExtra("katagori");
                if (kategori != null){
                    if(kategori.equals("product")){
                        valueCatagory = "product";
                        getValueCategory();
                    } else if (kategori.equals("promo")){
                        valueCatagory = "promo";
                        getValueCategory();
                    } else if (kategori.equals("news")){
                        valueCatagory = "news";
                        getValueCategory();
                    } else if (kategori.equals("layanan")){
                        valueCatagory = "layanan";
                        getValueCategory();
                    } else if (kategori.equals("lelang")){
                        valueCatagory = "lelang";
                        getValueCategory();
                    }
                }
            }
        }
    }
}
