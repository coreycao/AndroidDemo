package com.ucar.sycao.rx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ucar.sycao.R;

/**
 * Created by sycao on 2017/8/4.
 * rx demo main list
 */

public class RxMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_main);
        btn1 = (Button) findViewById(R.id.btn_rx_main_1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn_rx_main_2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn_rx_main_3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn_rx_main_4);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rx_main_1:
                startActivity(new Intent(getApplicationContext(),RxActicity1.class));
                break;
            case R.id.btn_rx_main_2:
                startActivity(new Intent(getApplicationContext(),RxActivity2.class));
                break;
            case R.id.btn_rx_main_3:
                startActivity(new Intent(getApplicationContext(),RxActivity3.class));
                break;
            case R.id.btn_rx_main_4:
                startActivity(new Intent(getApplicationContext(),RxActivity4.class));
                break;
            default:
                Toast.makeText(getApplicationContext(), "todo...", Toast.LENGTH_SHORT).show();
        }
    }
}
