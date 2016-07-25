package com.example.android.content;

import com.example.android.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import my.nouilibrary.utils.T;

/**
 * Created by 14110105 on 2016-04-14.
 */
public class StorageActivity extends Activity {

    String FILENAME = "hello_file";
    String string = "hello world!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_storage);
    }


    public void onClick(View view) {

        switch (view.getId()) {
            // 文件目录保存在：/data/data/package-name/files/file_name
            case R.id.btn_write_internal_file:
                try {
                    FileOutputStream fileOutputStream = openFileOutput(FILENAME, Context
                            .MODE_PRIVATE);
                    fileOutputStream.write(string.getBytes());
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_read_internal_file:
                try {
                    FileInputStream fileInputStream = openFileInput(FILENAME);

                    byte[] buffer = new byte[1024];
                    int length = fileInputStream.read(buffer);
                    fileInputStream.close();

                    T.showShort(this, new String(buffer));

                    File fileDir = getFilesDir();


                    // getFilesDir() = /data/data/my.android.demo/files , getDir() =
                    // /data/data/my.android.demo/app_null , fileList() = [share_history.xml,
                    // instant-run, hello_file]
                    Log.d(this.getClass().getName(), "getFilesDir() = " + getFilesDir()
                            .getAbsolutePath() + "：" + Arrays.toString(getFilesDir().list()) + " , "
                            + "getDir() = " + getDir(null, MODE_PRIVATE) + " , "
                            + "fileList() = " + Arrays.toString(fileList()));
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;

            case R.id.btn_write_internal_cache_file:
                // 文件目录保存在：/data/data/package-name/cache/file_name
                File cacheDir = getCacheDir();
                File cache = new File(cacheDir, FILENAME);
                try {
                    FileWriter fileWriter = new FileWriter(cache);
                    fileWriter.write(cache.getAbsolutePath() + ", Hello Cache!");
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_read_internal_cache_file:
                File readCacheDir = getCacheDir();
                File readCache = new File(readCacheDir, FILENAME);
                try {
                    FileReader fileReader = new FileReader(readCache);
                    char[] buffer = new char[1024];
                    fileReader.read(buffer);
                    T.showLong(this, String.copyValueOf(buffer));
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_external_app_private:

                File fileDir = getExternalFilesDir(null);
                File[] fileDirs = getExternalFilesDirs(null);

                Log.d(getClass().getName(), fileDir.getAbsolutePath());
                for (File dir : fileDirs) {
                    Log.d(getClass().getName(), dir.getAbsolutePath());
                }

                Log.d(getClass().getName(), getExternalCacheDir().getAbsolutePath());

                break;

            default:
                break;
        }

    }
}
