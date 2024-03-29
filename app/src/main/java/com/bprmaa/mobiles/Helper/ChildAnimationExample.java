package com.bprmaa.mobiles.Helper;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.daimajia.slider.library.Animations.BaseAnimationInterface;

/**
 * Created by ASUS on 03/05/2018.
 */

public class ChildAnimationExample implements BaseAnimationInterface {

    private final static String TAG = "ChildAnimationExample";

    @Override
    public void onPrepareCurrentItemLeaveScreen(View current) {
        View descriptionLayout = current.findViewById(com.daimajia.slider.library.R.id.description_layout);
        if(descriptionLayout!=null){
//            current.findViewById(com.daimajia.slider.library.R.id.description_layout).setVisibility(View.INVISIBLE);
            current.findViewById(com.daimajia.slider.library.R.id.description_layout)
                    .setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
        Log.e(TAG,"onPrepareCurrentItemLeaveScreen called");
    }

    @Override
    public void onPrepareNextItemShowInScreen(View next) {
        View descriptionLayout = next.findViewById(com.daimajia.slider.library.R.id.description_layout);
        if(descriptionLayout!=null){
//            next.findViewById(com.daimajia.slider.library.R.id.description_layout).setVisibility(View.INVISIBLE);
            next.findViewById(com.daimajia.slider.library.R.id.description_layout)
                    .setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
        Log.e(TAG,"onPrepareNextItemShowInScreen called");
    }

    @Override
    public void onCurrentItemDisappear(View view) {
        Log.e(TAG,"onCurrentItemDisappear called");
    }

    @Override
    public void onNextItemAppear(View view) {

        View descriptionLayout = view.findViewById(com.daimajia.slider.library.R.id.description_layout);
        if(descriptionLayout!=null){
            view.findViewById(com.daimajia.slider.library.R.id.description_layout)
                    .setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
        Log.e(TAG,"onCurrentItemDisappear called");
    }
}