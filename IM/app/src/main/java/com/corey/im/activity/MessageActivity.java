package com.corey.im.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.corey.im.R;
import com.corey.im.adapter.MessageAdapter;
import com.corey.im.entity.MessageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sycao on 2017/11/3.
 * 聊天页activity
 */

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lvChatList;
    MessageAdapter messageAdapter;
    List<MessageEntity> data;

    Button btnSend;
    EditText etMsgInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        this.data = getMockData();

        findViewById(R.id.iv_message_back).setOnClickListener(this);
        btnSend = (Button) findViewById(R.id.btn_chat_send);
        btnSend.setOnClickListener(this);
        etMsgInput = (EditText) findViewById(R.id.et_chat_input);

        lvChatList = (ListView) findViewById(R.id.lv_message);
        messageAdapter = new MessageAdapter(this, data);
        lvChatList.setAdapter(messageAdapter);
        lvChatList.setSelection(data.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private List<MessageEntity> getMockData() {
        List<MessageEntity> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add(new MessageEntity("message hello world:" + i, i % 2));
        }
        return data;
    }

    boolean flag = true;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_chat_send) {
            if (!TextUtils.isEmpty(etMsgInput.getText())) {
                data.add(new MessageEntity(etMsgInput.getText().toString(), flag ? MessageEntity.MSG_TYPE_SEND : MessageEntity.MSG_TYPE_REC));
                messageAdapter.notifyDataSetChanged();
                lvChatList.setSelection(data.size());
                etMsgInput.setText("");
                flag = !flag;
            }
        } else if (v.getId() == R.id.iv_message_back) {
            super.onBackPressed();
        }
    }
}
