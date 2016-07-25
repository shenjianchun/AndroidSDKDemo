package com.example.android.animation.propertyanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * This application demonstrates the seeking capability of ValueAnimator. The SeekBar in the
 * UI allows you to set the position of the animation. Pressing the Run button will play from
 * the current position of the animation.
 */
public class AnimationSeeking extends BaseActivity {


    private static final int DURATION = 1500;

    @Bind(R.id.container)
    LinearLayout mContainer;
    @Bind(R.id.seekBar)
    SeekBar mSeekBar;
    MyAnimationView animationView;

    @Override
    protected void initViewsAndEvents() {
        animationView = new MyAnimationView(this);
        mContainer.addView(animationView);


        mSeekBar.setMax(DURATION);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (animationView.getHeight() != 0) {
                    animationView.seek(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.animation_seeking;
    }

    @OnClick(R.id.startButton)
    public void onClick(View view) {
        animationView.startAnimation();
    }


    private class MyAnimationView extends View implements Animator.AnimatorListener,
            ValueAnimator.AnimatorUpdateListener {

        private static final int RED = 0xffFF8080;
        private static final int BLUE = 0xff8080FF;
        private static final int CYAN = 0xff80ffff;
        private static final int GREEN = 0xff80ff80;
        private static final float BALL_SIZE = 100f;


        public final ArrayList<ShapeHolder> balls = new ArrayList<>();
        ValueAnimator bounceAnim;
        ShapeHolder ball;


        public MyAnimationView(Context context) {
            super(context);
            ball = addBall(200, 0);
        }


        private ShapeHolder addBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(BALL_SIZE, BALL_SIZE);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            int red = (int) (100 + Math.random() * 155);
            int green = (int) (100 + Math.random() * 155);
            int blue = (int) (100 + Math.random() * 155);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint();
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);
            return shapeHolder;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.translate(ball.getX(), ball.getY());

            ShapeDrawable shapeDrawable = ball.getShape();
            shapeDrawable.draw(canvas);

        }

        public void seek(int progress) {

            createAnimation();
            bounceAnim.setCurrentPlayTime(progress);
        }

        public void createAnimation() {
            if (bounceAnim == null) {
                bounceAnim = ObjectAnimator.ofFloat(ball, "y", ball.getY(), getHeight() - BALL_SIZE)
                        .setDuration(DURATION);
                bounceAnim.setInterpolator(new BounceInterpolator());
                bounceAnim.addUpdateListener(this);
            }
        }

        public void startAnimation() {
            createAnimation();
            bounceAnim.start();
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
