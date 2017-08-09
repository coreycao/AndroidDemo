package com.ucar.sycao.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ucar.sycao.R;

import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends AppCompatActivity {

    Button btnSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnSecond=(Button)findViewById(R.id.btn_second);
        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new FirstEvent("这是从第二个Activity发送来的消息"));
                Toast.makeText(getApplicationContext(),"send msg",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
