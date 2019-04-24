package com.bprmaa.mobiles.Tentang;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisiFragment extends Fragment {

    private static final String TAG = "VisiFragment";
    private List<DataModel> mItemVisi = new ArrayList<>();
    private RecyclerView recyclerViewVisi;
    private RecyclerView.Adapter mAdapter;

    private TextView txt_visi;
    private String visi;
    private TextView misi, alertVisi, alertMisi;
    private View view;
    private ProgressBar progressBarVisi, progressBarMisi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_visi, container, false);

//        recyclerViewVisi = (RecyclerView) view.findViewById(R.id.rv_visi);
        alertMisi = view.findViewById(R.id.tv_aleretMisi);
        alertVisi = view.findViewById(R.id.tv_aleretVisi);
        progressBarVisi = (ProgressBar) view.findViewById(R.id.progressBarVisi);
        progressBarMisi = (ProgressBar) view.findViewById(R.id.progressBar);

        loadData();

        return view;
    }

    void loadData() {
        ApiService api = ApiConfig.getInstanceRetrofit();
        // GET VISI
        api.getAbout().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    webViewMisi("http://android.bprmaa.co.id/webview/visi");
                    webViewVisi("http://android.bprmaa.co.id/webview/misi");
                } else {
                    SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Gagal Memuat data");
                    dialogKoneksi.show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops")
                        .setContentText("Gagal Memuat data");
                dialogKoneksi.show();
                alertMisi.setVisibility(View.VISIBLE);
                alertVisi.setVisibility(View.VISIBLE);
                progressBarMisi.setVisibility(View.GONE);
                progressBarVisi.setVisibility(View.GONE);
            }
        });

    }

    private void webViewVisi(String url) {

        final WebView webView = (WebView) view.findViewById(R.id.wv_visi);
        WebSettings webSettings = webView.getSettings();
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBarVisi.setVisibility(View.GONE);
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

    private void webViewMisi(String url) {
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
                progressBarMisi.setVisibility(View.GONE);
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

    void displayView() {

        //Visi
//        mAdapter = new VisiAdapter(getActivity(), mItemVisi);
//        recyclerViewVisi.setLayoutManager(new GridLayoutManager(getActivity(), 1));
//        recyclerViewVisi.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();

        //Direksi
//        mAdapter = new ManagementAdapter(getActivity(), mItemDireksi);
//        recyclerViewDireksi.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//        recyclerViewDireksi.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged();
    }
}

