package com.example.android.animation.viewanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * View Animation
 * Created by 14110105 on 2016-03-15.
 */
public class ViewAnimationActivity extends BaseActivity {

    private static final String[] INTERPOLATORS = {
            "Accelerate", "Decelerate", "Accelerate/Decelerate",
            "Anticipate", "Overshoot", "Anticipate/Overshoot",
            "Bounce"
    };

    @Bind(R.id.pw)
    EditText mPwEt;

    @Bind(R.id.view_flipper)
    ViewFlipper mViewFlipper;
    @Bind(R.id.spinner)
    Spinner mSpinner;
    @Bind(R.id.spinner2)
    Spinner mSpinner2;
    @Bind(R.id.target)
    TextView mTargetTv;

    String[] mStrings = {
            "Push up", "Push left", "Cross fade", "Hyperspace"};

    @Override
    protected void initViewsAndEvents() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(ViewAnimationActivity.this, R.anim.push_up_out));
                        mViewFlipper.setInAnimation(ViewAnimationActivity.this, R.anim.push_up_in);
                        break;

                    case 1:
                        mViewFlipper.setOutAnimation(ViewAnimationActivity.this, R.anim.push_left_out);
                        mViewFlipper.setInAnimation(ViewAnimationActivity.this, R.anim.push_left_in);
                        break;

                    case 2:
                        mViewFlipper.setOutAnimation(ViewAnimationActivity.this, android.R.anim.fade_out);
                        mViewFlipper.setInAnimation(ViewAnimationActivity.this, android.R.anim.fade_in);
                        break;
                    case 3:
                        mViewFlipper.setOutAnimation(ViewAnimationActivity.this, R.anim.hyperspace_out);
                        mViewFlipper.setInAnimation(ViewAnimationActivity.this, R.anim.hyperspace_in);
                        break;

                    default:
                        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(ViewAnimationActivity.this, R.anim.push_up_out));
                        mViewFlipper.setInAnimation(ViewAnimationActivity.this, R.anim.push_up_in);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, INTERPOLATORS);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner2.setAdapter(adapter2);
        mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final View parentView = (View) mTargetTv.getParent();

                TranslateAnimation animation = new TranslateAnimation(0.0f, parentView.getWidth() - mTargetTv.getWidth() -
                        parentView.getPaddingLeft() - parentView.getPaddingRight(), 0.0f, 0.0f);
                animation.setDuration(1000);
                animation.setStartOffset(300);
                animation.setRepeatMode(Animation.RESTART);
                animation.setRepeatCount(Animation.INFINITE);


                switch (position) {
                    case 0:
                        animation.setInterpolator(AnimationUtils.loadInterpolator(ViewAnimationActivity.this,
                                android.R.anim.accelerate_interpolator));
                        break;
                    case 1:
                        animation.setInterpolator(AnimationUtils.loadInterpolator(ViewAnimationActivity.this,
                                android.R.anim.decelerate_interpolator));
                        break;
                    case 2:
                        animation.setInterpolator(AnimationUtils.loadInterpolator(ViewAnimationActivity.this,
                                android.R.anim.accelerate_decelerate_interpolator));
                        break;
                    case 3:
                        animation.setInterpolator(AnimationUtils.loadInterpolator(ViewAnimationActivity.this,
                                android.R.anim.anticipate_interpolator));
                        break;
                    case 4:
                        animation.setInterpolator(AnimationUtils.loadInterpolator(ViewAnimationActivity.this,
                                android.R.anim.overshoot_interpolator));
                        break;
                    case 5:
                        animation.setInterpolator(AnimationUtils.loadInterpolator(ViewAnimationActivity.this,
                                android.R.anim.anticipate_overshoot_interpolator));
                        break;
                    case 6:
                        animation.setInterpolator(AnimationUtils.loadInterpolator(ViewAnimationActivity.this,
                                android.R.anim.bounce_interpolator));
                        break;
                }

                mTargetTv.startAnimation(animation);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mViewFlipper.startFlipping();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_view_animation;
    }

    @OnClick(R.id.login)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:

                Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
                mPwEt.startAnimation(animation);
                break;
        }
    }

}
