package com.corey.basic.ipc.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.corey.basic.base.BaseActivity;

/**
 * Created by sycao on 2018/5/21.
 */

public class BookManagerActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LinearLayout ll = new LinearLayout(this);
    ll.setOrientation(LinearLayout.VERTICAL);

    Button btnBind = new Button(this);
    btnBind.setText("bind");
    btnBind.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    btnBind.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        bindService(new Intent(BookManagerActivity.this, BookManagerService.class),
            connection, BIND_AUTO_CREATE);
      }
    });

    Button btnGet = new Button(this);
    btnGet.setText("get");
    btnGet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    btnGet.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Log.d("get", bookManager.getBookList().toString());
        } catch (RemoteException e) {
          e.printStackTrace();
        }
      }
    });

    Button btnAdd = new Button(this);
    btnAdd.setText("add");
    btnAdd.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          bookManager.addBook(new Book(3, "hello world"));
        } catch (RemoteException e) {
          e.printStackTrace();
        }
      }
    });

    ll.addView(btnBind);
    ll.addView(btnAdd);
    ll.addView(btnGet);
    setContentView(ll);
  }

  IBookManager bookManager;

  ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      bookManager = IBookManager.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
  };

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbindService(connection);
  }
}
