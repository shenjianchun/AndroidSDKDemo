package com.example.android.view;

import com.example.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * TextSwitcher
 * Created by 14110105 on 2016-04-15.
 */
public class TextSwitcher1 extends Activity implements ViewSwitcher.ViewFactory {

    private int mCounter = 0;
    private TextSwitcher mSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_switcher_1);

        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        mSwitcher = (TextSwitcher) findViewById(R.id.text_switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(fadeIn);
        mSwitcher.setOutAnimation(fadeOut);

        updateCounter();

    }

    public void onClick(View view) {
        mCounter++;
        updateCounter();
    }

    private void updateCounter() {
        mSwitcher.setText(String.valueOf(mCounter));
    }


    @Override
    public View makeView() {
        // 通过ViewFactory， makeView会被调用两次
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(36);

        return textView;
    }
}
