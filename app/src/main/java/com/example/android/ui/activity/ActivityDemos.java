package com.example.android.ui.activity;

import com.example.android.R;
import com.example.android.ui.activity.tasks.LaunchModeActivity;
import com.uilibrary.app.BaseActivity;

import android.view.View;

/**
 *
 * Created by 14110105 on 2016-03-03.
 */
public class ActivityDemos extends BaseActivity {


    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_demos;
    }


    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_launch_mode:
                readyGo(LaunchModeActivity.class);
                break;

            default:
                break;
        }

    }
}
