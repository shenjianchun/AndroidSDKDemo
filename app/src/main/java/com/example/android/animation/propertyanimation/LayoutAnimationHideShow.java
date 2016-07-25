package com.example.android.animation.propertyanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * This application demonstrates how to use LayoutTransition to automate transition animations
 * as items are hidden or shown in a container.
 */
public class LayoutAnimationHideShow extends BaseActivity {

    @Bind(R.id.parent)
    LinearLayout mParent;
    @Bind(R.id.hideGoneCB)
    CheckBox mhideGoneCB;


    private int numButtons = 1;
    ViewGroup mContainer;
    private LayoutTransition mTransition;

    @Override
    protected void initViewsAndEvents() {

        mContainer = new LinearLayout(this);
        mContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        for (int i = 0; i < 4; i++) {
            Button button = new Button(this);
            button.setText(String.valueOf(numButtons++));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(mhideGoneCB.isChecked() ? View.GONE : View.INVISIBLE);
                }
            });

            mContainer.addView(button);
        }

        mParent.addView(mContainer);

        resetTransition();

    }

    private void resetTransition() {
        mTransition = new LayoutTransition();
        mContainer.setLayoutTransition(mTransition);
    }

    @OnClick(R.id.addNewButton)
    public void onClick() {
        // 显示所有按钮
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            View view = mContainer.getChildAt(i);
            view.setVisibility(View.VISIBLE);
        }

    }

    @OnCheckedChanged(R.id.customAnimCB)
    public void onCheckChanged(CompoundButton buttonView, boolean isChecked) {

        long duration;
        if (isChecked) {

            mTransition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
            mTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
            setupCustomAnimations();
            duration = 500;
        } else {
            resetTransition();
            duration = 300;
        }
        mTransition.setDuration(duration);
    }

    private void setupCustomAnimations() {

        PropertyValuesHolder pvLeft = PropertyValuesHolder.ofInt("left", 0, 1);
        PropertyValuesHolder pvTop = PropertyValuesHolder.ofInt("top", 0, 1);
        PropertyValuesHolder pvRight = PropertyValuesHolder.ofInt("right", 0, 1);
        PropertyValuesHolder pvBottom = PropertyValuesHolder.ofInt("bottom", 0, 1);

        // Changing while add
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
        ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(mContainer, pvLeft, pvTop,
                pvRight, pvBottom, pvhScaleX, pvhScaleY)
                .setDuration(mTransition.getDuration(LayoutTransition.CHANGE_APPEARING));
        changeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((View) ((ObjectAnimator) animation).getTarget()).setScaleX(1f);
                ((View) ((ObjectAnimator) animation).getTarget()).setScaleY(1f);
            }
        });
        mTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);

        //Changing while remove
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f); // 第一个参数为时间百分比，第二个参数是在第一个参数的时间时的属性值。
        Keyframe kf1 = Keyframe.ofFloat(0.9999f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder prvRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(this, pvLeft, pvTop,
                pvRight, pvBottom, prvRotation).setDuration(mTransition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        changeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((View) ((ObjectAnimator) animation).getTarget()).setRotation(0f);
            }
        });
        mTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);

        // Adding
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f)
                .setDuration(mTransition.getDuration(LayoutTransition.APPEARING));
        animIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((View) ((ObjectAnimator) animation).getTarget()).setRotationY(0f);
            }
        });
        mTransition.setAnimator(LayoutTransition.APPEARING, animIn);

        // Removing
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotationX", 0, 90f)
                .setDuration(mTransition.getDuration(LayoutTransition.DISAPPEARING));
        animOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((View) ((ObjectAnimator) animation).getTarget()).setRotationX(0);
            }
        });
        mTransition.setAnimator(LayoutTransition.DISAPPEARING, animOut);


    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_animations_hideshow;
    }
}
