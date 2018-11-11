package com.corey.customview.layout;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/**
 * @author caosanyang
 * @date 2018/11/8
 */
public class StretchedLayout extends LinearLayout {

    private OnItemClickListener mOnItemClickListener;

    private ListAdapter mAdapter;

    private DataSetObserver mObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            startUpdate();
        }
    };

    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mObserver);
        }
        startUpdate();
    }

    public StretchedLayout(Context context) {
        this(context, null);
    }

    public StretchedLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StretchedLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        child.setOnClickListener(null);
    }

    @Override
    public void onViewAdded(final View child) {
        super.onViewAdded(child);
        child.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = indexOfChild(child);
                    mOnItemClickListener.onItemClick(StretchedLayout.this, child, position, mAdapter.getItemId(position));
                }
            }
        });
    }

    private void startUpdate() {
        if (mAdapter == null) {
            removeAllViews();
            return;
        }
        int expectCount = mAdapter.getCount();
        int childCount = getChildCount();
        if (childCount > expectCount) {
            for (int i = childCount - 1; i >= expectCount; i--) {
                removeViewAt(i);
            }
            for (int i = 0; i < expectCount; i++) {
                mAdapter.getView(i, getChildAt(i), this);
            }
        } else {
            for (int i = 0; i < childCount; i++) {
                mAdapter.getView(i, getChildAt(i), this);
            }
            for (int i = childCount; i < expectCount; i++) {
                View view = mAdapter.getView(i, null, this);
                LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
                view.setLayoutParams(lp);
                addView(view);
            }
        }

        requestLayout();
    }

    public interface OnItemClickListener {
        void onItemClick(StretchedLayout parent, View view, int position, long id);
    }

}
