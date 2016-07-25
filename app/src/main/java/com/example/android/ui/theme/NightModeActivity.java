package com.example.android.ui.theme;

import com.example.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * 夜间模式
 * Created by 14110105 on 2016-05-14.
 */
public class NightModeActivity extends Activity {

    private boolean isNightMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_night_mode);


//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                changeTheme();
//            }
//        });


    }


    public void changeTheme(View view) {
        if (isNightMode) {
            setTheme(R.style.DayTheme);
            getApplication().setTheme(R.style.DayTheme);
            isNightMode = false;
        } else {
            setTheme(R.style.NightTheme);
            getApplication().setTheme(R.style.NightTheme);

            isNightMode =  true;
        }

        setContentView(R.layout.activity_night_mode);
    }

}
