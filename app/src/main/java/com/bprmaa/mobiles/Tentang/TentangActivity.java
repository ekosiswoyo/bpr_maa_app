package com.bprmaa.mobiles.Tentang;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bprmaa.mobiles.Helper.SectionPageAdapter;
import com.bprmaa.mobiles.R;

public class TentangActivity extends AppCompatActivity{

    private static final String TAG = "TentangActivity";
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    private String posisi;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        posisi = getIntent().getStringExtra("FRAGMENT_ID");
        //Fragment
        Log.d(TAG, "onCreate: String.");
        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        //Set Up the View pager with the Section adapter
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //Teb Selected
        TabLayout.Tab tab = tabLayout.getTabAt(Integer.parseInt(posisi));
        tab.select();
    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SejarahFragment(), "Sejarah");
        adapter.addFragment(new VisiFragment(), "Visi & Misi");
        adapter.addFragment(new LokasiKantorFragment(), "Lokasi kantor");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
