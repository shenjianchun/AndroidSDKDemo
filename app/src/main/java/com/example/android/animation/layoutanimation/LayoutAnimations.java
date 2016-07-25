package com.example.android.animation.layoutanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.view.View;

/**
 * This application demonstrates how to use LayoutTransition to automate transition animations
 * as items are removed from or added to a container.
 */
public class LayoutAnimations extends BaseActivity {
    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_animations;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_grid_fade:
                readyGo(LayoutAnimation1.class);
                break;

            case R.id.btn_list_cascade:
                readyGo(LayoutAnimation2.class);
                break;

            case R.id.btn_reverse_order:
                readyGo(LayoutAnimation3.class);
                break;

            case R.id.btn_randomize:
                readyGo(LayoutAnimation4.class);
                break;

            case R.id.btn_grid_direction:
                readyGo(LayoutAnimation5.class);
                break;
            case R.id.btn_wave_scale:
                readyGo(LayoutAnimation6.class);
                break;

        }
    }

}
