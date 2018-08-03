package com.corey.basic.touchevent;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by sycao on 2018/6/19.
 */

public class TouchActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Button btnGo = new Button(this);
    btnGo.setText("touch me");
    btnGo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    btnGo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(TouchActivity.this, "click me success", Toast.LENGTH_SHORT).show();
      }
    });
    setContentView(btnGo);
    expandViewTouchDelegate(btnGo, 0, 500, 0, 0);
    btnGo.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        Toast.makeText(TouchActivity.this, "touch me success", Toast.LENGTH_SHORT).show();

        return true;
      }
    });
  }

  public static void expandViewTouchDelegate(final View view, final int top,
      final int bottom, final int left, final int right) {

    ((View) view.getParent()).post(new Runnable() {
      @Override
      public void run() {
        Rect bounds = new Rect();
        view.setEnabled(true);
        view.getHitRect(bounds);

        bounds.top -= top;
        bounds.bottom += bottom;
        bounds.left -= left;
        bounds.right += right;

        TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

        if (View.class.isInstance(view.getParent())) {
          ((View) view.getParent()).setTouchDelegate(touchDelegate);
        }
      }
    });
  }
}
