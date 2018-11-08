package com.corey.customview.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * @author caosanyang
 * @date 2018/10/29
 * 利用贝塞尔曲线绘制出的平滑曲线
 */
public class BesselView extends View {

    private Paint mPointPaint;

    private Paint mPathPaint;

    private Paint mLinePaint;

    private Paint mGridPaint;

    private Path mPath;

    private DataProcessor dataProcessor;

    public BesselView(Context context) {
        this(context, null);
    }

    public BesselView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BesselView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setStrokeWidth(20);

        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setColor(Color.BLUE);
        mPathPaint.setStrokeWidth(10);
        mPathPaint.setStyle(Paint.Style.STROKE);

        mGridPaint = new Paint();
        mGridPaint.setAntiAlias(true);
        mGridPaint.setColor(Color.BLACK);
        mGridPaint.setStrokeWidth(15);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(Color.YELLOW);
        mLinePaint.setStrokeWidth(15);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);

        if (dataProcessor == null) return;

        processPath(this.dataProcessor.getPoints());

        drawGrid(canvas);

        drawPath(canvas);

        drawPoint(canvas);

    }

    private void drawGrid(Canvas canvas) {
        for (ScalePoint point : this.dataProcessor.getYCoordinates()) {
            canvas.drawLine(0, point.y, getWidth(), point.y, mGridPaint);
        }
    }

    private void drawPath(Canvas canvas) {
        // draw mPath
        canvas.drawPath(this.mPath, mPathPaint);

        ScalePoint lastPoint = this.dataProcessor.getPoints().get(this.dataProcessor.getPoints().size()-1);
        canvas.drawLine(lastPoint.x, lastPoint.y, lastPoint.x, getHeight(), mLinePaint);
    }

    private void drawPoint(Canvas canvas) {
        for (ScalePoint point : this.dataProcessor.getPoints()) {
            canvas.drawPoint(point.x, point.y, mPointPaint);
        }
    }

    public void setValues(DataProcessor processor) {
        this.dataProcessor = processor;
    }

    private static final float CTRL_VALUE_A = 0.2f;

    private static final float CTRL_VALUE_B = 0.2f;

    private void calculateControlPoint(List<ScalePoint> pointFList, int currentIndex,
                                       PointF ctrlPointA, PointF ctrlPointB) {
        ctrlPointA.x = pointFList.get(currentIndex).x +
                (pointFList.get(currentIndex + 1).x - pointFList.get(currentIndex - 1).x) * CTRL_VALUE_A;
        ctrlPointA.y = pointFList.get(currentIndex).y +
                (pointFList.get(currentIndex + 1).y - pointFList.get(currentIndex - 1).y) * CTRL_VALUE_A;
        ctrlPointB.x = pointFList.get(currentIndex + 1).x -
                (pointFList.get(currentIndex + 2).x - pointFList.get(currentIndex).x) * CTRL_VALUE_B;
        ctrlPointB.y = pointFList.get(currentIndex + 1).y -
                (pointFList.get(currentIndex + 2).y - pointFList.get(currentIndex).y) * CTRL_VALUE_B;
    }

    private void processPath(List<ScalePoint> pointFList) {
        pointFList.add(0, new ScalePoint(pointFList.get(0).x, pointFList.get(0).y));
        pointFList.add(new ScalePoint(pointFList.get(pointFList.size() - 1).x,
                pointFList.get(pointFList.size() - 1).y));
        pointFList.add(new ScalePoint(pointFList.get(pointFList.size() - 1).x,
                pointFList.get(pointFList.size() - 1).y));

        mPath.moveTo(pointFList.get(0).x, pointFList.get(0).y);

        PointF ctrlPointA = new PointF();
        PointF ctrlPointB = new PointF();
        for (int i = 1; i < pointFList.size() - 3; i++) {
            calculateControlPoint(pointFList, i, ctrlPointA, ctrlPointB);
            mPath.cubicTo(ctrlPointA.x, ctrlPointA.y, ctrlPointB.x, ctrlPointB.y,
                    pointFList.get(i + 1).x, pointFList.get(i + 1).y);
        }
        pointFList.remove(0);
        pointFList.remove(pointFList.size() - 1);
        pointFList.remove(pointFList.size() - 1);
    }

}
