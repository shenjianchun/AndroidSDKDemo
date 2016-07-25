package com.example.android.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.View;

/**
 * ShapeDrawable 类的使用，包含Shape和Paint
 * Created by 14110105 on 2016-04-06.
 */
public class ShapeDrawable1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
        
    }


    private static class SampleView extends View {

        private ShapeDrawable[] mDrawables;

        private static Shader makeSweep() {

            return new SweepGradient(150, 25, new int[]{0xFFFF0000, 0xFF00FF00, 0xFF0000FF,
                    0xFFFF0000}, null);
        }

        private static Shader makeLinear() {
            return new LinearGradient(0, 0, 50, 50,
                    new int[]{0xFFFF0000, 0xFF00FF00, 0xFF0000FF},
                    null, Shader.TileMode.MIRROR);
        }

        private static Shader makeTiling() {

            int[] pixels = new int[]{0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0};
            Bitmap bm = Bitmap.createBitmap(pixels, 2, 2, Bitmap.Config.ARGB_8888);

            return new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }

        private static class MyShapeDrawable extends ShapeDrawable {
            private Paint mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

            public MyShapeDrawable(Shape s) {
                super(s);
                mStrokePaint.setStyle(Paint.Style.STROKE);
            }

            public Paint getStrokePaint() {
                return mStrokePaint;
            }

            @Override
            protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
                super.onDraw(shape, canvas, paint);
                shape.draw(canvas, paint);
                shape.draw(canvas, mStrokePaint);
            }
        }

        public SampleView(Context context) {
            super(context);
            setFocusable(true);

            float[] outerR = new float[]{12, 12, 12, 12, 0, 0, 0, 0};
            float[] innerR = new float[]{12, 12, 0, 0, 12, 12, 0, 0};
            RectF inset = new RectF(6, 6, 6, 6);

            Path path = new Path();
            path.moveTo(50, 0);
            path.lineTo(0, 50);
            path.lineTo(50, 100);
            path.lineTo(100, 50);
            path.close();

            mDrawables = new ShapeDrawable[7];
            mDrawables[0] = new ShapeDrawable(new RectShape());
            mDrawables[0].getPaint().setColor(0xFFFF0000);
            mDrawables[1] = new ShapeDrawable(new OvalShape());
            mDrawables[1].getPaint().setColor(0xFF00FF00);
            mDrawables[2] = new ShapeDrawable(new RoundRectShape(outerR, null,
                    null));
            mDrawables[2].getPaint().setColor(0xFF0000FF);
            mDrawables[3] = new ShapeDrawable(new RoundRectShape(outerR, inset,
                    null));
            mDrawables[3].getPaint().setShader(makeSweep());
            mDrawables[4] = new ShapeDrawable(new RoundRectShape(outerR, inset,
                    innerR));
            mDrawables[4].getPaint().setShader(makeLinear());
            mDrawables[5] = new ShapeDrawable(new PathShape(path, 100, 100));
            mDrawables[5].getPaint().setShader(makeTiling());
            mDrawables[6] = new MyShapeDrawable(new ArcShape(45, -270));
            mDrawables[6].getPaint().setColor(0x88FF8844);


            PathEffect pe = new DiscretePathEffect(10, 4);
            PathEffect pe2 = new CornerPathEffect(4);
            mDrawables[3].getPaint().setPathEffect(new ComposePathEffect(pe2, pe));

            MyShapeDrawable msd = (MyShapeDrawable) mDrawables[6];
            msd.getStrokePaint().setStrokeWidth(4);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int x = 10;
            int y = 10;
            int width = 300;
            int height = 50;

            for (ShapeDrawable drawable : mDrawables) {
                drawable.setBounds(x, y, x + width, y + height);
                drawable.draw(canvas);
                y += height + 5;
            }

        }
    }
}
