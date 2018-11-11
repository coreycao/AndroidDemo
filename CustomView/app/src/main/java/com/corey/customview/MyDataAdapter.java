package com.corey.customview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caosanyang
 * @date 2018/11/9
 */
public class MyDataAdapter extends BaseAdapter {

    List<String> mData;

    public MyDataAdapter(List<String> data) {
        this.mData = data;
    }

    public void update(List<String> newData){
        if (mData == null){
            mData = new ArrayList<>();
        }

        mData.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View rootView = convertView;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            rootView = layoutInflater.inflate(R.layout.item_card, parent, false);
            holder = new ViewHolder();
            holder.nameText = (TextView) rootView.findViewById(R.id.tv_item);
            rootView.setTag(holder);
        } else {
            holder = (ViewHolder) rootView.getTag();
        }
        holder.nameText.setText(getItem(position));
        return rootView;
    }

    private static class ViewHolder {
        TextView nameText = null;
    }
}
