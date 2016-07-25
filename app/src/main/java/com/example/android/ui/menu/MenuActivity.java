package com.example.android.ui.menu;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.view.View;

/**
 * Menu Demo
 * Created by 14110105 on 2016-03-23.
 */
public class MenuActivity extends BaseActivity {

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_menu;
    }

    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.btn_menu_inflate_from_xml:
                readyGo(MenuInflateFromXml.class);
                break;
            case R.id.btn_menu_contextual_action_mode:
                readyGo(ContextualActionMode.class);
                break;
        }
    }

}
