package com.corey.basic.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sycao on 2018/5/14.
 * BindService
 */

public class NormalBindService extends Service {

  private final static String TAG = "NormalBindService";

  private int count;

  private boolean quit = false;

  private Binder binder;

  private Thread thread;

  @Override
  public void onCreate() {
    super.onCreate();
    Log.d(TAG, "onCreate");
    binder = new LocalBinder();
    thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (!quit) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          count++;
        }
      }
    });
    thread.start();
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    Log.d(TAG, "onBind");
    return binder;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    Log.d(TAG, "onUnbind");
    return super.onUnbind(intent);
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy");
    super.onDestroy();
    this.quit = true;
  }

  public class LocalBinder extends Binder {
    NormalBindService getService() {
      return NormalBindService.this;
    }
  }

  public int getCount() {
    return this.count;
  }
}
