package com.bprmaa.mobiles;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import com.bprmaa.mobiles.Helper.ChildAnimationExample;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends Fragment implements ViewPagerEx.OnPageChangeListener {

    private SliderLayout sliderLayout;
    private PagerIndicator indicator;

    private View view;

    public SliderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_slider, container, false);
        initView(view);

        //Call Function
        setupSlider();
        return view;
    }

    private void setupSlider() {

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("BPR 1", R.drawable.slider2);
        file_maps.put("BPR 2", R.drawable.slider1);
        file_maps.put("BPR 3", R.drawable.slider2);

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

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setCustomAnimation(new ChildAnimationExample());
        sliderLayout.setCustomIndicator(indicator);
        sliderLayout.setDuration(4000);
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

    private void initView(View view) {
        sliderLayout = view.findViewById(R.id.sliderSlider);
        indicator = view.findViewById(R.id.custom_indicator);
    }
}
