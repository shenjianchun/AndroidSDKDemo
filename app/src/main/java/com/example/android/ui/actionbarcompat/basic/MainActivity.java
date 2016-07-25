package com.example.android.ui.actionbarcompat.basic;


import com.example.android.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import my.nouilibrary.utils.T;

/**
 * Actionbar basic Demo
 * Created by 14110105 on 2016-03-04.
 */
public class MainActivity extends AppCompatActivity {

    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_actionbar_basic_main);
//        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        /* When using the Material themes (default in API 21 or newer) the navigation button
        (formerly "Home") takes over the space previously occupied by the application icon */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar
// .DISPLAY_SHOW_TITLE);
//        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_actionbarcompat_basic, menu);

        MenuItem locationItem = menu.add(Menu.NONE, R.id.menu_location, 0, "Location");
        locationItem.setIcon(R.drawable.ic_action_location);

        MenuItemCompat.setShowAsAction(locationItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_refresh:
//                T.showShort(this, "Here we might start a background refresh task");
                if(mToast == null) {
                    mToast = Toast.makeText(this, "Here we might start a background refresh task", Toast.LENGTH_SHORT);
                } else {
                    mToast.setText("Here we might start a background refresh task");
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
                return true;

            case R.id.menu_location:
                T.showShort(this, "Here we might call LocationManager.requestLocationUpdates()");
                return true;

            case R.id.menu_settings:
                T.showShort(this, "Here we would open up our settings activity");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
