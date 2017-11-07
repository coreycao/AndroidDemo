package com.corey.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.corey.im.db.entity.User;
import com.corey.im.ui.activity.LoginActivity;

import java.util.List;

/**
 * Created by sycao on 2017/11/6.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUser;
    TextView tvUser;
    Button btnGoto;
    Button btnSave;
    Button btnGet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.et_main_save);
        tvUser = findViewById(R.id.tv_main_get);
        btnGoto = findViewById(R.id.btn_main_goto);
        btnSave = findViewById(R.id.btn_main_save);
        btnGet = findViewById(R.id.btn_main_get);

        btnGoto.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnGet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_main_goto) {
            startActivity(new Intent(this, LoginActivity.class));

        } else if (view.getId() == R.id.btn_main_save) {
            if (null != etUser && !TextUtils.isEmpty(etUser.getText().toString())) {
                User user = new User();
                user.setName(etUser.getText().toString());
                App.getDaoSession().getUserDao().insert(user);
            }
        } else if (view.getId() == R.id.btn_main_get) {
            List<User> users = App.getDaoSession().getUserDao().loadAll();
            StringBuilder names = new StringBuilder();
            for (User user : users) {
                names.append(user.getName());
            }
            tvUser.setText(names.toString());
        }
    }
}
