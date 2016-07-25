package com.example.android.ui.notifications;

import com.example.android.R;
import com.uilibrary.app.BaseFragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

import butterknife.OnClick;

/**
 * Created by 14110105 on 2016-03-13.
 */
public class CustomNotificationFragment extends BaseFragment {


    private NotificationManager mNotificationManager;


    public static CustomNotificationFragment newInstance() {
        CustomNotificationFragment fragment = new CustomNotificationFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_custom_notification;
    }

    @Override
    protected void initViewsAndEvents() {
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @OnClick(R.id.button)
    public void onClick(View view) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        Intent intent = new Intent(getActivity(), NotificationsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setTicker(getResources().getString(R.string.custom_notification));
        builder.setSmallIcon(R.drawable.ic_stat_notification);
        builder.setAutoCancel(true);

        RemoteViews remoteViews = new RemoteViews(getActivity().getPackageName(), R.layout.notification);
        String time = DateFormat.getTimeInstance().format(new Date());
        String text = getResources().getString(R.string.collapsed, time);
        remoteViews.setTextViewText(R.id.textView, text);
        builder.setContent(remoteViews);

        Notification notification = builder.build();
//        notification.contentView = remoteViews;


        // Add a big content view to the notification if supported.
        // Support for expanded notifications was added in API level 16.
        // (The normal contentView is shown when the notification is collapsed, when expanded the
        // big content view set here is displayed.)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            RemoteViews expandedView = new RemoteViews(getActivity().getPackageName(), R.layout.notification_expanded);
            notification.bigContentView = expandedView;
        }

        mNotificationManager.notify(0, notification);
    }
}
