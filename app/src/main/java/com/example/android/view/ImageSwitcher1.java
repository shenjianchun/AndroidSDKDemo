package com.example.android.view;

import com.example.android.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * ImageSwitcher
 * Created by 14110105 on 2016-04-15.
 */
public class ImageSwitcher1 extends Activity implements ViewSwitcher.ViewFactory {

    private ImageSwitcher mSwitcher;
    private int mCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_switcher_1);

        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        mSwitcher = (ImageSwitcher) findViewById(R.id.image_switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(fadeIn);
        mSwitcher.setOutAnimation(fadeOut);

        updateImage();
    }

    public void onClick(View view) {
        mCounter++;
        updateImage();
    }

    public void updateImage() {
        int res;
        switch (mCounter % 10) {

            case 2:
                res = R.drawable.loading_02;
                break;

            case 3:
                res = R.drawable.loading_03;
                break;

            case 4:
                res = R.drawable.loading_04;
                break;


            case 5:
                res = R.drawable.loading_05;
                break;

            case 6:
                res = R.drawable.loading_06;
                break;

            case 7:
                res = R.drawable.loading_07;
                break;

            case 8:
                res = R.drawable.loading_08;
                break;

            case 9:
                res = R.drawable.loading_09;
                break;

            default:
                res = R.drawable.loading_01;
                break;
        }

        mSwitcher.setImageResource(res);

    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundColor(Color.WHITE);

        return imageView;
    }
}
