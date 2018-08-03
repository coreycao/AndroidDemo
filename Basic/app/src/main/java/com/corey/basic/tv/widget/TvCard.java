package com.corey.basic.tv.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by caosanyang on 2018/7/26.
 */
public class TvCard extends LinearLayout implements View.OnFocusChangeListener {
  public TvCard(Context context) {
    this(context, null);
  }

  public TvCard(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TvCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setOnFocusChangeListener(this);
  }

  @Override
  public void onFocusChange(View v, boolean hasFocus) {
    if (focusChangeListener != null) {
      focusChangeListener.onFocusChange(v, hasFocus);
    }
    if (hasFocus) {
      ViewCompat.animate(this).scaleX(1.05f).scaleY(1.05f).start();
    } else {
      ViewCompat.animate(this).scaleX(1.0f).scaleY(1.0f).start();
    }
  }

  private FocusChangeListener focusChangeListener;

  public void setFocusChangeListener(FocusChangeListener listener) {
    this.focusChangeListener = listener;
  }

  public interface FocusChangeListener {
    void onFocusChange(View v, boolean hasFocus);
  }
}
