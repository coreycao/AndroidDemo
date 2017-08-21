package com.ucar.sycao.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ucar.sycao.R;

/**
 * Created by sycao on 2017/8/21.
 * Retrofit demo main activity
 */

public class GankMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_main);

        findViewById(R.id.btn_gank_main_1).setOnClickListener(this);
        findViewById(R.id.btn_gank_main_2).setOnClickListener(this);
        findViewById(R.id.btn_gank_main_3).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gank_main_1:
                startActivity(new Intent(this, GankActivity.class));
                break;

            case R.id.btn_gank_main_2:
                startActivity(new Intent(this, GankActivity2.class));
                break;

            case R.id.btn_gank_main_3:
                startActivity(new Intent(this, GankActivity3.class));
                break;

            default:
                Toast.makeText(this, "todo...", Toast.LENGTH_SHORT).show();
        }

    }
}
