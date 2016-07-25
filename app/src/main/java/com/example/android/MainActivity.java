package com.example.android;

import com.example.android.animation.AnimationActivity;
import com.example.android.ui.actionbarcompat.ActionbarCompatActivity;
import com.example.android.ui.menu.MenuActivity;
import com.example.android.ui.notifications.NotificationsActivity;
import com.example.android.ui.systemui.SystemUIVisibility;
import com.uilibrary.app.BaseActivity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void initViewsAndEvents() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_actionbarcompat:
                readyGo(ActionbarCompatActivity.class);
                break;

            case R.id.btn_notifications:
                readyGo(NotificationsActivity.class);
                break;

            case R.id.btn_animation:
                readyGo(AnimationActivity.class);
                break;

            case R.id.btn_menu:
                readyGo(MenuActivity.class);
                break;

            case R.id.btn_system_ui:
                readyGo(SystemUIVisibility.class);
                break;
            default:
                break;
        }

    }

}
