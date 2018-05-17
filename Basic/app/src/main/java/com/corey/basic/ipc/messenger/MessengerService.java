package com.corey.basic.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sycao on 2018/5/17.
 */

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    static class MyServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    Log.d(TAG, "new message");
                    Message msgR = Message.obtain();
                    msgR.what = 0x02;
                    Bundle bundle = new Bundle();
                    bundle.putString("REPLY", "this is a message from " + TAG);
                    msgR.setData(bundle);
                    Messenger clientMessenger = msg.replyTo;
                    try {
                        clientMessenger.send(msgR);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    Messenger messenger;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        messenger = new Messenger(new MyServiceHandler());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "destroy");
        super.onDestroy();
    }
}
