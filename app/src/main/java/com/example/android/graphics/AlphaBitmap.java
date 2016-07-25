package com.example.android.graphics;

import com.example.android.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

import java.io.InputStream;

/**
 * Created by 14110105 on 2016-04-05.
 */
public class AlphaBitmap extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new SampleView(this));
    }

    private static class SampleView extends View {

        private Bitmap mBitmap;
        private Bitmap mBitmap2;
        private Bitmap mBitmap3;
        private Shader mShader;


        public SampleView(Context context) {
            super(context);
            setFocusable(true);

            InputStream is = getResources().openRawResource(R.raw.app_sample_code);
            mBitmap = BitmapFactory.decodeStream(is);
            mBitmap2 = mBitmap.extractAlpha();
            mBitmap3 = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);

            drawIntoBitmap(mBitmap3);

            mShader = new LinearGradient(0, 0, 100, 70, new int[]{Color.RED, Color.GREEN, Color
                    .BLUE}, null, Shader.TileMode.MIRROR);

        }

        private static void drawIntoBitmap(Bitmap bm) {
            float x = bm.getWidth();
            float y = bm.getHeight();
            Canvas canvas = new Canvas(bm);
            Paint paint = new Paint();
            paint.setAntiAlias(true);

            paint.setAlpha(0x80);
            canvas.drawCircle(x / 2, y / 2, x / 2, paint);

            paint.setAlpha(0x30);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            paint.setTextSize(60);
            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fm = paint.getFontMetrics();
            canvas.drawText("Alpha", x / 2, (y - fm.ascent) / 2, paint);


        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawColor(Color.WHITE);

            Paint paint = new Paint();
            float y = 10;

            paint.setColor(Color.RED);
            canvas.drawBitmap(mBitmap, 10, y, paint);

            y += mBitmap.getHeight() + 10;
            canvas.drawBitmap(mBitmap2, 10, y, paint);

            y += mBitmap2.getHeight() + 10;
            paint.setShader(mShader);
            canvas.drawBitmap(mBitmap3, 10, y, paint);

        }
    }

}
