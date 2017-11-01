package com.corey.basic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by sycao on 2017/10/24.
 * Notification 的基本使用
 */

public class NotificationActivity extends AppCompatActivity {

    NotificationManager notificationManager;
    NotificationCompat.Builder builder;
    final int notificationId = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);

        findViewById(R.id.btn_notification_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }

    private void sendNotification() {
        notificationManager.cancel(notificationId);
        PendingIntent pi = PendingIntent.getActivity(this, 1, new Intent(this, StatusBarActivity.class), PendingIntent.FLAG_ONE_SHOT);
        builder.setContentTitle("Hello:")
                .setContentText("This is a secret message.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pi)
//                .setFullScreenIntent(pi,true)
                .setTicker("xxx")
                .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher_round, "确定",
                        PendingIntent.getActivity(this, 1, new Intent(this, StatusBarActivity.class), PendingIntent.FLAG_ONE_SHOT)))
                .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher_round, "取消",
                        PendingIntent.getActivity(this, 1, new Intent(this, ConstraintActivity.class), PendingIntent.FLAG_ONE_SHOT)));
        notificationManager.notify(notificationId, builder.build());
    }
}
