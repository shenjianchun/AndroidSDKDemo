package com.example.android.graphics;

import com.example.android.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 14110105 on 2016-06-07.
 */
public class CanvasActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addContentView(new CustomView(this, null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }


    private class CustomView extends View {

        public CustomView(Context context) {
            super(context);
        }

        public CustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawColor(Color.WHITE);

//            canvasTranslate(canvas);

//            canvasRotate(canvas);

//            canvasClip(canvas);

//            canvasSaveAndRestore(canvas);

            drawXfermode(canvas);

//            drawBitmapWithMatrix(canvas);
        }

        private void canvasTranslate(Canvas canvas) {

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setTextSize(50);

            canvas.drawText("蓝色字体为Translate前所画", 0, 100, paint);

            canvas.translate(100, 200);

            paint.setColor(Color.BLACK);

            canvas.drawText("黑色字体为Translate后所画", 0, 0, paint);

        }

        private void canvasRotate(Canvas canvas) {

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setTextSize(50);

            canvas.translate(0, 200);

            canvas.drawText("Hello World!", 0, 0, paint);

            canvas.rotate(30);
            paint.setColor(Color.BLACK);

            canvas.drawText("Hello World!", 0, 0, paint);

        }

        private void canvasClip(Canvas canvas) {

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setTextSize(50);

            canvas.translate(0, 100);

            canvas.drawText("绿色部分为Canvas剪裁前的区域", 20, 0, paint);

            canvas.clipRect(20, 100, 800, 400);

            canvas.drawColor(Color.YELLOW);
            paint.setColor(Color.BLACK);

            canvas.drawText("黄色部分为Canvas剪裁后的区域", 10, 300, paint);

        }


        /**
         * 使用Canvas.save and Canvas.restore
         * @param canvas
         */
        private void canvasSaveAndRestore(Canvas canvas) {

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setTextSize(50);

            canvas.translate(0, 100);

            canvas.drawText("绿色部分为Canvas剪裁前的区域", 20, 0, paint);

            canvas.save();


            canvas.clipRect(20, 200, 800, 500);

            canvas.drawColor(Color.YELLOW);
            paint.setColor(Color.BLACK);

            canvas.drawText("黄色部分为Canvas剪裁后的区域", 10, 300, paint);

            canvas.restore();

            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            canvas.drawText("canvas.restore()之后的文字", 10, 120, paint);

        }


        private void drawXfermode(Canvas canvas) {

            canvas.translate(50, 50);

            Bitmap bitmap = Bitmap.createBitmap(400, 300, Bitmap.Config.ARGB_8888);

            Canvas newCanvas = new Canvas(bitmap);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

//            Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frantic, options);
            Bitmap srcBitmap = makeSrc(500, 400);

            Paint paint = new Paint();
            paint.setColor(Color.YELLOW);
            paint.setAntiAlias(true);

            Rect rect = new Rect(0, 0, 400, 300);

            RectF rectF = new RectF(rect);
            newCanvas.drawRoundRect(rectF, 30, 30, paint);

            PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
            paint.setXfermode(xfermode);

            newCanvas.drawBitmap(srcBitmap, 0, 0, paint);

            canvas.drawBitmap(bitmap, 0, 0, null);
        }


        public Bitmap makeSrc(int w, int h) {

            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bitmap);

            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

            LinearGradient gradient = new LinearGradient(0, 0 , 0, h,
                    Color.GREEN, Color.BLUE, Shader.TileMode.REPEAT);
            p.setShader(gradient);
            p.setStyle(Paint.Style.FILL);
            c.drawRect(0, 0 , w, h, p);

            return bitmap;
        }


        private void drawBitmapWithMatrix(Canvas canvas) {


            canvas.translate(50, 50);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.frantic, options);

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Matrix matrix = new Matrix();

            canvas.drawBitmap(bitmap, matrix, paint);

            matrix.postTranslate(width/3, height);
            canvas.drawBitmap(bitmap, matrix, paint);

            matrix.postScale(0.5f, 0.5f);
            canvas.drawBitmap(bitmap, matrix, paint);

        }

    }

}
