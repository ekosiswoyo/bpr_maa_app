package com.bprmaa.mobiles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bprmaa.mobiles.Activity.OTPActivity;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bprmaa.mobiles.Activity.GeneralActivity;
import com.bprmaa.mobiles.Activity.ListActivity;
import com.bprmaa.mobiles.ActivityPengajuan.PengajuanActivity;
import com.bprmaa.mobiles.Adapter.PromoAdapter;
import com.bprmaa.mobiles.App.ApiConfig;
import com.bprmaa.mobiles.App.ApiService;
import com.bprmaa.mobiles.App.DataModel;
import com.bprmaa.mobiles.App.ResponModel;
import com.bprmaa.mobiles.Helper.ChildAnimationExample;
import com.bprmaa.mobiles.Helper.ItemClickSupport;
import com.bprmaa.mobiles.Product.ProductActivity;
import com.bprmaa.mobiles.Storage.SharedPref;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ViewPagerEx.OnPageChangeListener {

    private static final String LIST_STATE = "List";
    private static final String BUNDLE_RECYCLER = "recycler_layout";
    private static Bundle mBundleRecyclerViewState;

    private SliderLayout sliderLayout;
    private ImageView homeIvInfo, homeIvProduct, homeIvPengajuan, homeIvNews;
    private ImageView cvCarrer, cvKurs, cvCommodity, cvLayanan, cvPromo, cvLelang;
    private ScrollView scrollView;
    private PagerIndicator indicator;
    private LinearLayout layoutInfo, layoutPromo, layoutNews;

    private RecyclerView listLelang;
    private RecyclerView listPromo;
    //    private RecyclerView listInfo;
    private List<DataModel> mItemPromo = new ArrayList<>();
    private List<DataModel> mItemNews = new ArrayList<>();
    private List<DataModel> mItemInfo = new ArrayList<>();
    private List<DataModel> mItemSlider = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private TextView btn_selengkapnya_p, btn_selengkapnya_l;
    private SharedPref s;

    private Intent intent;
    private Menu menu;
    private View view;

    private String gambar, token, namaPromo, slider;
//    private String expired = "2019-02-15 16:56:00";
    private boolean info = false;

    private List<DataModel> l;
    private ArrayList<DataModel> list;

    private ProgressDialog pd;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);

//        list = new ArrayList<>();
//        list.addAll(InfoData.getListData());
//        listInfo.setHasFixedSize(true);
//        listInfo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        InfoAdapter infoAdapter = new InfoAdapter(getActivity());
//        infoAdapter.setListPresident(list);
//        listInfo.setAdapter(infoAdapter);

        //Call Function
        setupSlider();
        HomeButton();
        loadData();
        buttonInfo();

        return view;
    }

    private void loadData() {
        ApiService api = ApiConfig.getInstanceRetrofit();
        api.getListDashboard().enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {

                if (response.isSuccessful()) {

                    l = response.body().getSlider();
                    if (l != null) {
                        for (final DataModel d : l) {

                            slider = d.getImage();

                            String gb = d.getLink().replace("/", " ");
                            final String strArray[] = gb.split(" ");

                            Log.d("respons", ", " + d.getLink() + "Status" + strArray[4]);

                            TextSliderView textSliderView = new TextSliderView(getActivity());
                            textSliderView
                                    .image(slider)
                                    .setScaleType(BaseSliderView.ScaleType.CenterInside).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    pd = new ProgressDialog(getActivity());
                                    pd.setMessage("Getting data...");
                                    pd.setCancelable(false);
                                    pd.show();

                                    Log.d("Slider", d.getImage() + d.getLink());
                                    ApiService api = ApiConfig.getInstanceRetrofit();
                                    api.getUrl(d.getLink()).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            pd.dismiss();

                                            if (response.isSuccessful()) {

                                                try {
                                                    JSONObject j = new JSONObject(response.body().string());
                                                    JSONObject r = j.getJSONObject("result");
                                                    String id = r.getString("id");
                                                    String name = r.getString("name");
                                                    String embeded = "null";
                                                    String short_desc = r.getString("short_desc");
                                                    String image = r.getString("image");
//                                                    if (strArray[4] == "promo"){
//                                                        expired = r.getString("expired");
//                                                    }
                                                    Log.d("Respon get slider ", "berhasil :" + name);

                                                    intent = new Intent(getActivity(), ListActivity.class);
                                                    intent.putExtra("jenis", "detail");
                                                    intent.putExtra("katagori", strArray[4]);
                                                    s.setProductInfo(id, name, embeded, short_desc, image);
                                                    startActivity(intent);


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Toast.makeText(getActivity(), "Gagal Mengambil data", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            pd.dismiss();
                                            Log.d("Respon get slider ", "gagal");
                                            Toast.makeText(getActivity(), "Gagal Mengambil data", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle();
//                            .putString("extra", name);

                            sliderLayout.addSlider(textSliderView);
                        }
                    }
                }

                if (response.isSuccessful()) {
//                    Log.d("RETRO", "response : " + response.body().getPesan());
                    mItemPromo = response.body().getPromo();
                    mItemNews = response.body().getNews();
                    mItemSlider = response.body().getSlider();
                    displayRecycler();

                    listPromo.setVisibility(View.VISIBLE);
                    listLelang.setVisibility(View.VISIBLE);
                    layoutPromo.setVisibility(View.GONE);
                    layoutNews.setVisibility(View.GONE);
                } else {
//                    Log.d("RETRO", "response : " + response.body().getPesan());

                }
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {

                SweetAlertDialog dialogKoneksi = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops")
                        .setContentText("Gagal Memuat data");
                dialogKoneksi.show();

                HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
                file_maps.put("BPR 1", R.drawable.slider2);
                file_maps.put("BPR 2", R.drawable.slider1);
                file_maps.put("BPR 3", R.drawable.slider2);

                listPromo.setVisibility(View.GONE);
                listLelang.setVisibility(View.GONE);
                layoutPromo.setVisibility(View.VISIBLE);
                layoutNews.setVisibility(View.VISIBLE);
                layoutPromo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadData();
                    }
                });
                layoutNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadData();
                    }
                });

//                String url = "http://103.82.242.173/maa/storage/files/20181115151131_1.png";

                for (String name : file_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    textSliderView
                            .image(file_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    sliderLayout.addSlider(textSliderView);
                }
            }
        });
    }

    private void displayRecycler() {

        //PROMO
        mAdapter = new PromoAdapter(getActivity(), mItemPromo);
        listPromo.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        listPromo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        listPromo.setHasFixedSize(true);

        ItemClickSupport.addTo(listPromo).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showItemSelectedPromo(mItemPromo.get(position));
            }
        });

        // LELANG
        mAdapter = new PromoAdapter(getActivity(), mItemNews);
        listLelang.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        listLelang.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        listLelang.setHasFixedSize(true);

        ItemClickSupport.addTo(listLelang).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showItemSelectedNews(mItemNews.get(position));
            }
        });
    }

    private void showItemSelectedPromo(DataModel mItem) {
        intent = new Intent(getActivity(), ListActivity.class);
        intent.putExtra("jenis", "detail");
        intent.putExtra("katagori", "promo");

//        Log.d("tanggal", "tgl: " + mItem.getExpired());
        s.setProductInfo(mItem.getId(), mItem.getName(), mItem.getEmbeded(), mItem.getShort_desc(), mItem.getImage());
        startActivity(intent);
    }

    private void showItemSelectedNews(DataModel mItem) {
        intent = new Intent(getActivity(), ListActivity.class);
        intent.putExtra("jenis", "detail");
        intent.putExtra("katagori", "news");
        s.setProductInfo(mItem.getId(), mItem.getName(), mItem.getEmbeded(), mItem.getShort_desc(), mItem.getImage());
        startActivity(intent);
    }

    private void HomeButton() {
        homeIvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), GeneralActivity.class));
            }
        });

        homeIvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProductActivity.class));
            }
        });


        homeIvPengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s.getSudahLogin() == false) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("a", "c");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), PengajuanActivity.class));
                }
            }
        });

