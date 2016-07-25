package com.example.android.app;

import com.example.android.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import my.nouilibrary.utils.T;

/**
 * This demonstrates how you can schedule an alarm that causes a service to
 * be started.  This is useful when you want to schedule alarms that initiate
 * long-running operations, such as retrieving recent e-mails.
 * Created by 14110105 on 2016-04-09.
 */
public class AlarmService extends Activity {

    AlarmManager mAlarmManager;
    PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_service);

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mPendingIntent = PendingIntent.getService(this, 0, new Intent(this, AlarmService_Service
                .class), 0);
    }


    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.start_alarm:

                long firstTime = SystemClock.elapsedRealtime();

                mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,
                        15 * 1000, mPendingIntent);

                T.showShort(this, R.string.repeating_scheduled);
                break;

            case R.id.stop_alarm:
                mAlarmManager.cancel(mPendingIntent);
                T.showShort(this, R.string.repeating_unscheduled);
                break;

            default:
                break;
        }
    }

}
