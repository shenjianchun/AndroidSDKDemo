package com.example.android.animation.propertyanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 *
 * Created by 14110105 on 2016-03-18.
 */
public class LayoutAnimationByDefault extends BaseActivity {

    @Bind(R.id.gridContainer)
    GridLayout mGridLayout;

    private int numButtons = 1;

    @Override
    protected void initViewsAndEvents() {



    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_animations_by_default;
    }

    @OnClick(R.id.addNewButton)
    public void onClick(View view) {


        final Button newButton = new Button(this);
        newButton.setText(String.valueOf(numButtons++));
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridLayout.removeView(v);
            }
        });

        mGridLayout.addView(newButton, Math.min(1, mGridLayout.getChildCount()));
    }
}
