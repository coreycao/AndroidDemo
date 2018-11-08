package com.corey.customview.table;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author caosanyang
 * @date 2018/11/6
 */
public class FocusableCard extends LinearLayout {


    public FocusableCard(Context context) {
        this(context, null);
    }

    public FocusableCard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusableCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    FocusableCard.this.animate().scaleX(1.05f).scaleY(1.05f).start();
                } else {
                    FocusableCard.this.animate().scaleX(1.0f).scaleY(1.0f).start();
                }
            }
        });
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(60);
        tv.setTextColor(Color.WHITE);
        tv.setText("Hello Scroller");
        addView(tv);
    }
}
