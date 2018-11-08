package com.corey.customview.chart;

import android.graphics.PointF;

/**
 * @author caosanyang
 * @date 2018/11/8
 */
public class ScalePoint extends PointF {

    private float valueX;

    private float valueY;

    public ScalePoint(float x, float y) {
        super(x, y);
    }

    public ScalePoint(float x, float y, float valueX, float valueY) {
        super(x, y);
        this.valueX = valueX;
        this.valueY = valueY;
    }

    public float getValueX() {
        return valueX;
    }

    public void setValueX(float valueX) {
        this.valueX = valueX;
    }

    public float getValueY() {
        return valueY;
    }

    public void setValueY(float valueY) {
        this.valueY = valueY;
    }
}
