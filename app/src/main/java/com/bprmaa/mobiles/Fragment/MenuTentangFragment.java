package com.bprmaa.mobiles.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Tentang.TentangActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuTentangFragment extends Fragment {

    private ImageView btnSejarah, btnLokasi, btnVisi;

    View view;

    public MenuTentangFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu_tentang, container, false);
        initView(view);

        buttonMenuTentang();
        return view;
    }

    public void buttonMenuTentang(){

        btnSejarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TentangActivity.class);
                intent.putExtra("FRAGMENT_ID", "0");
                startActivity(intent);
            }
        });

        btnVisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TentangActivity.class);
                intent.putExtra("FRAGMENT_ID", "1");
                startActivity(intent);
            }
        });

        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TentangActivity.class);
                intent.putExtra("FRAGMENT_ID", "2");
                startActivity(intent);
            }
        });


    }

    private void initView(View view){

        // Button Menu Tentang
        btnLokasi =(ImageView) view.findViewById(R.id.btn_lokasi);
        btnSejarah =(ImageView) view.findViewById(R.id.btn_sejarah);
        btnVisi =(ImageView) view.findViewById(R.id.btn_visi);

    }

}
