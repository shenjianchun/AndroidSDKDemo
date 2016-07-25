package com.example.android.app;

import com.example.android.R;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Foreground Service
 * Created by 14110105 on 2016-05-05.
 */
public class ForegroundService extends Service {

    static final String ACTION_FOREGROUND = "com.example.android.apis.FOREGROUND";
    static final String ACTION_BACKGROUND = "com.example.android.apis.BACKGROUND";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ACTION_FOREGROUND.equals(intent.getAction())){

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, Controller.class), 0);

            Notification notification = new Notification.Builder(this)
                    .setContentTitle("Sample Alarm Service")
                    .setContentText("Service is in the foreground")
                    .setTicker("Service is in the foreground - Ticker")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.stat_sample)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.sample_1))
                    .setWhen(System.currentTimeMillis())
                    .build();

            startForeground(12123, notification);

        } else if (ACTION_BACKGROUND.equals(intent.getAction())) {
            stopForeground(true);
        }


        return START_STICKY;
    }


    public static class Controller extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.foreground_service_controller);

            // Watch for button clicks.
            Button button = (Button)findViewById(R.id.start_foreground);
            button.setOnClickListener(mForegroundListener);
            button = (Button)findViewById(R.id.start_background);
            button.setOnClickListener(mBackgroundListener);
            button = (Button)findViewById(R.id.stop);
            button.setOnClickListener(mStopListener);

        }

        private View.OnClickListener mForegroundListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_FOREGROUND);
                intent.setClass(Controller.this, ForegroundService.class);
                startService(intent);
            }
        };

        private View.OnClickListener mBackgroundListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_BACKGROUND);
                intent.setClass(Controller.this, ForegroundService.class);
                startService(intent);
            }
        };

        private View.OnClickListener mStopListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ACTION_BACKGROUND);
                intent.setClass(Controller.this, ForegroundService.class);
                stopService(intent);
            }
        };


    }


}
