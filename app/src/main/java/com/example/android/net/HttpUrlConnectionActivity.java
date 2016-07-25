package com.example.android.net;

import com.example.android.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import my.nouilibrary.utils.T;

/**
 * HttpUrlConnection的使用
 * Created by 14110105 on 2016-04-25.
 */
public class HttpUrlConnectionActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_http_url_conncection);

    }

    public void onClick(View view) {

        if (view.getId() == R.id.btn_http_download) {
            new DownloadTask().execute(0, 0, 0);
        } else {
            new MyAsyncTask().execute(view.getId());
        }


    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {

            if (params[0] == R.id.btn_http_get) {
                return httpGetNormal();
            } else if (params[0] == R.id.btn_http_post) {
                return httpPostNormal();
            } else if (params[0] == R.id.btn_http_sign_on) {
                return handleSignIn();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            T.showShort(HttpUrlConnectionActivity.this, s);
        }
    }

    private String httpGetNormal() {

        try {
            URL url = new URL("http://cn.bing.com/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            String result = readInStream(urlConnection.getInputStream());

            urlConnection.disconnect();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String handleSignIn() {

        URL url = null;
        try {
            url = new URL("http://cn.bing.com/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            if (!url.getHost().equals(urlConnection.getURL().getHost())){
                urlConnection.disconnect();
                return "Need to handle Sign in!";
            } else {
                urlConnection.disconnect();
                return "Connected!";
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String httpPostNormal() {

        try {
            URL url = new URL("http://asssit.cnsuning.com/asas/asloginAction.do");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
            
            OutputStream outputStream = urlConnection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("employeeId=" + "12134&");
            stringBuilder.append("password=" + "12134&");
            stringBuilder.append("imei=" + "12134");
            dataOutputStream.writeBytes(stringBuilder.toString());

            dataOutputStream.flush();
            dataOutputStream.close();

            int responseCode = urlConnection.getResponseCode();

            String result = readInStream(urlConnection.getInputStream());

            urlConnection.disconnect();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String readInStream(InputStream inputStream) {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                (bufferedInputStream));
        String buffer;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            while ((buffer = bufferedReader.readLine()) != null) {
                stringBuilder.append(buffer);
            }

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }

    private class DownloadTask extends AsyncTask<Integer, Integer, Integer> {

        ProgressDialog mProgressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(HttpUrlConnectionActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(100);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setTitle("Downing File");
            mProgressDialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            try {
                URL url = new URL("http://tumspre.cnsuning.com/ASS/2.0.1.0/SuningCusService_pre_2.0.1.0_20151116_N.apk");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept-Encoding", "identity");

                int fileLength = urlConnection.getContentLength();
                int downloadLength = 0;

                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                File file = new File(Environment.getExternalStorageDirectory(), "download_test");
                FileOutputStream fileOutputStream = new FileOutputStream(file);


                byte[] buffer = new byte[1024 * 4];
                while ( inputStream.read(buffer) != -1) {

                    fileOutputStream.write(buffer);
                    downloadLength += buffer.length;
                    int progress = downloadLength * 100 / fileLength ;

                    publishProgress(progress);
                }

                inputStream.close();
                fileOutputStream.close();

                urlConnection.disconnect();

                return fileLength;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mProgressDialog.dismiss();
        }
    }

}
