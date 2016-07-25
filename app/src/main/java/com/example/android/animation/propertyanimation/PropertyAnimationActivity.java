package com.example.android.animation.propertyanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.view.View;

import butterknife.OnClick;

/**
 * Property Animation Activity
 * Created by 14110105 on 2016-03-18.
 */
public class PropertyAnimationActivity extends BaseActivity {

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_property_animation;
    }

    @OnClick({R.id.bt_bouncing_balls, R.id.bt_cloning, R.id.bt_layout_animation_default,
    R.id.bt_layout_animation_hideshow, R.id.bt_animation_seeking, R.id.bt_animation_loading})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_bouncing_balls:
                readyGo(BouncingBalls.class);
                break;

            case R.id.bt_cloning:
                readyGo(AnimationCloning.class);
                break;

            case R.id.bt_layout_animation_default:
                readyGo(LayoutAnimationByDefault.class);
                break;

            case R.id.bt_layout_animation_hideshow:
                readyGo(LayoutAnimationHideShow.class);
                break;

            case R.id.bt_animation_seeking:
                readyGo(AnimationSeeking.class);
                break;
            case R.id.bt_animation_loading:
                readyGo(AnimationLoading.class);
                break;
        }
    }

}
