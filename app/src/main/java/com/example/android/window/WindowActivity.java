package com.example.android.window;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;

/**
 * Window相关的demo，测试添加、删除Window
 *
 * Created by 14110105 on 2016-06-16.
 */
public class WindowActivity extends BaseActivity {

    WindowManager mWindowManager;
    WindowManager.LayoutParams mLayoutParams;

    int mStatusBarHeight;

    @Bind(R.id.btn_add_window)
    Button mAddWindowBtn;
    @Bind(R.id.btn_remove_window)
    Button mRemoveWindowBtn;


    Button mFloatButton;


    NotificationView mNotificationView;
    int mStartX;
    int mStartY;


    @Override
    protected void initViewsAndEvents() {

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        mStatusBarHeight = getStatusBarHeight(this);



        mFloatButton = new Button(this);
//        mFloatButton.setBackgroundColor(Color.GREEN);
        mFloatButton.setText("FloatButton");
        mFloatButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                int offsetX = x - mFloatButton.getWidth() / 2;
                int offsetY = y - mStatusBarHeight - mFloatButton.getHeight() / 2;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        mLayoutParams.x = offsetX;
                        mLayoutParams.y = offsetY;

                        mWindowManager.updateViewLayout(mFloatButton, mLayoutParams);
                        break;

                    default:
                        break;
                }
                return false;
            }
        });




        mAddWindowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNotificationView = new NotificationView(WindowActivity.this);


                mNotificationView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int rawX = (int) event.getRawX();
                        int deltaX = rawX - mStartX;

                        switch (event.getAction()) {

                            case MotionEvent.ACTION_DOWN:
                                mStartX = rawX;
                                mStartY = (int) event.getRawY();
                                break;

                            case MotionEvent.ACTION_MOVE:

                                mLayoutParams.x = deltaX;
                                mLayoutParams.y = 0;

                                mWindowManager.updateViewLayout(mNotificationView, mLayoutParams);


                                break;

                            case MotionEvent.ACTION_UP:

                                if (Math.abs(deltaX) > 240) {
                                    slideOut(mNotificationView);
                                } else {
                                    slideIn(mNotificationView);
                                }

                                break;
                        }


                        return false;
                    }
                });



                mLayoutParams = new WindowManager.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, 112,
                                0, 0, PixelFormat.TRANSLUCENT);
                mLayoutParams.gravity = Gravity.START | Gravity.TOP;
                mLayoutParams.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager
                        .LayoutParams.FLAG_LAYOUT_NO_LIMITS;
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                mLayoutParams.x = 0;
                mLayoutParams.y = 0;

                mWindowManager.addView(mNotificationView, mLayoutParams);

//                // 添加一个浮动的Button
//                mLayoutParams = new WindowManager.LayoutParams
//                        (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
//                                0, 0, PixelFormat.TRANSPARENT);
//                mLayoutParams.gravity = Gravity.START | Gravity.TOP;
//                mLayoutParams.flags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
//                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//                mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//                mLayoutParams.x = 100;
//                mLayoutParams.y = 400;
//
//                mWindowManager.addView(mFloatButton, mLayoutParams);

            }
        });


        mRemoveWindowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mFloatButton != null) {
                    mWindowManager.removeView(mNotificationView);
                    mNotificationView = null;
                }

            }
        });

    }


    private void slideOut(final View view) {

        final IntEvaluator evaluator = new IntEvaluator();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.setDuration(500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();

                float fraction = animation.getAnimatedFraction();

                int x = evaluator.evaluate(fraction, mLayoutParams.x, view.getWidth());

                mLayoutParams.x = x;

                mWindowManager.updateViewLayout(mNotificationView, mLayoutParams);

            }
        });

        valueAnimator.start();

//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "x", view.getTranslationX(),
//                view.getWidth());
//        objectAnimator.setDuration(500);

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                mWindowManager.removeView(mNotificationView);

            }
        });

//        objectAnimator.start();
    }

    private void slideIn(final View view) {
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "x", mLayoutParams.x,
//                0);
//        objectAnimator.setDuration(500);
//        objectAnimator.start();


        final IntEvaluator evaluator = new IntEvaluator();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.setDuration(500);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();

                float fraction = animation.getAnimatedFraction();

                int x = evaluator.evaluate(fraction, mLayoutParams.x, 0);

                mLayoutParams.x = x;

                mWindowManager.updateViewLayout(mNotificationView, mLayoutParams);

            }
        });

        valueAnimator.start();


    }


    private int getStatusBarHeight(Context context) {

        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_window;
    }


    private class NotificationView extends FrameLayout {

        public NotificationView(Context context) {
            super(context);
            init(context);
        }

        public NotificationView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public NotificationView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        public NotificationView(Context context, AttributeSet attrs, int defStyleAttr, int
                defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init(context);
        }

        private void init(Context context) {

            setBackgroundColor(Color.WHITE);

            TextView textView = new TextView(context);
            textView.setTextColor(Color.RED);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            textView.setText("oHHH, 滑动删除！");
            textView.setGravity(Gravity.CENTER_VERTICAL);

            addView(textView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

        }


    }

}
