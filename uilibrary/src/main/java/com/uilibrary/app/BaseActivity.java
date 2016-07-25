package com.uilibrary.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import butterknife.ButterKnife;
import my.uilibrary.R;


/**
 * Activity基类
 * Created by 14110105 on 2015/9/25.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /**
     * Toolbar
     */
    protected Toolbar mToolbar;

    /**
     * overridePendingTransition mode
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (toggleOverridePendingTransition()) {

            switch (getOverridePendingTransitionMode()) {

                case LEFT:
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;

                case RIGHT:
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;

                case TOP:
                    overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                    break;

                case BOTTOM:
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                    break;

                case SCALE:
                    overridePendingTransition(R.anim.grow_fade_in_center, R.anim.shrink_fade_out_center);
                    break;

                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;

                default:
                    break;

            }
        }

        super.onCreate(savedInstanceState);

        // 判断是否指定了Layout
        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;

        initViewsAndEvents();

    }


    /**
     * toggle overridePendingTransition
     *
     * @return true means enable, false means disable
     */
    protected  boolean toggleOverridePendingTransition() {
        return false;
    }

    /**
     * get the overridePendingTransition mode
     */
    protected  TransitionMode getOverridePendingTransitionMode() {

        return TransitionMode.LEFT;
    }

    @Override
    public void finish() {
        super.finish();

        if (toggleOverridePendingTransition()) {

            switch (getOverridePendingTransitionMode()) {

                case LEFT:
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;

                case RIGHT:
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    break;

                case TOP:
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                    break;

                case BOTTOM:
                    overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                    break;


                case SCALE:
                    overridePendingTransition(R.anim.grow_fade_in_center, R.anim.shrink_fade_out_center);
                    break;

                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;

                default:
                    break;

            }

        }
    }



    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        // ButterKnife绑定控件
        ButterKnife.bind(this);

        // 初始化并设置Toolbar
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            setHomeButtonEnabled();
        }

    }

    /**
     * 显示Toolbar左边的返回按钮并添加finish事件
     */
    protected void setHomeButtonEnabled() {
        if (mToolbar != null) {

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();

    /**
     * get layout resource
     *
     * @return layout resource id
     */
    protected abstract int getLayoutResource();





    /**************************************分割线*********************************************/

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
