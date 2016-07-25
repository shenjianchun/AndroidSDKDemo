package com.example.android.app;

import com.example.android.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import java.util.Calendar;

import my.nouilibrary.utils.T;

/**
 * Example of scheduling one-shot and repeating alarms
 * Created by 14110105 on 2016-04-09.
 */
public class AlarmController extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_controller);
    }

    public void onClick(View view) {
        Intent intent;
        PendingIntent pendingIntent;
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        switch (view.getId()) {

            case R.id.one_shot:
                // When the alarm goes off, we want to broadcast an Intent to our
                // BroadcastReceiver.  Here we make an Intent with an explicit class
                // name to have our own receiver (which has been published in
                // AndroidManifest.xml) instantiated and called, and then create an
                // IntentSender to have the intent executed as a broadcast.
                intent = new Intent(this, OneShotAlarm.class);
                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 15);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


                T.showShort(this, R.string.one_shot_scheduled);

                break;

            case R.id.start_repeating:

                intent = new Intent(this, RepeatingAlarm.class);
                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                long  firstTime = SystemClock.elapsedRealtime();
                firstTime += 15 * 1000;

                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstTime,  15 * 1000, pendingIntent);

                T.showShort(this, R.string.repeating_scheduled);
                break;

            case R.id.stop_repeating:

                intent = new Intent(this, RepeatingAlarm.class);
                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.cancel(pendingIntent);

                T.showShort(this, R.string.repeating_unscheduled);
                break;

            default:
                break;

        }

    }
}
