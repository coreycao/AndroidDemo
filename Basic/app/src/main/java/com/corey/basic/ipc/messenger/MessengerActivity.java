package com.corey.basic.ipc.messenger;

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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by sycao on 2018/5/17.
 */

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        Button btnBind = new Button(this);
        btnBind.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnBind.setText("Bind");
        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "bind");
                bindService(new Intent(MessengerActivity.this, MessengerService.class),
                        connection, Context.BIND_AUTO_CREATE);
            }
        });
        ll.addView(btnBind);

        Button btnUnBind = new Button(this);
        btnUnBind.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnUnBind.setText("UnBind");
        btnUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messenger != null) {
                    Log.d(TAG, "unbind");
                    messenger = null;
                    unbindService(connection);
                } else {
                    Log.d(TAG, "unbind error");
                }
            }
        });
        ll.addView(btnUnBind);

        Button btnSend = new Button(this);
        btnSend.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnSend.setText("Send Msg");
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "send message");
                sendMessage();
            }
        });
        ll.addView(btnSend);

        tvContent = new TextView(this);
        tvContent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.addView(tvContent);

        setContentView(ll);

        clientMessenger = new Messenger(new MessengerActivity.ClientHandler(this));

    }

    TextView tvContent;

    public void setText(String txt){
        if (tvContent!=null){
            tvContent.setText(txt);
        }
    }

    Messenger messenger;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
        }
    };

    private void sendMessage() {
        if (messenger != null) {
            Message msg = Message.obtain();
            msg.what = 0x01;
            msg.replyTo = clientMessenger;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    Messenger clientMessenger;

    static class ClientHandler extends Handler {

        WeakReference<Activity> activityWeakReference;

        ClientHandler(Activity activity){
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x02:
                    Log.d(TAG, "client receive message, and the content is:" +
                            msg.getData().getString("REPLY"));
                    MessengerActivity activity = (MessengerActivity)activityWeakReference.get();
                    activity.setText(msg.getData().getString("REPLY"));
                    break;
            }
        }
    }
}
