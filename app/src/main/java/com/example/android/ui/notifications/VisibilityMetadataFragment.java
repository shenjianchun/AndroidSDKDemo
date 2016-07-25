package com.example.android.ui.notifications;

import com.example.android.R;
import com.uilibrary.app.BaseFragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Fragment that demonstrates how notifications with different visibility metadata differ on
 * a lockscreen.
 * Created by 14110105 on 2016-03-13.
 */
public class VisibilityMetadataFragment extends BaseFragment {

    private NotificationManager mNotificationManager;

    private Integer mIncrementalNotificationId = 0;

    @Bind(R.id.visibility_radio_group)
    RadioGroup mRadioGroup;

    public static VisibilityMetadataFragment newInstance() {

        VisibilityMetadataFragment fragment = new VisibilityMetadataFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_visibility_metadata_notification;
    }

    @Override
    protected void initViewsAndEvents() {
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @OnClick(R.id.show_notification_button)
    public void onClick(View view) {
        mIncrementalNotificationId = mIncrementalNotificationId + 1;
        mNotificationManager.notify(mIncrementalNotificationId,
                createNotification(getVisibilityFromSelectedRadio(mRadioGroup)));
    }

    private Notification createNotification(NotificationVisibility visibility) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setContentTitle("Notification for Visibility metadata");
        builder.setVisibility(visibility.getVisibility());
        builder.setSmallIcon(visibility.getNotificationIconId());
        builder.setContentText(String.format("Visibility: %s", visibility.getDescription()));
        return builder.build();

    }

    private NotificationVisibility getVisibilityFromSelectedRadio(RadioGroup radioGroup) {

        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.visibility_public_radio_button:
                return NotificationVisibility.PUBLIC;
            case R.id.visibility_private_radio_button:
                return NotificationVisibility.PRIVATE;
            case R.id.visibility_secret_radio_button:
                return NotificationVisibility.SECRET;
            default:
                return NotificationVisibility.PUBLIC;
        }

    }


    enum NotificationVisibility {

        PUBLIC(Notification.VISIBILITY_PUBLIC, "Public", R.drawable.ic_public_notification),
        PRIVATE(Notification.VISIBILITY_PRIVATE, "Private", R.drawable.ic_private_notification),
        SECRET(Notification.VISIBILITY_SECRET, "Serect", R.drawable.ic_secret_notification);

        private final int mVisibility;

        private final String mDescription;

        private final int mNotificationIconId;

        NotificationVisibility(int visibility, String description, int notificationIconId) {
            mVisibility = visibility;
            mDescription = description;
            mNotificationIconId = notificationIconId;
        }


        public int getVisibility() {
            return mVisibility;
        }

        public String getDescription() {
            return mDescription;
        }

        public int getNotificationIconId() {
            return mNotificationIconId;
        }


    }

}
