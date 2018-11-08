package com.corey.customview.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author caosanyang
 * @date 2018/11/1
 * 详情气泡
 * 气泡在图表中的高度是固定的，气泡的位置，以及下方箭头的位置都需要根据对应数据点的位置进行调整
 */
public class TipView extends View {


    private Paint mTextPaint;

    public TipView(Context context) {
        this(context, null);
    }

    public TipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
