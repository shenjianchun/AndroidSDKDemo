package com.example.android.animation.drawableanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import butterknife.Bind;

/**
 * Created by 14110105 on 2016-03-22.
 */
public class DrawableAnimation extends BaseActivity {


    @Bind(R.id.iv_loading)
    ImageView mImageView;

    @Override
    protected void initViewsAndEvents() {
        AnimationDrawable drawable = (AnimationDrawable) mImageView.getBackground();
        drawable.start();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.drawable_animation;
    }
}
