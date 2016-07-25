package com.example.android.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

/**
 * Touch事件画画
 * Created by 14110105 on 2016-04-06.
 */
public class TouchPaint extends GraphicsActivity {

    /** Used as a pulse to gradually fade the contents of the window. */
    private static final int MSG_FADE = 1;

    /** How often to fade the contents of the window (in ms). */
    private static final int FADE_DELAY = 100;

    /** Menu ID for the command to clear the window. */
    private static final int CLEAR_ID = Menu.FIRST;

    /** Menu ID for the command to toggle fading. */
    private static final int FADE_ID = Menu.FIRST+1;

    /** Background color. */
    static final int BACKGROUND_COLOR = Color.BLACK;

    /** Colors to cycle through. */
    static final int[] COLORS = new int[]{
            Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN,
            Color.CYAN, Color.BLUE, Color.MAGENTA,
    };

    PaintView mPaintView;

    /** Is fading mode enabled? */
    boolean mFading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        mPaintView = new PaintView(this);
        setContentView(mPaintView);


        mPaintView.requestFocus();

        if (savedInstanceState != null) {
            mFading = savedInstanceState.getBoolean("fading", true);
            mPaintView.mColorIndex = savedInstanceState.getInt("color", 0);
        } else {
            mFading = true;
            mPaintView.mColorIndex = 0;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // If fading mode is enabled, then as long as we are resumed we want
        // to run pulse to fade the contents.
        if (mFading) {
            startFading();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopFading();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, CLEAR_ID, 0, "clear");
        menu.add(0, FADE_ID, 0, "Fade").setCheckable(true);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(FADE_ID).setChecked(mFading);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case CLEAR_ID:
                mPaintView.clear();
                return true;

            case FADE_ID:
                mFading = !mFading;
                if (mFading) {
                    startFading();
                } else {
                    stopFading();
                }
                return  true;

            default:

                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Start up the pulse to fade the screen, clearing any existing pulse to
     * ensure that we don't have multiple pulses running at a time.
     */
    void startFading() {
        mHandler.removeMessages(MSG_FADE);
        scheduleFade();
    }

    void stopFading() {
        mHandler.removeMessages(MSG_FADE);
    }

    /**
     * Schedule a fade message for later.
     */
    void scheduleFade() {
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_FADE), FADE_DELAY);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FADE:
                    mPaintView.fade();
                    scheduleFade();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    public static class PaintView extends View {

        private static final int FADE_ALPHA = 0x06;
        private static final int MAX_FADE_STEPS = 256 / (FADE_ALPHA / 2) + 4;

        private Bitmap mBitmap;
        private Canvas mCanvas;

        private final Paint mPaint = new Paint();
        private final Paint mFadePaint = new Paint();
        private int mFadeSteps = MAX_FADE_STEPS;

        private float mCurX;
        private float mCurY;

        /** The index of the current color to use. */
        int mColorIndex;

        public PaintView(Context context) {
            super(context);
            init();
        }

        private void init() {
            setFocusable(true);

            mPaint.setAntiAlias(true);
            mFadePaint.setColor(BACKGROUND_COLOR);
            mFadePaint.setAlpha(FADE_ALPHA);
        }

        public void fade() {
            if (mCanvas != null && mFadeSteps < MAX_FADE_STEPS) {
                mCanvas.drawPaint(mFadePaint);
                invalidate();

                mFadeSteps++;
            }
        }

        public void clear() {
            if (mCanvas != null) {
                mPaint.setColor(BACKGROUND_COLOR);
                mCanvas.drawPaint(mPaint);

                invalidate();
                mFadeSteps = MAX_FADE_STEPS;
            }
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            int curW = mBitmap != null ? mBitmap.getWidth() : 0;
            int curH = mBitmap != null ? mBitmap.getHeight() : 0;

            if (curW < w) {
                curW = w;
            }

            if (curH < h) {
                curH = h;
            }

            Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
            Canvas newCanvas = new Canvas(newBitmap);
            if (mBitmap != null) {
                newCanvas.drawBitmap(mBitmap, 0, 0, null);
            }

            mBitmap = newBitmap;
            mCanvas = newCanvas;
            mFadeSteps = MAX_FADE_STEPS;

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (mBitmap != null) {
                canvas.drawBitmap(mBitmap, 0, 0, null);
            }
        }


        @Override
        public boolean onTouchEvent(MotionEvent event) {

            final int action = event.getActionMasked();

            if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {

                final int N = event.getHistorySize();
                final int P = event.getPointerCount();

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < P; j++) {
                        paint(PaintMode.Draw, event.getHistoricalX(j, i),
                                event.getHistoricalY(j, i),
                                event.getHistoricalPressure(j, i),
                                event.getHistoricalTouchMajor(j, i),
                                event.getHistoricalTouchMinor(j, i),
                                event.getHistoricalOrientation(j, i),
                                event.getHistoricalAxisValue(MotionEvent.AXIS_DISTANCE, j, i),
                                event.getHistoricalAxisValue(MotionEvent.AXIS_TILT, j, i));
                    }
                }

                for (int j = 0; j < P; j++) {
                    paint(PaintMode.Draw,
                            event.getX(j),
                            event.getY(j),
                            event.getPressure(j),
                            event.getTouchMajor(j),
                            event.getTouchMinor(j),
                            event.getOrientation(j),
                            event.getAxisValue(MotionEvent.AXIS_DISTANCE, j),
                            event.getAxisValue(MotionEvent.AXIS_TILT, j));
                }

                mCurX = event.getX();
                mCurY = event.getY();

            }

            return true;
        }

        private void paint(PaintMode mode, float x, float y, float pressure,
                           float major, float minor, float orientation,
                           float distance, float tilt) {
            if (mBitmap != null) {
                if (major <= 0 || minor <= 0) {
                    // If size is not available, use a default value.
                    major = minor = 16;
                }

                switch (mode) {

                    case Draw:
                        mPaint.setColor(COLORS[mColorIndex]);
                        mPaint.setAlpha(Math.min((int) (pressure * 128), 255));
                        drawOval(mCanvas, x, y, major, minor, orientation, mPaint);
                }

            }
            mFadeSteps = 0;
            invalidate();
        }


        /**
         * Draw an oval.
         *
         * When the orienation is 0 radians, orients the major axis vertically,
         * angles less than or greater than 0 radians rotate the major axis left or right.
         */
        private final RectF mReusableOvalRect = new RectF();

        private void drawOval(Canvas canvas, float x, float y, float major, float minor, float
                orientation, Paint paint) {
            canvas.save(Canvas.MATRIX_SAVE_FLAG);
            canvas.rotate((float) (orientation * 180 / Math.PI), x, y);
            mReusableOvalRect.left = x - minor / 2;
            mReusableOvalRect.right = x + minor / 2;
            mReusableOvalRect.top = y - major / 2;
            mReusableOvalRect.bottom = y + major / 2;
            canvas.drawOval(mReusableOvalRect, paint);

            canvas.restore();
        }

    }


    enum PaintMode {
        Draw,
        Splat,
        Erase
    }

}
