package com.corey.customview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author caosanyang
 * @date 2018/11/7
 */
public class DetailFragment extends Fragment {

    View rootView;

    MyScrollView scrollView;

    LinearLayout layoutCover;

    int screenHeight = 1080;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        scrollView = rootView.findViewById(R.id.scrollView_detail);
        layoutCover = rootView.findViewById(R.id.ll_detail_container);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollView.setOnScrollChangeListener((offsetY) -> {
            if (offsetY == 0) {
                scrollView.setBackgroundColor(getActivity().getResources().getColor(R.color.blue_60));
            } else {
                scrollView.setBackgroundColor(Color.TRANSPARENT);
            }
            layoutCover.setBackgroundColor(Color.argb(200 * (offsetY > 1080 ? 1080 : offsetY) / 1080, 0, 0, 0));
        });
    }
}
