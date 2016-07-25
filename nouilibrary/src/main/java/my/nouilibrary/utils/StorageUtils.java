package my.nouilibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;

/**
 * Android Storage Utils
 * Created by 14110105 on 2016-04-13.
 */
public class StorageUtils {

    /** Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /** Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }


    /**
     * Create a File Uri for saving an image or video.
     *
     * @param albumName album directory name
     * @param fileName  file name
     */
    public static Uri getOutputMediaFileUri(String albumName, String fileName) {
        return Uri.fromFile(getOutputMediaFile(albumName, fileName));
    }

    /**
     * Create a File for saving an image or video.
     * (saved to：/sdcard/Pictures/albumName/fileName)
     *
     * @param albumName album directory name
     * @param fileName  file name
     */
    public static File getOutputMediaFile(String albumName, String fileName) {

        File mediaStorageDir = getStoragePublicDirectory(Environment.DIRECTORY_PICTURES, albumName);

        if (mediaStorageDir == null) {
            return  null;
        }

        return new File(mediaStorageDir, fileName);

    }

    /**
     * Create or get public Album external storage directory
     * @param type a public directory in external Storage pass to getExternalStoragePublicDirectory
     *             {@link android.os.Environment#getExternalStoragePublicDirectory(String)}
     *
     * @param albumName album directory name
     * @return new directory under /sdcard/@type/albumName/
     */
    public static File getStoragePublicDirectory(@NonNull String type, String albumName) {

        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        if (!isExternalStorageWritable()) {
            Log.e("StorageUtils", "ExternalStorage is not writable!");
            return null;
        }

        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                type), albumName);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("StorageUtils", "Directory not created");
                return null;
            }
        }

        return file;
    }

    /**
     * 在外部存储器的应用cache目录中创建文件，如果外部存储器不存在，就在内部存储器的应用对应cache目录中创建文件。
     * @param context Context上下文
     * @param uniqueName cache文件名
     * @return File
     */
    public static File getDiskCache(Context context, String uniqueName) {
        
        String cachePath;
        if (isExternalStorageWritable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }


    /**
     * 发送媒体扫描广播
     *
     * @param uri 照片uri
     */
    private static void sendMediaScanBroadcast(Context context, Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        context.sendBroadcast(mediaScanIntent);

    }

}
