package com.example.android.animation.propertyanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.animation.AnimatorSet;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 复制动画
 * Created by 14110105 on 2016-03-18.
 */
public class AnimationCloning extends BaseActivity {

    @Bind(R.id.container)
    LinearLayout container;

    MyAnimationView mAnimationView;

    @Override
    protected void initViewsAndEvents() {
        mAnimationView = new MyAnimationView(this);
        container.addView(mAnimationView);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.animation_cloning;
    }

    @OnClick(R.id.startButton)
    public void onClick(View view) {

        mAnimationView.startAnimation();

    }


    private class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {

        private ArrayList<ShapeHolder> balls = new ArrayList<>();
        private AnimatorSet animator;
        private float mDensity;

        public MyAnimationView(Context context) {
            super(context);
            mDensity = getResources().getDisplayMetrics().density;

            ShapeHolder ball0 = addBall(50f, 25f);
            ShapeHolder ball1 = addBall(150f, 25f);
            ShapeHolder ball2 = addBall(250f, 25f);
            ShapeHolder ball3 = addBall(350f, 25f);

        }


        private void createAnimation() {

            if (animator == null) {
                ObjectAnimator anim1 = ObjectAnimator.ofFloat(balls.get(0), "y",
                        0, getHeight() - balls.get(0).getHeight()).setDuration(500);
                ObjectAnimator anim2 = anim1.clone();
                anim2.setTarget(balls.get(1));

                ShapeHolder ball2 = balls.get(2);
                ObjectAnimator animDown = ObjectAnimator.ofFloat(ball2, "y",
                        0f, getHeight() - ball2.getHeight()).setDuration(500);
                animDown.setInterpolator(new AccelerateInterpolator());
                ObjectAnimator animUp = ObjectAnimator.ofFloat(ball2, "y",
                        getHeight() - ball2.getHeight(), 0f).setDuration(500);
                animUp.setInterpolator(new DecelerateInterpolator());

                AnimatorSet s1 = new AnimatorSet();
                s1.playSequentially(animDown, animUp);
                animDown.addUpdateListener(this);
                animUp.addUpdateListener(this);

                AnimatorSet s2 = s1.clone();
                s2.setTarget(balls.get(3));

                animator = new AnimatorSet();
                animator.playTogether(anim1,anim2,s1);
                animator.playSequentially(s1, s2);


            }

        }

        public void startAnimation() {
            createAnimation();
            animator.start();

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            for (int i = 0; i < balls.size(); i++) {
                ShapeHolder shapeHolder = balls.get(i);
                canvas.save();

                canvas.translate(shapeHolder.getX(), shapeHolder.getY());
                shapeHolder.getShape().draw(canvas);

                canvas.restore();

            }


        }

        private ShapeHolder addBall(float x, float y) {

            OvalShape shape = new OvalShape();
            shape.resize(50f * mDensity, 50f * mDensity);
            ShapeDrawable drawable = new ShapeDrawable(shape);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x - 25);
            shapeHolder.setY(y - 25);

            int red = (int)(100 + Math.random() * 155);
            int green = (int)(100 + Math.random() * 155);
            int blue = (int)(100 + Math.random() * 155);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint(); //new Paint(Paint.ANTI_ALIAS_FLAG);
            int darkColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);

            return shapeHolder;
        }


        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
    }
}
