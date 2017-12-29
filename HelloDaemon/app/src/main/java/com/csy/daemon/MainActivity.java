package com.csy.daemon;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.csy.daemon.service.DaemonService;
import com.csy.daemon.service.MyService;

import demo.csy.com.hellodaemon.R;

/**
 * Created by sycao on 2017/12/26.
 * MainActivity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_bind).setOnClickListener(this);
        findViewById(R.id.btn_unbind).setOnClickListener(this);
        findViewById(R.id.btn_start_daemon).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startService(new Intent(this, MyService.class));
                break;
            case R.id.btn_stop:
                stopService(new Intent(this, MyService.class));
                break;
            case R.id.btn_bind:
                bindService(new Intent(this, MyService.class),
                        connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(connection);
                break;
            case R.id.btn_start_daemon:
                startService(new Intent(this, DaemonService.class));
                break;
        }
    }

    private MyService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "ServiceConnection#onServiceConnected");
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.sayHi();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "ServiceConnection#onServiceDisconnected");
        }
    };
}
