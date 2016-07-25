package com.example.android.view;

import com.example.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;

/**
 * Created by 14110105 on 2016-06-10.
 */
public class ScrollerActivity extends Activity {

    Button mButton;
    Scroller mScroller ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);

        mButton = (Button) findViewById(R.id.button);

        mScroller = new Scroller(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScroller();
            }
        });

    }



    private void startScroller() {


        mScroller.startScroll(mButton.getScrollX(), mButton.getScrollY(), -100, -100);


        mButton.setScroller(mScroller);

    }




}
