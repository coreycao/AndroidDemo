package com.corey.customview.chart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caosanyang
 * @date 2018/11/1
 * 对传入的数据进行处理
 * 将真实的数值映射到图片像素上
 * 分别处理并给出针对 X轴 Y轴 曲线的数据
 */
public class DataProcessor {

    private ChartConfig chartConfig;

    private List<Float> values;

    private List<ScalePoint> yCoordinates;

    private List<ScalePoint> xCoordinates;

    private List<ScalePoint> points;

    public DataProcessor(ChartConfig chartConfig, List<Float> value) {
        this.chartConfig = chartConfig;
        this.values = value;
    }

    public void processData() {
        if (this.values == null || this.values.isEmpty()) {
            return;
        }
        computeYAxisCoordinate();
        computeXAxisCoordinate();
        computeDataPoints();
    }

    private float upperValue;

    private float lowerValue;

    private void computeYAxisCoordinate() {
        if (yCoordinates == null) {
            yCoordinates = new ArrayList<>();
        }

        float max = values.get(0);
        float min = values.get(0);
        for (float f : values) {
            if (f >= max) max = f;
            if (f <= min) min = f;
        }

        upperValue = (float) DataProcessor.ceil2Int((long) max);

        lowerValue = (float) DataProcessor.floor2Int((long) min);

        float midValue = (upperValue - lowerValue) / 2 + lowerValue;

        float upperBoundaryPixel = mappingValueY2Pixel(upperValue, lowerValue, upperValue);

        float lowerBoundaryPixel = mappingValueY2Pixel(upperValue, lowerValue, lowerValue);

        float midDividerPixel = (lowerBoundaryPixel - upperBoundaryPixel) / 2;

        this.yCoordinates.add(new ScalePoint(0, upperBoundaryPixel + 40, 0, upperValue));

        this.yCoordinates.add(new ScalePoint(0, midDividerPixel, 0, midValue));

        this.yCoordinates.add(new ScalePoint(0, lowerBoundaryPixel - 40, 0, lowerValue));

    }

    private float step;

    private void computeXAxisCoordinate() {
        if (xCoordinates == null) {
            xCoordinates = new ArrayList<>();
        }
        step = (chartConfig.getWidthOfXAxis() - chartConfig.endOffset - chartConfig.startOffset) / (chartConfig.numOfPoints - 1);
        int start = chartConfig.numOfPoints < values.size()
                ? values.size() - chartConfig.numOfPoints : 0;
        for (int i = start; i < values.size(); i++) {
            this.xCoordinates.add(new ScalePoint(step * i + chartConfig.startOffset, chartConfig.heightOfXAxis / 2, 0, 0));
        }
    }

    private void computeDataPoints() {
        if (points == null) {
            points = new ArrayList<>();
        }

        int start = chartConfig.numOfPoints < values.size()
                ? values.size() - chartConfig.numOfPoints : 0;

        for (int i = start; i < values.size(); i++) {
            this.points.add(new ScalePoint(step * i + chartConfig.startOffset, mappingValueY2Pixel(upperValue,lowerValue,values.get(i)), 0, values.get(i)));
        }

    }

    public List<ScalePoint> getYCoordinates() {
        return yCoordinates;
    }

    public List<ScalePoint> getXCoordinates() {
        return xCoordinates;
    }

    public List<ScalePoint> getPoints() {
        return points;
    }

    private float mappingValueY2Pixel(float upper, float lower, float target) {
        return chartConfig.getHeightOfYAxis() - (target - lower) / (upper - lower) * chartConfig.getHeightOfYAxis();
    }

    /**
     * 去零头并向上取整
     * eg: 23333 -> 30000
     *
     * @param d
     * @return
     */
    public static double ceil2Int(long d) {
        d = Math.abs(d);
        int i = 0;
        while (d / 10 > 0) {
            d = d / 10;
            i++;
        }
        return Math.pow(10, i) * (d + 1);
    }

    /**
     * 去零头并向下取整
     * eg: 23333 -> 20000
     *
     * @param d
     * @return
     */
    public static double floor2Int(long d) {
        d = Math.abs(d);
        int i = 0;
        while (d / 10 > 0) {
            d = d / 10;
            i++;
        }
        return Math.pow(10, i) * (d);
    }

    private static final double BILLION = 100000000d;

    private static final double TEN_THOUSAND = 10000d;

    public static String currencyFormat(double d) {
        d = Math.abs(d);

        String unit = "";

        if (d >= BILLION) {
            d = d / BILLION;
            unit = "亿";
        } else {
            d = d / TEN_THOUSAND;
            unit = "万";
        }

        return new DecimalFormat("####.#").format(d) + unit;
    }
}
