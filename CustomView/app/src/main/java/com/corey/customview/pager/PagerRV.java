package com.corey.customview.pager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author caosanyang
 * @date 2018/11/11
 */
public class PagerRV extends RecyclerView {
    public PagerRV(Context context) {
        super(context);
    }

    public PagerRV(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerRV(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if (onLoadMoreListener == null) return;
        LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
        PagerAdapter adapter = (PagerAdapter) getAdapter();
        Log.d("pagerRV","onScroll#LastCompletelyVisibleItemPosition:" + manager.findLastCompletelyVisibleItemPosition());
        Log.d("pagerRV","onScroll#getItemCount:" + adapter.getDataSize());
        if (manager.findLastCompletelyVisibleItemPosition() >= adapter.getDataSize()) {
            onLoadMoreListener.onLoadMore();
        }
    }

    private OnLoadMoreListener onLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
