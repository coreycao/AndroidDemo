package com.ucar.sycao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ucar.sycao.eventbus.FirstActivity;
import com.ucar.sycao.retrofit.GankMainActivity;
import com.ucar.sycao.rx.RxMainActivity;

/**
 * Created by sycao on 2017/8/3.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_main_eventbus).setOnClickListener(this);
        findViewById(R.id.btn_main_rx).setOnClickListener(this);
        findViewById(R.id.btn_main_retrofit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_eventbus:
                startActivity(new Intent(this, FirstActivity.class));
                break;
            case R.id.btn_main_rx:
                startActivity((new Intent(this, RxMainActivity.class)));
                break;
            case R.id.btn_main_retrofit:
                startActivity(new Intent(this, GankMainActivity.class));
                break;
        }
    }
}
