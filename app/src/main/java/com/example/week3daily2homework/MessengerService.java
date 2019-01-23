package com.example.week3daily2homework;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

public class MessengerService extends Service {
    private String TAG = "MessengerService";
    static final int MSG_SAY_HELLO = 1;

    Messenger mMessenger = new Messenger(new IncomingHandler());

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Bundle bundle = msg.getData();
                    String hello = (String) bundle.get("hello");

                    Toast.makeText(getApplicationContext(), hello, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    public MessengerService(){
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate called");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind done");
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }
}
