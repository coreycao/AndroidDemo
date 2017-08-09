package com.ucar.sycao.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ucar.sycao.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FirstActivity extends AppCompatActivity {

    Button btnFirst;
    TextView tvShowMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        // 注册 EventBus
        EventBus.getDefault().register(this);

        btnFirst=(Button) findViewById(R.id.btn_first);
        tvShowMsg=(TextView)findViewById(R.id.tv_show_msg);
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.btn_first){
                    Intent intent=new Intent(getApplicationContext(),SecondActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFirstEvent(FirstEvent event){
        tvShowMsg.setText(event.getMsg());
        Toast.makeText(getApplicationContext(),event.getMsg(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
