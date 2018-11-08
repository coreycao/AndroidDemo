package com.corey.customview.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.corey.customview.R;

import java.util.List;

/**
 * @author caosanyang
 * @date 2018/10/31
 * combine XAxisView, YAxisView, BesselView, TipView together
 */
public class BesselChart extends RelativeLayout {

    private XAxisView xAxisView;

    private YAxisView yAxisView;

    private BesselView besselView;

    private ChartConfig chartConfig;

    public BesselChart(Context context) {
        this(context, null);
    }

    public BesselChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BesselChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        chartConfig = new ChartConfig();

        xAxisView = new XAxisView(getContext());

        yAxisView = new YAxisView(getContext());

        besselView = new BesselView(getContext());

        RelativeLayout.LayoutParams lpY = new RelativeLayout.LayoutParams(chartConfig.widthOfYAxis, RelativeLayout.LayoutParams.MATCH_PARENT);

        lpY.bottomMargin = chartConfig.heightOfXAxis;
        lpY.topMargin = chartConfig.heightOfTipView;

        yAxisView.setLayoutParams(lpY);
        yAxisView.setId(R.id.id_test);

        RelativeLayout.LayoutParams lpX = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, chartConfig.heightOfXAxis);

        lpX.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lpX.addRule(RelativeLayout.RIGHT_OF, yAxisView.getId());

        xAxisView.setLayoutParams(lpX);

        RelativeLayout.LayoutParams lpCurve = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lpCurve.bottomMargin = chartConfig.heightOfXAxis;
        lpCurve.topMargin = chartConfig.heightOfTipView;
        lpCurve.addRule(RelativeLayout.RIGHT_OF, yAxisView.getId());

        besselView.setLayoutParams(lpCurve);
        besselView.setId(R.id.id_test2);

        addView(yAxisView);
        addView(besselView);
        addView(xAxisView);
    }

    public void setValues(List<Float> values) {
        post(() -> {
            chartConfig.heightOfChart = getHeight();

            chartConfig.widthOfChart = getWidth();

            DataProcessor processor = new DataProcessor(chartConfig, values);

            processor.processData();

            besselView.setValues(processor);

            xAxisView.setValues(processor, value -> String.valueOf(value) + "");

            yAxisView.setValues(processor, value -> String.valueOf(value) + "元");

            invalidate();

            showTipView(processor);


        });
    }

    public void showTipView(DataProcessor processor) {
        PointF latestPoint = processor.getPoints().get(processor.getPoints().size() - 1);
        RelativeLayout.LayoutParams lpTipView = new RelativeLayout.LayoutParams(
                chartConfig.widthOfTipView,
                chartConfig.heightOfTipView);
        if (latestPoint.x <= chartConfig.widthOfTipView / 2) {
            lpTipView.addRule(ALIGN_LEFT, besselView.getId());
        } else if (besselView.getWidth() - latestPoint.x <= chartConfig.widthOfTipView / 2) {
            lpTipView.addRule(ALIGN_RIGHT, besselView.getId());
        } else {
            lpTipView.addRule(ALIGN_LEFT, besselView.getId());
            lpTipView.leftMargin = (int) latestPoint.x - chartConfig.widthOfTipView / 2;
        }
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(lpTipView);
        textView.setText("hello");
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.YELLOW);
        addView(textView);
    }
}
