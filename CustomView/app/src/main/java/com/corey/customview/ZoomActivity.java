package com.corey.customview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.corey.customview.view.ZoomInScrollView;

/**
 * Created by sycao on 2018/6/15.
 */

public class ZoomActivity extends AppCompatActivity {

    private static final String TAG = "ZoomActivity";

    ZoomInScrollView zoomInScrollView;

    LinearLayout llHead;

    LinearLayout llToobar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        llHead = findViewById(R.id.ll_head);
        llToobar = findViewById(R.id.ll_toolbar);
        final int headHeight = llHead.getLayoutParams().height;
        final int toolbarHeight = llToobar.getLayoutParams().height;
        zoomInScrollView = findViewById(R.id.zoom_scroll_view);
        zoomInScrollView.setOnScrollListener(new ZoomInScrollView.OnScrollListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {
                float move_distance = headHeight - toolbarHeight;
                if (dy<=move_distance){
                    float percent = dy / move_distance;
                    llToobar.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    llToobar.setAlpha(percent);
                }
//                if (!isUp && dy <= move_distance) {
//                    //手指往上滑,距离未超过头部高度
//                    // iv_back.setImageResource(R.mipmap.back_white);
//                    float percent = dy / move_distance;
//                    llToobar.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
//                    llToobar.setAlpha(percent);
//                } else if (!isUp && dy > move_distance) {
//                    // 手指往上滑,距离超过头部高度
//                    // iv_back.setImageResource(R.mipmap.back_black);
//                    llToobar.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
//
//                } else if (isUp && dy > move_distance) {
//                    //返回顶部，但距离头部位置大于头部高度
//
//                } else if (isUp && dy <= move_distance) {
//                    //返回顶部，但距离头部位置小于头部高度
//                    // iv_back.setImageResource(R.mipmap.back_white);
//                    float percent = dy / move_distance;
//                    llToobar.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
//                    llToobar.setAlpha(percent);
//                }
            }
        });
    }
}
