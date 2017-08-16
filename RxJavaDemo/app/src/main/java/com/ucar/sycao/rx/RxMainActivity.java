package com.ucar.sycao.rx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ucar.sycao.R;
import com.ucar.sycao.retrofit.GankActivity;

/**
 * Created by sycao on 2017/8/4.
 * rx demo main list
 */

public class RxMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_main);

        findViewById(R.id.btn_rx_main_1).setOnClickListener(this);
        findViewById(R.id.btn_rx_main_2).setOnClickListener(this);
        findViewById(R.id.btn_rx_main_3).setOnClickListener(this);
        findViewById(R.id.btn_rx_main_4).setOnClickListener(this);
        findViewById(R.id.btn_rx_main_5).setOnClickListener(this);
        findViewById(R.id.btn_rx_main_6).setOnClickListener(this);
        findViewById(R.id.btn_rx_main_7).setOnClickListener(this);
        findViewById(R.id.btn_rx_main_8).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rx_main_1:
                startActivity(new Intent(getApplicationContext(), RxActicity1.class));
                break;
            case R.id.btn_rx_main_2:
                startActivity(new Intent(getApplicationContext(), RxActivity2.class));
                break;
            case R.id.btn_rx_main_3:
                startActivity(new Intent(getApplicationContext(), RxActivity3.class));
                break;
            case R.id.btn_rx_main_4:
                startActivity(new Intent(getApplicationContext(), RxActivity4.class));
                break;
            case R.id.btn_rx_main_5:
                startActivity(new Intent(getApplicationContext(), RxActivity5.class));
                break;
            case R.id.btn_rx_main_6:
                startActivity(new Intent(getApplicationContext(), RxActivity6.class));
                break;
            case R.id.btn_rx_main_7:
                startActivity(new Intent(getApplicationContext(), RxActivity7.class));
                break;
            case R.id.btn_rx_main_8:
                startActivity(new Intent(getApplicationContext(), GankActivity.class));
                break;
            default:
                Toast.makeText(getApplicationContext(), "todo...", Toast.LENGTH_SHORT).show();
        }
    }
}
