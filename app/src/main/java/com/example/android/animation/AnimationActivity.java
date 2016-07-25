package com.example.android.animation;

import com.example.android.R;
import com.example.android.animation.drawableanimation.DrawableAnimation;
import com.example.android.animation.layoutanimation.LayoutAnimations;
import com.example.android.animation.propertyanimation.PropertyAnimationActivity;
import com.example.android.animation.viewanimation.ViewAnimationActivity;
import com.uilibrary.app.BaseActivity;

import android.view.View;

import butterknife.OnClick;

/**
 * Created by 14110105 on 2016-03-15.
 */
public class AnimationActivity extends BaseActivity {

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_animation;
    }


    @OnClick({R.id.bt_view_animation, R.id.bt_property_animation, R.id.bt_drawable_animation,
            R.id.bt_layout_animation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_view_animation:
                readyGo(ViewAnimationActivity.class);
                break;
            case R.id.bt_property_animation:
                readyGo(PropertyAnimationActivity.class);
                break;
            case R.id.bt_drawable_animation:
                readyGo(DrawableAnimation.class);
                break;
            case R.id.bt_layout_animation:
                readyGo(LayoutAnimations.class);
                break;

        }
    }
}
