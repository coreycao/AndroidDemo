package com.corey.customview.pager;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caosanyang
 * @date 2018/11/11
 */
public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.ItemVH> {

    /**
     * 是否已经添加了 footer view
     */
    private volatile boolean hasFooterView = false;

    /**
     * 数据类型
     */
    private static final int TYPE_DATA = 0x01;
    private static final int TYPE_SECTION = 0x02;
    private static final int TYPE_FOOTER = 0x03;

    /**
     * footer view 的状态
     */
    public static final int FOOTER_STATUS_SUCCESS = 0x04;
    public static final int FOOTER_STATUS_FAILURE = 0x05;
    public static final int FOOTER_STATUS_LOADING = 0x06;

    /**
     * data set
     */
    private List<ItemData> mData;

    public PagerAdapter() {
        mData = new ArrayList<>();
    }

    public PagerAdapter(List<ItemData> data) {
        mData = data;
    }

    public void addData(List<ItemData> data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        if (hasFooterView) {
            mData.addAll(mData.size() - 1, data);
        } else {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public ItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ItemVH itemVH = null;
        switch (viewType) {
            case TYPE_DATA:
                view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                itemVH = new DataVH(view);
                break;
            case TYPE_SECTION:
                view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                itemVH = new SectionHeaderVH(view);
                break;
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                itemVH = new FooterVH(view);
                break;
        }
        return itemVH;
    }

    @Override
    public void onBindViewHolder(ItemVH holder, int position) {
        ItemData itemData = mData.get(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_DATA:
                ListData listData = (ListData) itemData;
                DataVH dataVH = (DataVH) holder;
                dataVH.tv.setText(listData.desc);
                break;
            case TYPE_SECTION:
                SectionHeaderData sectionHeaderData = (SectionHeaderData) itemData;
                SectionHeaderVH sectionHeaderVH = (SectionHeaderVH) holder;
                sectionHeaderVH.tv.setText(sectionHeaderData.sectionName);
                break;
            case TYPE_FOOTER:
                FooterData footerData = (FooterData) itemData;
                FooterVH footerVH = (FooterVH) holder;
                footerVH.tv.setText(footerData.status);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getDataSize() {
        int count = 0;
        for (ItemData itemData : mData) {
            if (itemData instanceof ListData) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    private void setFootView(FooterData footerData) {
        hasFooterView = true;
        if (this.mData == null) mData = new ArrayList<>();
        footerData.position = 0;
        this.mData.add(footerData);
        notifyItemInserted(mData.size() - 1);
    }

    public void updateFooterStatus(int statusCode) {
        FooterData footerData = new FooterData();
        switch (statusCode) {
            case FOOTER_STATUS_LOADING:
                footerData.status = "加载中...";
                break;
            case FOOTER_STATUS_SUCCESS:
                footerData.status = "加载成功！";
                break;
            case FOOTER_STATUS_FAILURE:
                footerData.status = "加载失败...";
                break;
        }
        addFooterView(footerData);
    }

    public void addFooterView(FooterData footerData) {
        footerData.position = 0;
        if (hasFooterView) {
            FooterData existedFooter = (FooterData) this.mData.get(mData.size() - 1);
            existedFooter.status = footerData.status;
            notifyItemChanged(mData.size() - 1);
        } else {
            if (this.mData == null) mData = new ArrayList<>();
            this.mData.add(footerData);
            notifyItemInserted(mData.size() - 1);
            hasFooterView = true;
        }
    }

    public void removeFooterView() {
        if (!hasFooterView) return;
        this.mData.remove(mData.size() - 1);
        notifyItemRemoved(mData.size());
        hasFooterView = false;
    }

    /**
     * Base Data Model
     */
    public static abstract class ItemData {
        public int position;

        abstract int getItemType();
    }

    public static class ListData extends ItemData {

        public int secondId;

        public String desc;

        @Override
        int getItemType() {
            return TYPE_DATA;
        }
    }

    public static class SectionHeaderData extends ItemData {

        public String sectionName;

        @Override
        int getItemType() {
            return TYPE_SECTION;
        }
    }

    public static class FooterData extends ItemData {

        public String status;

        @Override
        int getItemType() {
            return TYPE_FOOTER;
        }
    }

    /**
     * Base ViewHolder
     */
    static abstract class ItemVH extends RecyclerView.ViewHolder {

        ItemVH(View itemView) {
            super(itemView);
        }

        abstract int getViewType();
    }

    private static class DataVH extends ItemVH {

        TextView tv;

        DataVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(android.R.id.text1);
        }

        @Override
        int getViewType() {
            return TYPE_DATA;
        }
    }

    private static class SectionHeaderVH extends ItemVH {

        TextView tv;

        SectionHeaderVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(android.R.id.text1);
            tv.setBackgroundColor(Color.RED);
        }

        @Override
        int getViewType() {
            return TYPE_SECTION;
        }
    }

    private static class FooterVH extends ItemVH {

        TextView tv;

        FooterVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(android.R.id.text1);
            tv.setBackgroundColor(Color.YELLOW);
        }

        @Override
        int getViewType() {
            return TYPE_FOOTER;
        }
    }

}
