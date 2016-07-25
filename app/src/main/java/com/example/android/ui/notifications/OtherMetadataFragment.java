package com.example.android.ui.notifications;

import com.example.android.R;
import com.uilibrary.app.BaseFragment;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Fragment that demonstrates how to attach metadata introduced in Android L, such as
 * priority data, notification category and person data.
 * Created by 14110105 on 2016-03-13.
 */

public class OtherMetadataFragment extends BaseFragment {

    /**
     * Request code used for picking a contact.
     */
    public static final int REQUEST_CODE_PICK_CONTACT = 1;

    /**
     * Incremental Integer used for ID for notifications so that each notification will be
     * treated differently.
     */
    private Integer mIncrementalNotificationId = Integer.valueOf(0);

    @Bind(R.id.category_spinner)
    Spinner mCategorySpinner;
    @Bind(R.id.priority_spinner)
    Spinner mPrioritySpinner;

    private NotificationManager mNotificationManager;

    public static OtherMetadataFragment newInstance() {
        OtherMetadataFragment fragment = new OtherMetadataFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_other_metadata;
    }

    @Override
    protected void initViewsAndEvents() {
        mNotificationManager = (NotificationManager) getActivity().getSystemService(Context
                .NOTIFICATION_SERVICE);

        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(getActivity(),
                android.R.layout.simple_spinner_item, Category.values());
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(categoryArrayAdapter);

        ArrayAdapter<Priority> priorityArrayAdapter = new ArrayAdapter<Priority>(getActivity(),
                android.R.layout.simple_spinner_item, Priority.values());
        priorityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(priorityArrayAdapter);

    }

    @OnClick(R.id.show_notification_button)
    public void onClick(View view) {
        mIncrementalNotificationId = mIncrementalNotificationId + 1;
        Category category = (Category) mCategorySpinner.getSelectedItem();
        Priority priority = (Priority) mPrioritySpinner.getSelectedItem();
        Notification notification = createNotification(priority, category, null);
        mNotificationManager.notify(mIncrementalNotificationId, notification);
    }


    /**
     * Creates a new notification and sets metadata passed as arguments.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Notification createNotification(Priority priority, Category category, Uri contactUri) {
        Notification.Builder notificationBuilder = new Notification.Builder(getActivity())
                .setContentTitle("Notification with other metadata")
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setPriority(priority.value)
                .setCategory(category.value)
                .setContentText(String.format("Category %s, Priority %s", category.value,
                        priority.name()));
//        if (contactUri != null) {
//            notificationBuilder.addPerson(contactUri.toString());
//            Bitmap photoBitmap = loadBitmapFromContactUri(contactUri);
//            if (photoBitmap != null) {
//                notificationBuilder.setLargeIcon(photoBitmap);
//            }
//        }
        return notificationBuilder.build();
    }

    /**
     * Enum indicating possible categories in {@link Notification} used from
     * {@link #mCategorySpinner}.
     */
    //@VisibleForTesting
    static enum Category {
        ALARM("alarm"),
        CALL("call"),
        EMAIL("email"),
        ERROR("err"),
        EVENT("event"),
        MESSAGE("msg"),
        PROGRESS("progress"),
        PROMO("promo"),
        RECOMMENDATION("recommendation"),
        SERVICE("service"),
        SOCIAL("social"),
        STATUS("status"),
        SYSTEM("sys"),
        TRANSPORT("transport");

        private final String value;

        Category(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Enum indicating possible priorities in {@link Notification} used from
     * {@link #mPrioritySpinner}.
     */
    //@VisibleForTesting
    static enum Priority {
        DEFAULT(Notification.PRIORITY_DEFAULT),
        MAX(Notification.PRIORITY_MAX),
        HIGH(Notification.PRIORITY_HIGH),
        LOW(Notification.PRIORITY_LOW),
        MIN(Notification.PRIORITY_MIN);

        private final int value;

        Priority(int value) {
            this.value = value;
        }
    }


}
