package com.example.android.app;

import com.example.android.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 14110105 on 2016-05-05.
 */
public class MessengerServiceActivities {

    public static class Binding extends Activity {

        /** Messenger for communicating with service. */
        Messenger mService = null;
        /** Flag indicating whether we have called bind on the service. */
        boolean mIsBound;
        /** Some text view we are using to show state information. */
        TextView mCallbackText;

        private ServiceConnection mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = new Messenger(service);

                mCallbackText.setText("Attached.");

                Message message = Message.obtain(null, MessengerService.MSG_REGISTER_CLIENT);
                message.replyTo = mMessenger;
                Message setValue = Message.obtain(null, MessengerService.MSG_SET_VALUE, this.hashCode(), 0);
                try {
                    mService.send(message);
                    mService.send(setValue);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                mService = null;

                mCallbackText.setText("Disconnected.");

                // As part of the sample, tell the user what happened.
                Toast.makeText(Binding.this, R.string.remote_service_disconnected,
                        Toast.LENGTH_SHORT).show();
            }
        };

        private final Messenger mMessenger = new Messenger(new IncomingHandler());

        private class IncomingHandler extends Handler {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case MessengerService.MSG_SET_VALUE:
                        mCallbackText.setText("Received from service: " + msg.arg1);
                        break;
                    default:
                        super.handleMessage(msg);

                }

            }
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.messenger_service_binding);

            // Watch for button clicks.
            Button button = (Button)findViewById(R.id.bind);
            button.setOnClickListener(mBindListener);
            button = (Button)findViewById(R.id.unbind);
            button.setOnClickListener(mUnbindListener);

            mCallbackText = (TextView)findViewById(R.id.callback);
            mCallbackText.setText("Not attached.");
        }


        private View.OnClickListener mBindListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Binding.this, MessengerService.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

                mIsBound = true;

            }
        };

        private View.OnClickListener mUnbindListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsBound) {

                    try {
                        Message msg = Message.obtain(null, MessengerService.MSG_UNREGISTER_CLIENT);
                        msg.replyTo = mMessenger;
                        mService.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    unbindService(mServiceConnection);
                    mIsBound = false;

                    mCallbackText.setText("Unbinding.");
                }

            }
        };


    }
}
