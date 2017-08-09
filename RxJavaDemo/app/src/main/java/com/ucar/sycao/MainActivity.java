package com.ucar.sycao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ucar.sycao.eventbus.FirstActivity;
import com.ucar.sycao.rx.RxMainActivity;

/**
 * Created by sycao on 2017/8/3.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEventBus;
    Button btnRxJava;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEventBus = (Button)findViewById(R.id.btn_main_eventbus);
        btnRxJava = (Button)findViewById(R.id.btn_main_rx);
        btnEventBus.setOnClickListener(this);
        btnRxJava.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_main_eventbus:
                startActivity(new Intent(getApplicationContext(), FirstActivity.class));
                break;

            case R.id.btn_main_rx:
                startActivity((new Intent(getApplicationContext(), RxMainActivity.class)));
                break;

        }
    }
}