//        homeIvNews.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (info == false) {
//                    layoutInfo.setVisibility(View.VISIBLE);
//                    info = true;
//                } else if (info == true) {
//                    layoutInfo.setVisibility(View.GONE);
//                    info = false;
//                }
//            }
//        });

        btn_selengkapnya_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("jenis", "promo");
                intent.putExtra("data", "Isi Data ");
                startActivity(intent);
            }
        });

        btn_selengkapnya_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("jenis", "news");
                intent.putExtra("data", "Isi Data ");
                startActivity(intent);

            }
        });
    }

    void hiden() {
        layoutInfo.setVisibility(View.GONE);
        info = false;
    }

    private void setupSlider() {

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setCustomAnimation(new ChildAnimationExample());
        sliderLayout.setCustomIndicator(indicator);
        sliderLayout.setDuration(8000);
        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void buttonInfo() {
        //Button Info
        cvCarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (s.getSudahLogin() == false) {
//                alertLogin();
                    Intent intent = new Intent(getActivity(), OTPActivity.class);
                    intent.putExtra("d", "d");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ListActivity.class);
                    intent.putExtra("jenis", "career");
                    startActivity(intent);
                }

//                intent = new Intent(getActivity(), ListActivity.class);
//                intent.putExtra("jenis", "news");
//                startActivity(intent);
            }
        });

        cvKurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("jenis", "kurs");
                startActivity(intent);
//                hiden();
            }
        });

        cvCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("jenis", "commodity");
                startActivity(intent);
//                hiden();
            }
        });

        cvLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("jenis", "layanan");
                startActivity(intent);
//                hiden();
            }
        });

//        cvPromo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent = new Intent(getActivity(), ListActivity.class);
//                intent.putExtra("jenis", "promo");
//                startActivity(intent);
//                hiden();
//            }
//        });

        cvLelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("jenis", "lelang");
                startActivity(intent);
//                hiden();
            }
        });
    }

    private void initView(View view) {
        s = new SharedPref(getActivity());
        token = s.getUserInfo("token");

        listLelang = (RecyclerView) view.findViewById(R.id.rv_lelang);
        listPromo = (RecyclerView) view.findViewById(R.id.rv_promo);
        sliderLayout = view.findViewById(R.id.sliderSlider);
        indicator = view.findViewById(R.id.custom_indicator);
        homeIvInfo = view.findViewById(R.id.home_iv_info);
        homeIvProduct = view.findViewById(R.id.home_iv_product);
        homeIvPengajuan = view.findViewById(R.id.home_iv_pengajuan);

        cvCarrer = view.findViewById(R.id.cvCarrer);
        cvKurs = view.findViewById(R.id.cvKurs);
        cvCommodity = view.findViewById(R.id.cvCommodity);
        cvLayanan = view.findViewById(R.id.cvLayanan);
        cvLelang = view.findViewById(R.id.cvLelang);

        layoutInfo = view.findViewById(R.id.layout_info);
        layoutPromo = view.findViewById(R.id.layout_promo);
        layoutNews = view.findViewById(R.id.layout_news);

        btn_selengkapnya_p = view.findViewById(R.id.btn_selengkapnya_p);
        btn_selengkapnya_l = view.findViewById(R.id.btn_selengkapnya_l);
    }
}
