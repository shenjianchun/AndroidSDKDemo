package com.example.android.ui.notifications;

import com.example.android.R;
import com.example.android.ui.actionbarcompat.basic.MainActivity;
import com.uilibrary.app.BaseFragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import butterknife.OnClick;

/**
 * Basic notification example
 * Created by 14110105 on 2016-03-10.
 */
public class BasicNotificationFragment extends BaseFragment {


    NotificationManager mNotificationManager;

    public BasicNotificationFragment() {
    }

    public static BasicNotificationFragment newInstance() {

        BasicNotificationFragment fragment = new BasicNotificationFragment();
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_basic_notification;
    }

    @Override
    protected void initViewsAndEvents() {
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @OnClick({R.id.bt_normal, R.id.bt_expand_layout, R.id.bt_update_notifications, R.id.bt_remove_notifications,
            R.id.bt_fixed_duration_progress_notifications, R.id.bt_continuing_progress_notifications,
            R.id.bt_regular_activity, R.id.bt_special_activity})
    public void onClick(View view) {

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setSmallIcon(R.drawable.ic_stat_notification);
        PendingIntent pendingIntent;
        switch (view.getId()) {

            case R.id.bt_normal:
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                builder.setContentTitle("BasicNotifications Sample");
                builder.setContentText("Time to learn about notifications!");
                builder.setSubText("Tap to view documentation about notifications.");
//                builder.setOngoing(true);  // 是否常驻状态栏

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
                pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
                builder.setContentIntent(pendingIntent);

                mNotificationManager.notify(0, builder.build());
                break;

            // Applying an expanded layout to a notification
            case R.id.bt_expand_layout:
                builder.setContentTitle("BasicNotifications Sample");
                builder.setContentText("Time to learn about notifications!");
                final NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                String[] events = new String[]{"1", "2", "3", "4", "5", "7"};
                inboxStyle.setBigContentTitle("Event tracker details:");
                for (int i = 0; i < events.length; i++) {
                    inboxStyle.addLine(events[i]);
                }
                builder.setStyle(inboxStyle);


                mNotificationManager.notify(1, builder.build());
                break;
            // Updating notifications
            case R.id.bt_update_notifications:

                for (int i = 1; i < 5; i++) {
                    Message message = new Message();
                    message.what = i;
                    message.arg1 = i;
                    mHandler.sendMessageDelayed(message, 1000 * i);
                }
                break;

            // Removing notifications
            case R.id.bt_remove_notifications:

                mNotificationManager.cancel(2);
                break;

            // Displaying a fixed-duration progress indicator
            case R.id.bt_fixed_duration_progress_notifications:

                builder.setContentTitle("Picture Download");
                builder.setContentText("Download in progress");
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i <= 100; i = i + 5) {

                            builder.setProgress(100, i, false);
                            mNotificationManager.notify(3, builder.build());

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                        builder.setContentText("Download complete");
                        builder.setProgress(0, 0, false);
                        mNotificationManager.notify(3, builder.build());

                    }
                }).run();
                break;

            // Displaying a continuing activity indicator
            case R.id.bt_continuing_progress_notifications:
                builder.setContentTitle("Picture Download");
                builder.setContentText("Download in progress");
                builder.setProgress(0, 0, true);
                mNotificationManager.notify(4, builder.build());
                break;

            // Setting up a regular activity PendingIntent
            // 可以指定返回到哪个activity
            case R.id.bt_regular_activity:

                Intent regularIntent = new Intent(getActivity(), MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
                // Adds the back stack
                stackBuilder.addParentStack(MainActivity.class);
                // Adds the Intent to the top of the stack
                stackBuilder.addNextIntent(regularIntent);
                // Gets a PendingIntent containing the entire back stack
                // 当设置下面PendingIntent.FLAG_UPDATE_CURRENT这个参数的时候，常常使得点击通知栏没效果或者
                // 设置了返回堆栈不起作用，你需要给notification设置一个独一无二的requestCode，
                pendingIntent = stackBuilder.getPendingIntent(12345, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentTitle("Setting up a regular activity PendingIntent!");
                builder.setAutoCancel(true);
                builder.setContentIntent(pendingIntent);
                mNotificationManager.notify(5, builder.build());

                break;

            // Setting up a special activity PendingIntent, 返回之前操作的Activity
            case R.id.bt_special_activity:

                Intent specialIntent = new Intent(getActivity(), com.example.android.ui.actionbarcompat.listpopupmenu.MainActivity.class);
                pendingIntent = PendingIntent.getActivity(getActivity(), 0, specialIntent, 0);

                builder.setContentTitle("Setting up a special activity PendingIntent");
                builder.setContentIntent(pendingIntent);
                mNotificationManager.notify(8, builder.build());

                break;

        }
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
            builder.setSmallIcon(R.drawable.ic_stat_notification);
            builder.setContentTitle("New Message");
            builder.setContentText("You've received new messages! " + msg.arg1);
            builder.setNumber(msg.arg1);  // 设置重复数量
            mNotificationManager.notify(2, builder.build());
        }
    };

}
