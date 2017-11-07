package com.corey.im.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.corey.im.R;

/**
 * Created by sycao on 2017/11/6.
 * 登录验证页
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    Button btnGoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btn_login_perform);
        btnGoto = (Button) findViewById(R.id.btn_login_goto);
        btnLogin.setOnClickListener(this);
        btnGoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login_goto) {
            startActivity(new Intent(this, ChatActivity.class));
        }
    }
}
