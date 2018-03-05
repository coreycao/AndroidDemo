package com.corey.customview;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;

import com.corey.customview.devart.CircleView;
import com.corey.customview.edittext.EditTextActivity;

/**
 * Created by sycao on 2017/8/11.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    Button btnCustomEt;

    CircleView circleView;

    LinearLayout layoutver;

    LinearLayout layouthor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCustomEt = (Button) findViewById(R.id.btn_main_custom_et);
        btnCustomEt.setOnClickListener(this);

        findViewById(R.id.btn_move).setOnClickListener(this);

        circleView = (CircleView) findViewById(R.id.circle_view);

        layouthor = (LinearLayout) findViewById(R.id.ly_hor);

        layoutver = (LinearLayout) findViewById(R.id.ly_ver);

        LayoutInflater inflater = LayoutInflater.from(this);

        inflater.inflate(R.layout.layout_buttons, layouthor);

        inflater.inflate(R.layout.layout_buttons, layoutver);


        ViewStub stubHello = (ViewStub) findViewById(R.id.stub_hello);
        View layoutSub = stubHello.inflate();
        Log.d(TAG, String.valueOf(layoutSub.getId()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_custom_et:
                startActivity(new Intent(getApplicationContext(), EditTextActivity.class));
                break;
            case R.id.btn_move:
                ObjectAnimator.ofFloat(circleView, "translationX", 0, 100)
                        .setDuration(100).start();
                break;
        }
    }
}
