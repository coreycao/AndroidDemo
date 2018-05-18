package com.corey.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.corey.basic.ipc.IPCActitivy;
import com.corey.basic.launchmode.Launch1Activity;
import com.corey.basic.lifecycle.FirstActivity;
import com.corey.basic.service.ServiceActivity;
import com.corey.basic.ui.StatusBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sycao on 2017/8/14.
 */

public class MainActivity extends AppCompatActivity {

    List<Class<? extends AppCompatActivity>> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list.add(ServiceActivity.class);

        list.add(StatusBarActivity.class);

        list.add(IPCActitivy.class);

        list.add(FirstActivity.class);

        list.add(Launch1Activity.class);

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
                    startActivity(new Intent(MainActivity.this, clazz));
                }
            });
            linearLayout.addView(btn);
        }
        setContentView(linearLayout);
    }
}
