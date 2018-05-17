package com.corey.basic.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by sycao on 2018/5/14.
 * Service Demo
 */

public class ServiceActivity extends AppCompatActivity {

    NormalBindService normalBindService;

    private static final String TAG = "ServiceActivity";

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            normalBindService = ((NormalBindService.LocalBinder) service).getService();
        }

        /**
         * 当取消绑定的时候被回调。但正常情况下是不被调用的，它的调用时机是当Service服务被意外销毁时，
         * 例如内存的资源不足时这个方法才被自动调用。
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            normalBindService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Button btnStartService = new Button(this);
        btnStartService.setText("Start Service");
        btnStartService.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(ServiceActivity.this, NormalService.class));
            }
        });
        linearLayout.addView(btnStartService);

        Button btnStopService = new Button(this);
        btnStopService.setText("Stop Service");
        btnStopService.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(ServiceActivity.this, NormalService.class));
            }
        });
        linearLayout.addView(btnStopService);

        Button btnBindService = new Button(this);
        btnBindService.setText("Bind Service");
        btnBindService.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(ServiceActivity.this, NormalBindService.class), connection, Service.BIND_AUTO_CREATE);
            }
        });
        linearLayout.addView(btnBindService);

        Button btnOnBindService = new Button(this);
        btnOnBindService.setText("unbind");
        btnOnBindService.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnOnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (normalBindService != null) {
                    normalBindService = null;
                    unbindService(connection);
                }
            }
        });
        linearLayout.addView(btnOnBindService);

        Button btnGetDataByBinder = new Button(this);
        btnGetDataByBinder.setText("Get Data");
        btnGetDataByBinder.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnGetDataByBinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (normalBindService != null) {
                    Log.d(TAG, "getCount#" + normalBindService.getCount());
                } else {
                    Log.d(TAG, "normalBindService is null");
                }
            }
        });
        linearLayout.addView(btnGetDataByBinder);

        final Intent forgroundIntent = new Intent(this, ForgroundService.class);
        Button btnForground = new Button(this);
        btnForground.setText("startForground service");
        btnForground.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnForground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgroundIntent.putExtra("actionCode", 0);
                startService(forgroundIntent);
            }
        });
        linearLayout.addView(btnForground);

        Button stopForground = new Button(this);
        stopForground.setText("stop forground service");
        stopForground.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        stopForground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgroundIntent.putExtra("actionCode", 1);
                startService(forgroundIntent);
            }
        });
        linearLayout.addView(stopForground);

        setContentView(linearLayout);

    }
}
