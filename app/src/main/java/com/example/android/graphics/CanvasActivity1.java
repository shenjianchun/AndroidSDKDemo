package com.example.android.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 测试Canvas中的各种draw方法
 * Created by 14110105 on 2016-06-07.
 */
public class CanvasActivity1 extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addContentView(new CustomView(this, null), new ViewGroup.LayoutParams(ViewGroup
                .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }



    private class CustomView extends View {

        private Bitmap mBitmap;

        public CustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
            mBitmap = Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawColor(Color.WHITE);

            drawText(canvas);

            drawCircle(canvas);

            drawLine(canvas);

            drawRect(canvas);

            drawRoundRect(canvas);

            drawOval(canvas);

            drawArc(canvas);
        }


        private void drawLine(Canvas canvas) {

            Paint paint = new Paint();
            paint.setColor(Color.GRAY);

            paint.setStrokeWidth(20);

            canvas.drawLine(0, 200, getWidth() , 200, paint);

        }


        /**
         * 通过Canvas画圆
         * @param canvas
         */
        private void drawCircle(Canvas canvas) {

            Paint paint = new Paint();
            // 去锯齿
            paint.setAntiAlias(true);
            //设置颜色
            paint.setColor(Color.BLUE);
            // 绘制普通圆
            canvas.drawCircle(100, 100, 60, paint);
            // 设置空心Style
            paint.setStyle(Paint.Style.STROKE);
            // 设置空心边框的宽度
            paint.setStrokeWidth(20);
            // 绘制空心圆
            canvas.drawCircle(300, 100, 60, paint);

        }

        /**
         * 通过Canvas画矩形
         * @param canvas
         */
        private void drawRect(Canvas canvas) {


            Paint paint = new Paint();
            // 去锯齿
            paint.setAntiAlias(true);
            // 设置颜色
            paint.setColor(Color.GREEN);
            // 绘制正常矩形
            canvas.drawRect(100, 200, 300, 300, paint);
            // 上面代码等同于
//            Rect rect = new Rect(100, 200, 300, 300);
//            canvas.drawRect(rect, paint);

            // 设置空心Style
            paint.setStyle(Paint.Style.STROKE);
            // 设置空心边框的宽度
            paint.setStrokeWidth(20);

            // 绘制空心矩形
            RectF rectF = new RectF(400, 200, 600, 300);
            canvas.drawRect(rectF, paint);

        }

        /**
         * 通过Canvas绘制圆角矩形
         * @param canvas
         */
        private void drawRoundRect(Canvas canvas) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);

            canvas.drawRoundRect(100, 400, 300, 500, 30, 30, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(20);

            RectF rectF = new RectF(400, 400, 600, 500);
            canvas.drawRoundRect(rectF, 30, 30, paint);

        }


        /**
         * 通过Canvas绘制椭圆
         * @param canvas
         */
        private void drawOval(Canvas canvas) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.YELLOW);

            // 绘制普通椭圆
            canvas.drawOval(100, 600, 300, 700, paint);

            // 设置空心Style
            paint.setStyle(Paint.Style.STROKE);
            // 设置空心边框的宽度
            paint.setStrokeWidth(20);

            // 绘制空心椭圆
            RectF rectF = new RectF(400, 600, 600, 700);
            canvas.drawOval(rectF, paint);

        }

        /**
         * 通过Canvas绘制圆弧
         * @param canvas
         */
        private void drawArc(Canvas canvas) {

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.MAGENTA);

            // 实心圆弧
            canvas.drawArc(50, 800, 150, 900, 0, 270, false, paint);

            // 实心圆弧 将圆形包含在内
            RectF rectF = new RectF(200, 800, 300, 900);
            canvas.drawArc(rectF,0, 270, true, paint);

            // 设置空心Style
            paint.setStyle(Paint.Style.STROKE);
            // 设置空心边框的宽度
            paint.setStrokeWidth(10);

            RectF rectF2 = new RectF(350, 800, 450, 900);
            canvas.drawArc(rectF2,0, 270, false, paint);

            RectF rectF3 = new RectF(500, 800, 600, 900);
            canvas.drawArc(rectF3,0, 270, true, paint);

        }

        private void drawText(Canvas canvas) {

            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setAntiAlias(true);
            paint.setTextSize(60);
            canvas.drawText("Hello World!", 150, 200, paint);
        }

    }

}
