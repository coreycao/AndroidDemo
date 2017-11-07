package com.corey.im.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.corey.im.R;
import com.corey.im.ui.entity.MessageEntity;

import java.util.List;

/**
 * Created by sycao on 2017/11/6.
 * 消息聊天页adapter（纯文本）
 */

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<MessageEntity> data;

    public MessageAdapter(Context context, List<MessageEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MessageEntity getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageEntity msg = getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message, null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById
                    (R.id.item_message_left);
            viewHolder.rightLayout = (LinearLayout) view.findViewById
                    (R.id.item_message_right);
            viewHolder.leftMsg = (TextView) view.findViewById(R.id.tv_item_message_left);
            viewHolder.rightMsg = (TextView) view.findViewById(R.id.tv_item_message_right);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (msg.getMessageType() == MessageEntity.MSG_TYPE_REC) {
            // 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getContent());
        } else if (msg.getMessageType() == MessageEntity.MSG_TYPE_SEND) {
            // 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getContent());
        }
        return view;
    }

    class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
    }
}
