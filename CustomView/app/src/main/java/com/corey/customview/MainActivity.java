package com.corey.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.corey.customview.edittext.EditTextActivity;

/**
 * Created by sycao on 2017/8/11.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCustomEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCustomEt = (Button) findViewById(R.id.btn_main_custom_et);
        btnCustomEt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_main_custom_et:
                startActivity(new Intent(getApplicationContext(),EditTextActivity.class));
                break;
        }
    }
}
