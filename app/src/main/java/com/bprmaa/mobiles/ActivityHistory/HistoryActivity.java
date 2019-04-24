package com.bprmaa.mobiles.ActivityHistory;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bprmaa.mobiles.Helper.SectionPageAdapter;
import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = "PengajuanActivity";
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    SharedPref s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        s = new SharedPref(this);

        //Fragment
        Log.d(TAG, "onCreate: String.");
        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        //Set Up the View pager with the Section adapter
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new KreditFragment(), "kredit");
        adapter.addFragment(new TabunganFragment(), "Tabungan/Deposito");

        viewPager.setAdapter(adapter);
    }

//    @Override
//    public void onStart() {
//        if (s.getSudahLogin() == false) {
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//        super.onStart();
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
