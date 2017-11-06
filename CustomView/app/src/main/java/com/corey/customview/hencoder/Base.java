package com.corey.customview.hencoder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.corey.customview.R;

/**
 * Created by sycao on 2017/11/1.
 */

public class Base extends View {

    Paint mPaint = new Paint();

    // 开启抗锯齿
    Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);

    Rect rect = new Rect();

    RectF rectF = new RectF();

    public Base(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 开启抗锯齿
        mPaint2.setAntiAlias(true);

        mPaint.setColor(getResources().getColor(R.color.colorAccent));

        canvas.drawColor(getResources().getColor(R.color.colorPrimary));

        rect.set(10, 10, 50, 50);
        canvas.drawRect(rect, mPaint);


        canvas.drawCircle(80.0f, 80.0f, 8.0f, mPaint);


    }

    private void setPaint() {
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3.0f);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
}
