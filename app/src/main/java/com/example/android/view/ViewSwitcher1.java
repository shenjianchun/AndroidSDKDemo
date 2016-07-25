package com.example.android.view;

import com.example.android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * ViewSwitcher
 * Created by 14110105 on 2016-04-15.
 */
public class ViewSwitcher1 extends Activity implements ViewSwitcher.ViewFactory{

    private ViewSwitcher mViewSwitcher1;
    private ViewSwitcher mViewSwitcher2;
    private ViewSwitcher mViewSwitcher3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_switcher_1);

        mViewSwitcher1 = (ViewSwitcher) findViewById(R.id.view_switcher_1);
        mViewSwitcher1.setInAnimation(this, android.R.anim.fade_in);
        mViewSwitcher1.setOutAnimation(this, android.R.anim.fade_out);

        mViewSwitcher2 = (ViewSwitcher) findViewById(R.id.view_switcher_2);
        mViewSwitcher2.setInAnimation(this, android.R.anim.slide_in_left);
        mViewSwitcher2.setOutAnimation(this, android.R.anim.slide_out_right);

        mViewSwitcher3 = (ViewSwitcher) findViewById(R.id.view_switcher_3);

        addViews(1);
        addViews(2);

        mViewSwitcher3.setFactory(this);

    }

    private void addViews(int number) {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(24);
        textView.setText("view_switcher_2_" + "textview_" + number);
        mViewSwitcher2.addView(textView);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_prev:
                mViewSwitcher1.showPrevious();
                mViewSwitcher2.showPrevious();
                mViewSwitcher3.showPrevious();
                break;
            case R.id.btn_next:
                mViewSwitcher1.showNext();
                mViewSwitcher2.showNext();
                mViewSwitcher3.showNext();
                break;
        }
    }



    @Override
    public View makeView() {
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(24);
        textView.setText("view_switcher_3_" + "textview");
        return textView;
    }
}
