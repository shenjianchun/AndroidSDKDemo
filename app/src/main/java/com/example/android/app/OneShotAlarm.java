package com.example.android.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import my.nouilibrary.utils.T;

/**
 * This is an example of implement an {@link BroadcastReceiver} for an alarm that
 * should occur once.
 * Created by 14110105 on 2016-04-09.
 */
public class OneShotAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        T.showShort(context, "The one-shot alarm has gone off");
    }
}
