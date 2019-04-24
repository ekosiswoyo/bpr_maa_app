package com.bprmaa.mobiles.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bprmaa.mobiles.Fragment.SelengkapnyaFragment;
import com.bprmaa.mobiles.R;


public class CareerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career);

        callFragment(new SelengkapnyaFragment());
    }

    private void callFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_home, fragment)
                .commit();
    }
}
