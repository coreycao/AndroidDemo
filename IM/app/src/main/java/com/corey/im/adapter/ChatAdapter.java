package com.corey.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.corey.im.R;
import com.corey.im.entity.ChatEntity;

import java.util.List;

/**
 * Created by sycao on 2017/11/6.
 * 会话列表页adapter
 */

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private List<ChatEntity> data;

    public ChatAdapter(Context context, List<ChatEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ChatEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatEntity chat = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat, null);
            viewHolder = new ViewHolder();

            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_item_chat_name);
            viewHolder.tvTime = (TextView) view.findViewById(R.id.tv_item_chat_time);
            viewHolder.tvMsg = (TextView) view.findViewById(R.id.tv_item_chat_msg);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName.setText(chat.getFriendName());
        viewHolder.tvTime.setText(chat.getUpdateTime());
        viewHolder.tvMsg.setText(chat.getLatestMsg());

        return view;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvTime;
        TextView tvMsg;
    }
}
