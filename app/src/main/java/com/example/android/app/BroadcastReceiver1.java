package com.example.android.app;

import com.example.android.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import my.nouilibrary.utils.T;

/**
 * BroadcastReceiver 的使用demo
 * Created by 14110105 on 2016-05-10.
 */
public class BroadcastReceiver1 extends Activity {

    private static final String TAG = BroadcastReceiver1.class.getName();

    public static final String ACTION_RECEIVER_STATIC = "com.example.android.action.ACTION_RECEIVER_STATIC";
    public static final String ACTION_RECEIVER_DYNAMIC  = "com.example.android.action.ACTION_RECEIVER_DYNAMIC";

    public static final String PERMISSION_CUSTOMER_RECEIVER = "android.permission.CUSTOMER_RECEIVER";


    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.broadcast_receiver_1);

        mReceiver = new CustomerReceiver2();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // onResume 中注册Receiver
        IntentFilter intentFilter = new IntentFilter(ACTION_RECEIVER_DYNAMIC);
        intentFilter.setPriority(10);
        registerReceiver(mReceiver, intentFilter);

    }


    @Override
    protected void onPause() {
        super.onPause();
        // onPause中取消Receiver
        unregisterReceiver(mReceiver);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_send_broadcast_1:

                intent.setAction(ACTION_RECEIVER_STATIC);
                intent.putExtra("msg", "静态注册接收无序广播成功！");
                sendBroadcast(intent);
                break;

            case R.id.btn_send_broadcast_2:

                intent.setAction(ACTION_RECEIVER_DYNAMIC);
                intent.putExtra("msg", "动态注册接收无序广播成功！");
                sendBroadcast(intent);
                break;


            case R.id.btn_send_broadcast_3:
                intent.setAction(ACTION_RECEIVER_STATIC);
                intent.putExtra("msg", "静态注册接收有序广播成功！");
                sendOrderedBroadcast(intent, PERMISSION_CUSTOMER_RECEIVER);

                break;

            default:
                break;
        }
    }


    /**
     * 用于在AndroidManifest.xml中静态注册
     */
    public static class CustomerReceiver1 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String msg = intent.getStringExtra("msg");

            String className = Thread.currentThread().getName() + "  ,  " +  this.getClass().getName();

            T.showShort(context, className + "  " + msg);
            Log.d(TAG, className);

            if (getResultCode() != 0) {
                T.showShort(context, "上一个Receiver传递来的数据：" + getResultCode() + " , " + getResultData());
            }

        }
    }


    public static class CustomerReceiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String msg = intent.getStringExtra("msg");

            String className = Thread.currentThread().getName() + " ,  " +  this.getClass().getName();

            T.showShort(context, className + "  " + msg);
            Log.d(TAG, className);
        }
    }

    public static class CustomerReceiver3 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");

            String className = Thread.currentThread().getName() + " ,  " +  this.getClass().getName();

            T.showShort(context, className + "  " + msg);
            Log.d(TAG, className);

            // 传递结果给下一个Receiver
            setResult(RESULT_OK, "接收到CustomerReceiver3传递来的数据", null);

        }
    }


    public static class SystemReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.toString();

            String className = Thread.currentThread().getName() + " ,  " +  this.getClass().getName();

            T.showShort(context, className + "  " + msg);
            Log.d(TAG, className);
        }
    }
}
