package my.nouilibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Android Package Utils
 * Created by 14110105 on 2016-06-06.
 */
public class PackageUtils {

    /**
     * 获取可以接收到intent的Activities
     * (可以通过判断 activities != null 来判断是否有可匹配的Activity)
     * @param context Context
     * @param intent Intent
     * @return 可以接收到intent的Activities
     */
    public static List queryIntentActivities(Context context, Intent intent) {

        PackageManager packageManager = context.getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        return activities;
    }


    /**
     * 根据intent是否匹配到Activity
     * @param context Content
     * @param intent Intent
     * @return 是否匹配到Activity
     */
    public static boolean resolveActivity(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        ResolveInfo resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        return resolveInfo != null;

    }

}
