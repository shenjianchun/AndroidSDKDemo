package com.example.android.ui.actionbarcompat;

import com.example.android.R;
import com.example.android.ui.actionbarcompat.basic.MainActivity;
import com.uilibrary.app.BaseActivity;

import android.view.View;

/**
 * Actionbar Compat
 * 关于Actionbar相关的实例总页面
 * Created by 14110105 on 2016-03-04.
 */
public class ActionbarCompatActivity extends BaseActivity {


    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_actionbar_compat;
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_basic:
                readyGo(MainActivity.class);
                break;

            case R.id.btn_list_popup_menu:
                readyGo(com.example.android.ui.actionbarcompat.listpopupmenu.MainActivity.class);
                break;

            case R.id.btn_search_action_provider:
                readyGo(com.example.android.ui.actionbarcompat.shareactionprovider.MainActivity.class);
                break;

            default:
                break;
        }
    }
}
