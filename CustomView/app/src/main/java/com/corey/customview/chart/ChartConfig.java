package com.corey.customview.chart;

/**
 * @author caosanyang
 * @date 2018/11/8
 * size & style config of ChartView and its child
 */
public class ChartConfig {

    public int widthOfChart;

    public int heightOfChart;

    public int widthOfTipView = 500;

    public int heightOfTipView = 200;

    public int widthOfXAxis;

    public int heightOfXAxis = 150;

    public int widthOfYAxis = 200;

    public int heightOfYAxis;

    public float startOffset = 100f;

    public float endOffset = 100f;

    public int numOfPoints = 10;

    public int getWidthOfXAxis() {
        return widthOfChart - widthOfYAxis;
    }

    public int getHeightOfYAxis() {
        return heightOfChart - heightOfXAxis - heightOfTipView;
    }
}
