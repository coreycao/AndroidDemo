package com.corey.basic.tv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by caosanyang on 2018/7/31.
 */
public class TvScrollView extends ScrollView {
  public TvScrollView(Context context) {
    super(context);
  }

  public TvScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TvScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
    super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    Animation shakeAnim = new TranslateAnimation(0, 0, 0, 10);
    shakeAnim.setDuration(500);
    shakeAnim.setInterpolator(new CycleInterpolator(2));
    this.startAnimation(shakeAnim);
  }
}
