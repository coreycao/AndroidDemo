package com.corey.customview.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class StairLayout extends ViewGroup {
    public StairLayout(Context context) {
        super(context);
    }

    public StairLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StairLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i % 3 == 0) {
                child.layout(l, t + (i * child.getMeasuredHeight()), r + child.getMeasuredWidth(), b + child.getMeasuredHeight());
            } else if (i % 3 == 1) {
                child.layout(l + 20, t + (i * child.getMeasuredHeight()), r + child.getMeasuredWidth(), b + child.getMeasuredHeight());
            } else if (i % 3 == 2) {
                child.layout(l + 40, t + (i * child.getMeasuredHeight()), r + child.getMeasuredWidth(), b + child.getMeasuredHeight());
            }
        }
    }
}
