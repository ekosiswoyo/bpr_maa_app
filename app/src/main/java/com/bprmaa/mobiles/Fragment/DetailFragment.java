package com.bprmaa.mobiles.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bprmaa.mobiles.Activity.LihatFotoProfilActivity;
import com.bprmaa.mobiles.Activity.ListActivity;
import com.bprmaa.mobiles.Activity.OTPActivity;
import com.bprmaa.mobiles.Activity.PlayVideoActivity;
import com.bprmaa.mobiles.ActivityPengajuan.PengajuanActivity;
import com.bprmaa.mobiles.Helper.Config;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import com.bprmaa.mobiles.R;
import com.bprmaa.mobiles.Storage.SharedPref;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    public static final String API_KEY = "AIzaSyCsjhsD-3-Dp4PZ1ImcDgPJJYnASTR7V14";

    View view;
    ProgressBar progressBar;
    SharedPref s;
    ImageView btn_play, img_image;
    RelativeLayout lyt_video;
    TextView tv_name, tv_desc, tv_time;
    Button btn_pengajuan;

    YouTubePlayerView youTube;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        init(view);
        ListActivity sa = (ListActivity) getActivity();
        String getValue = sa.getValueCategory();

        tv_time.setVisibility(View.GONE);

        //Tampilkan Btn Pengajuan

        if (getValue.equals("promo")) {
            btn_pengajuan.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.VISIBLE);
        }else if (getValue.equals("product")) {
            btn_pengajuan.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.VISIBLE);
        }

        btn_pengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (s.getSudahLogin() == false) {
//                    alertLogin();
                    Intent intent = new Intent(getActivity(), OTPActivity.class);
                    intent.putExtra("d", "d");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), PengajuanActivity.class));
                }
            }
        });

        // Set value
        s = new SharedPref(getActivity());
        tv_name.setText(s.getProductInfo("name"));
        tv_desc.setText(s.getProductInfo("desc"));
//        tv_time.setText("expired: "+ new Helper().convertDateTime(s.getProductInfo("time"), "yyyy-MM-dd hh:mm:s"));

        if (s.getProductInfo("image") != null) {
            Picasso.with(getActivity()).load(s.getProductInfo("image")).into(img_image);
        } else {
            img_image.setImageResource(R.drawable.ic_home);
        }
        webView(Config.URL_Base+"webview/" + getValue + "/" + s.getProductInfo("id"));


        img_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LihatFotoProfilActivity.class);
                intent.putExtra("gambar", s.getProductInfo("image"));
                startActivity(intent);
            }
        });

        Log.d("Respons", "embeded: " + s.getProductInfo("embeded"));

        if (s.getProductInfo("embeded") != "null") {
            btn_play.setVisibility(View.VISIBLE);
        }

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                intent.putExtra("id", s.getProductInfo("embeded"));
                startActivity(intent);
            }
        });

        return view;
    }

    void init(View view) {
        btn_play = view.findViewById(R.id.btn_play);
        lyt_video = view.findViewById(R.id.layout_video);
        btn_pengajuan = view.findViewById(R.id.btn_pengajuan);

        img_image = (ImageView) view.findViewById(R.id.img_photo);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
    }

    private void webView(String url) {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final WebView webView = (WebView) view.findViewById(R.id.wv_pengantar_pdrd);
        WebSettings webSettings = webView.getSettings();
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }

        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl(url);
    }
}
