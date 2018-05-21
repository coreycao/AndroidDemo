package com.corey.basic.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.corey.basic.ipc.aidl.BookManagerActivity;
import com.corey.basic.ipc.messenger.MessengerActivity;
import com.corey.basic.ipc.provider.ProviderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sycao on 2018/5/17.
 */

public class IPCActitivy extends AppCompatActivity {

    private static final String TAG = "IPCActivity";

    List<Class<? extends AppCompatActivity>> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list.add(MessengerActivity.class);
        list.add(ProviderActivity.class);
        list.add(BookManagerActivity.class);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (final Class clazz : list) {
            Button btn = new Button(this);
            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            btn.setText(clazz.getSimpleName());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(IPCActitivy.this, clazz));
                }
            });
            linearLayout.addView(btn);
        }
        setContentView(linearLayout);
    }
}
