package com.example.android.net;

import com.example.android.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 多线程断点续传
 * Created by 14110105 on 2016-05-16.
 */
public class MultiThreadDownload extends Activity {

    public static final int THREAD_COUNT = 3;
    public static int runningThread = 3;// 记录正在运行的下载文件的线程数
    public int currentProcess = 0;

    private EditText mUrlEt;
    private Button mDownloadBtn;
    private ProgressBar mProgressBar;
    private TextView mProgressTv;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getApplicationContext(), "下载失败！",
                            Toast.LENGTH_SHORT).show();
                    break;

                case 1:
                    Toast.makeText(getApplicationContext(), "下载完成！",
                            Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    mProgressTv.setText("下载进度:" + mProgressBar.getProgress() * 100 / mProgressBar.getMax() + "%");

                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.multi_thread_download);



        mUrlEt = (EditText) findViewById(R.id.et_url);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mDownloadBtn = (Button) findViewById(R.id.btn_download);
        mProgressTv = (TextView) findViewById(R.id.tv_process);
        mDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAsyncTask.execute(1);

            }
        });

    }

    private AsyncTask<Integer, Integer, Integer> mAsyncTask = new AsyncTask<Integer, Integer,
            Integer>() {
        @Override
        protected Integer doInBackground(Integer... params) {
            downloadFile();
            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    };

    private void downloadFile() {
        final String path = mUrlEt.getText().toString();

        try {
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept-Encoding", "identity");

            int responseCode = urlConnection.getResponseCode();

            // 获取成功
            if (responseCode == 200) {

                int length = urlConnection.getContentLength();
                mProgressBar.setMax(length);


                Log.d("MultiThreadDownload", "文件长度：" + length);

                RandomAccessFile raf = new RandomAccessFile(new File(getExternalFilesDir(null), "temp.apk"), "rwd");
                // 指定创建的文件的长度
                raf.setLength(length);
                raf.close();

                // 假设3个线程去下载资源
                int blockSize = length / THREAD_COUNT;
                for (int threadId = 1; threadId <= 3; threadId++) {

                    // 第一个线程开始下载的位置
                    int startIndex = (threadId - 1) * blockSize;
                    int endIndex = threadId * blockSize - 1;
                    if (threadId == THREAD_COUNT) {
                        endIndex = length;
                    }

                    Log.d("MultiThreadDownload", "----threadId---"
                            + "--startIndex--" + startIndex
                            + "--endIndex--" + endIndex);

                    new DownloadThread(path, threadId, startIndex, endIndex).start();

                }


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * 下载文件的子线程，每一个线程下载对应位置的文件
     *
     * @author loonggg
     */
    public class DownloadThread extends Thread {

        private String path;
        private int threadId;
        private int startIndex;
        private int endIndex;


        public DownloadThread(String path, int threadId, int startIndex, int endIndex) {
            this.path = path;
            this.threadId = threadId;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {

            // 检查是否存在记录下载长度的文件，如果存在读取这个文件的数据
            File tempFile = new File(getExternalFilesDir(null), threadId + ".txt");
            if (tempFile.exists() && tempFile.length() > 0) {

                try {
                    FileInputStream fis = new FileInputStream(tempFile);
                    byte[] buffer = new byte[1024 * 10];
                    int length = fis.read(buffer);

                    String downloadLen = new String(buffer, 0, length);
                    int downloadInt = Integer.valueOf(downloadLen);
                    // ------------------这两行代码是关于断点续传时，设置进度条的起点时的关键代码-------------------
                    int alreadyDownloadInt = downloadInt - startIndex;

                    // 计算每个线程上次断点已经下载的文件的长度
                    currentProcess += alreadyDownloadInt;

                    startIndex = downloadInt;
                    fis.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            try {
                URL url = new URL(path);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("Accept-Encoding", "identity");
                // 重要：请求服务器下载部分的文件 指定文件的位置
                conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);

                int fileLength = conn.getContentLength();
                Log.d("MultiThreadDownload", " " + fileLength);
                int code = conn.getResponseCode();

                Log.d(String.valueOf(threadId), "ResponseCode:" + code);

                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                RandomAccessFile raf = new RandomAccessFile(new File(getExternalFilesDir(null), "temp.apk"), "rwd");
                // 随机写文件的时候从哪个位置开始写
                raf.seek(startIndex);

                int len = 0;

                byte[] buffer = new byte[1024];
                int total = 0;

                while ((len = bis.read(buffer)) != -1) {

                    RandomAccessFile  recordFile = new RandomAccessFile(new File(getExternalFilesDir(null), threadId + ".txt"), "rwd") ;// 记录每个线程的下载进度，为断点续传做标记
                    raf.write(buffer, 0, len);
                    total += len;

                    recordFile.write(String.valueOf(startIndex + total).getBytes());
                    recordFile.close();


                    synchronized (MultiThreadDownload.this) {

                        currentProcess += len; // 获取当前的总进度
                        // TODO:添加进度条更新

                        mProgressBar.setProgress(currentProcess);

                        Message message = Message.obtain();
                        message.what = 2;
                        mHandler.sendMessage(message);

                    }

                }

                bis.close();
                raf.close();
                System.out.println("线程：" + threadId + "下载完毕了！");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                threadFinish();
            }


        }


        private void threadFinish() {

            runningThread--;

            if (runningThread == 0) {  // 所有的线程已经执行完毕

                for (int i = 1; i < THREAD_COUNT; i ++) {
                    File file = new File(getExternalFilesDir(null), i + ".txt");
                    file.delete();
                }

            }

        }

    }


}
