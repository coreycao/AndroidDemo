package com.corey.im.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.corey.im.R;
import com.corey.im.adapter.ChatAdapter;
import com.corey.im.entity.ChatEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sycao on 2017/11/6.
 * 会话列表页
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Context context = this;
    ListView lvChatList;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatAdapter = new ChatAdapter(this, getMockData());
        lvChatList = (ListView) findViewById(R.id.lv_chat_list);
        lvChatList.setAdapter(chatAdapter);
        lvChatList.setOnItemClickListener(this);
    }

    private List<ChatEntity> getMockData() {
        List<ChatEntity> toReturn = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            toReturn.add(new ChatEntity("", "Jame" + i, "hi", "Yesterday"));
        }
        return toReturn;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, MessageActivity.class));
    }
}
