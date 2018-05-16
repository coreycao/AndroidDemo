package com.corey.customview.devart;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.corey.customview.R;

/**
 * Created by sycao on 2018/5/16.
 */

public class DevArtActivity extends AppCompatActivity {

    CircleView circleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devart);

        findViewById(R.id.btn_move).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(circleView, "translationX", 0, 100)
                        .setDuration(100).start();
            }
        });

        circleView = (CircleView) findViewById(R.id.circle_view);
    }
}
