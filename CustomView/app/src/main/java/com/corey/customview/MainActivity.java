package com.corey.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.corey.customview.chart.ChartActivity;
import com.corey.customview.chart.DataProcessor;
import com.corey.customview.edittext.EditTextActivity;
import com.corey.customview.gcssloop.CanvasActivity;
import com.corey.customview.layout_tips.LayoutActivity;

/**
 * Created by sycao on 2017/8/11.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_main_custom_et).setOnClickListener(this);
        findViewById(R.id.btn_layout).setOnClickListener(this);
        findViewById(R.id.btn_other).setOnClickListener(this);
        findViewById(R.id.btn_zoom).setOnClickListener(this);
        findViewById(R.id.btn_chart).setOnClickListener(this);
        findViewById(R.id.btn_detail).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_custom_et:
                startActivity(new Intent(this, EditTextActivity.class));
                break;
            case R.id.btn_other:
                startActivity(new Intent(this, CanvasActivity.class));
                break;
            case R.id.btn_layout:
                startActivity(new Intent(this, LayoutActivity.class));
                break;
            case R.id.btn_zoom:
                startActivity(new Intent(this, ZoomActivity.class));
                break;
            case R.id.btn_chart:
                startActivity(new Intent(this, ChartActivity.class));
                break;
            case R.id.btn_detail:
                startActivity(new Intent(this, DetailActivity.class));
                break;
        }
    }
}
