package com.corey.basic.tv.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * Created by caosanyang on 2018/7/27.
 */
public class TvViewPager extends ViewPager {
  public TvViewPager(Context context) {
    super(context);
  }

  public TvViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  // disable the key event
  @Override
  public boolean executeKeyEvent(KeyEvent event) {
    return false;
  }
}
