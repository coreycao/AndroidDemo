package com.corey.customview.chart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Arrays;
import java.util.List;

/**
 * @author caosanyang
 * @date 2018/10/29
 */
public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Float> floats = Arrays.asList(1400f, 2000f, 5000f, 4000f, 3000f, 6000f, 7500f, 4000f, 3000f, 1500f);
//        List<Float> floats = Arrays.asList(1400f);

//        List<Float> floats = Arrays.asList(1400f, 2000f, 5000f,2000f);

        BesselChart besselChart = new BesselChart(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        besselChart.setLayoutParams(lp);

        setContentView(besselChart);

        besselChart.setValues(floats);
    }
}
