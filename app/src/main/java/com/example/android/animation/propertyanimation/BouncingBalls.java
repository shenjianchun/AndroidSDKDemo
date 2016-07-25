package com.example.android.animation.propertyanimation;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 主要使用propertyAnimtor实现小圆球下落回弹的过程
 * Created by 14110105 on 2016-03-18.
 */
public class BouncingBalls extends BaseActivity {

    @Bind(R.id.container)
    LinearLayout container;

    @Override
    protected void initViewsAndEvents() {

        container.addView(new MyAnimationView(this));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.bouncing_balls;
    }


    public class MyAnimationView extends View {

        private final ArrayList<ShapeHolder> balls = new ArrayList<>();

        private static final int RED = 0xffFF8080;
        private static final int BLUE = 0xff8080FF;
        private static final int CYAN = 0xff80ffff;
        private static final int GREEN = 0xff80ff80;

        public MyAnimationView(Context context) {
            super(context);

            ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", RED, BLUE);
            colorAnim.setDuration(3000);
            colorAnim.setRepeatCount(Animation.INFINITE);
            colorAnim.setRepeatMode(Animation.REVERSE);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() != MotionEvent.ACTION_DOWN
                    && event.getAction() != MotionEvent.ACTION_MOVE) {
                return false;
            }

            final ShapeHolder newBall = addBall(event.getX(), event.getY());

            float startY = newBall.getY();
            float endY = getHeight() - 50f;
            float h = getHeight();
            float eventY = event.getY();
            int duration = (int) (((h - eventY) / h) * 500);

            // 下移动画
            ValueAnimator bounceAnim = ObjectAnimator.ofFloat(newBall, "y", startY, endY);
            bounceAnim.setDuration(duration);
            bounceAnim.setInterpolator(new AccelerateInterpolator());

            ValueAnimator squashAnim1 = ObjectAnimator.ofFloat(newBall, "x", newBall.getX(),
                    newBall.getX() - 25f);
            squashAnim1.setDuration(duration / 4);
            squashAnim1.setRepeatCount(1);
            squashAnim1.setRepeatMode(Animation.REVERSE);
            squashAnim1.setInterpolator(new DecelerateInterpolator());

            ValueAnimator squashAnim2 = ObjectAnimator.ofFloat(newBall, "width", newBall.getWidth(),
                    newBall.getWidth() + 50f);
            squashAnim2.setDuration(duration / 4);
            squashAnim2.setRepeatCount(1);
            squashAnim2.setRepeatMode(ValueAnimator.REVERSE);
            squashAnim2.setInterpolator(new DecelerateInterpolator());

            ValueAnimator stretchAnim1 = ObjectAnimator.ofFloat(newBall, "y", endY,
                    endY + 25f);
            stretchAnim1.setDuration(duration / 4);
            stretchAnim1.setRepeatCount(1);
            stretchAnim1.setRepeatMode(ValueAnimator.REVERSE);
            stretchAnim1.setInterpolator(new DecelerateInterpolator());

            ValueAnimator stretchAnim2 = ObjectAnimator.ofFloat(newBall, "height", newBall.getHeight(),
                    newBall.getHeight() - 25f);
            stretchAnim2.setDuration(duration / 4);
            stretchAnim2.setRepeatCount(1);
            stretchAnim2.setRepeatMode(ValueAnimator.REVERSE);
            stretchAnim2.setInterpolator(new DecelerateInterpolator());

            ValueAnimator bounceBackAnim = ObjectAnimator.ofFloat(newBall, "y", endY,
                    startY);
            bounceBackAnim.setDuration(duration);
            bounceBackAnim.setInterpolator(new DecelerateInterpolator());

            AnimatorSet bouncer = new AnimatorSet();
            bouncer.play(bounceAnim).before(squashAnim1);
            bouncer.play(squashAnim1).with(squashAnim2);
            bouncer.play(squashAnim1).with(stretchAnim1);
            bouncer.play(squashAnim1).with(stretchAnim2);
            bouncer.play(bounceBackAnim).after(squashAnim1);
//            bouncer.start();


            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "alpha", 1f, 0f);
            fadeAnim.setDuration(duration / 4);
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    balls.remove(newBall);
                }
            });


            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(bouncer).before(fadeAnim);
            animatorSet.start();

            return true;
        }

        private ShapeHolder addBall(float x, float y) {

            // 初始化圆形Shape
            OvalShape circle = new OvalShape();
            // 尺寸
            circle.resize(50f, 50f);
            // ShapeDrawable 里面包含Shape和Paint
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);

            shapeHolder.setX(x - 25f);
            shapeHolder.setY(y - 25f);

            int red = (int) (Math.random() * 255);

            int green = (int) (Math.random() * 255);

            int blue = (int) (Math.random() * 255);

            int color = 0xff000000 | red << 16 | green << 8 | blue;
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);

            Paint paint = drawable.getPaint();
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);

            balls.add(shapeHolder);

            return shapeHolder;


        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            for (int i = 0; i < balls.size(); i++) {
                ShapeHolder shapeHolder = balls.get(i);
                canvas.save(); // 保存当前的画布
                // 把canvas的原点在原来的基础上偏移x,y
                canvas.translate(shapeHolder.getX(), shapeHolder.getY());

                shapeHolder.getShape().draw(canvas);

                canvas.restore(); // 恢复画布

            }

        }
    }
}
