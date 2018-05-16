package com.corey.customview.layout_tips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.corey.customview.R;

/**
 * Created by sycao on 2018/5/16.
 */

public class LayoutActivity extends AppCompatActivity {

    private static final String TAG = LayoutActivity.class.getSimpleName();

    LinearLayout layoutver;

    LinearLayout layouthor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        layouthor = (LinearLayout) findViewById(R.id.ly_hor);

        layoutver = (LinearLayout) findViewById(R.id.ly_ver);


        LayoutInflater inflater = LayoutInflater.from(this);

        inflater.inflate(R.layout.layout_buttons, layouthor);

        inflater.inflate(R.layout.layout_buttons, layoutver);


        ViewStub stubHello = (ViewStub) findViewById(R.id.stub_hello);
        View layoutSub = stubHello.inflate();
        Log.d(TAG, String.valueOf(layoutSub.getId()));
    }
}
