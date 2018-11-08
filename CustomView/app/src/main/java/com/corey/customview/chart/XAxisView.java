package com.corey.customview.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * @author caosanyang
 * @date 2018/10/31
 * X 轴 刻度的数量固定
 */
public class XAxisView extends View {

    private Paint mPaint;

    private Transformer transformer;

    private DataProcessor processor;

    public XAxisView(Context context) {
        this(context, null);
    }

    public XAxisView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XAxisView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(32);
    }

    public void setValues(DataProcessor processor,Transformer transformer){
        this.processor = processor;
        this.transformer = transformer;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);
        if (processor == null){
            return;
        }
        drawXAxis(canvas);
    }

    /**
     * 绘制天数
     * @param canvas
     */
    private void drawXAxis(Canvas canvas) {
        List<ScalePoint> points = processor.getXCoordinates();
        for (int i = 0; i < points.size(); i++) {
            if (transformer == null) {
                canvas.drawText(String.valueOf(i), points.get(i).x, points.get(i).y, mPaint);
            } else {
                canvas.drawText(transformer.transform(i), points.get(i).x, points.get(i).y, mPaint);
            }
        }
    }

    /**
     * 根据跨年的情况绘制月份或者年份
     * todo
     */
    private void drawMonth(){

    }

    interface Transformer {
        String transform(int value);
    }
}
