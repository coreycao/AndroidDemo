package com.corey.customview.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author caosanyang
 * @date 2018/10/31
 * 纵轴绘制
 * 传入的是真实数据，在该 View 中自行进行计算处理后绘制刻度
 */
public class YAxisView extends View {

    private Paint mPaint;

    private Transformer transformer;

    public YAxisView(Context context) {
        this(context, null);
    }

    public YAxisView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YAxisView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(42);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (this.dataProcessor == null) {
            return;
        }

        drawYAxis(canvas);

    }

    private void drawYAxis(Canvas canvas) {
        if (transformer != null) {
            for (ScalePoint point : dataProcessor.getYCoordinates()) {
                canvas.drawText(transformer.transform(point.getValueY()), 0,  point.y, mPaint);
            }
        } else {
            for (ScalePoint point : dataProcessor.getYCoordinates()) {
                canvas.drawText(String.valueOf(point.getValueY()), 0, point.y, mPaint);
            }

        }
    }

    private DataProcessor dataProcessor;

    public void setValues(DataProcessor processor, Transformer transformer) {
        this.dataProcessor = processor;
        this.transformer = transformer;
    }

    /**
     * 坐标轴数值转化
     */
    interface Transformer {
        String transform(float value);
    }
}
