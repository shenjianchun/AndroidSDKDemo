package com.example.android.ui.notifications;

import com.example.android.R;
import com.uilibrary.app.BaseFragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.CheckBox;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Fragment that demonstrates options for displaying Heads-Up Notifications.
 * Created by 14110105 on 2016-03-13.
 */
public class HeadsUpNotificationFragment extends BaseFragment {


    /**
     * NotificationId used for the notifications from this Fragment.
     */
    private static final int NOTIFICATION_ID = 1;

    private NotificationManager mNotificationManager;

    @Bind(R.id.use_heads_up_checkbox)
    CheckBox mUseHeadsUpCheckbox;


    public static HeadsUpNotificationFragment newInstance() {
        HeadsUpNotificationFragment fragment = new HeadsUpNotificationFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_heads_up_notification;
    }

    @Override
    protected void initViewsAndEvents() {
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @OnClick(R.id.show_notification_button)
    public void onClick(View view) {


        mNotificationManager.notify(NOTIFICATION_ID, createNotification(mUseHeadsUpCheckbox.isChecked()));

    }

    private Notification createNotification(boolean makeHeadsUpNotification) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setSmallIcon(R.drawable.ic_stat_notification)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("Sample Notification")
                .setContentText("This is a normal notification.");

        if (makeHeadsUpNotification) {

            Intent push = new Intent(getActivity(), NotificationsActivity.class);
            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(getActivity(), 0, push, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentText("Heads-Up Notification on Android L or above.");
            builder.setFullScreenIntent(fullScreenPendingIntent, true);
        }
        return builder.build();

    }
}
