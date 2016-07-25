package com.example.android.ui.systemui;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.view.View;

/**
 * System UI Demo
 * Created by 14110105 on 2016-03-28.
 */
public class SystemUIVisibility extends BaseActivity {

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.system_ui_visibility;
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_content_browser:
                readyGo(ContentBrowser.class);
                break;
            case R.id.btn_system_ui_mode:
                readyGo(SystemUIModes.class);
                break;
        }

    }
}
