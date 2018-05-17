package com.corey.basic.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.corey.basic.R;

/**
 * Created by sycao on 2018/5/17.
 */

public class ForgroundService extends Service {

    private static final String TAG = ForgroundService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int code = intent.getIntExtra("actionCode", 0);
        if (code == 1) {
            stopForeground(true);
        } else if (code == 0) {
            createNotification();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setShowWhen(true)
                .setContentTitle("this is a title");
        startForeground(0x01, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
