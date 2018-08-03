package com.corey.basic.tv.widget;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by caosanyang on 2018/7/31.
 */
public class ShakeAnimHelper {

  private ShakeAnimHelper() {
  }

  public static final int SHAKE_ORIENTATION_HORIZONTAL = 1;

  public static final int SHAKE_ORIENTATION_VERTICAL = 2;

  private int shakeXDelta = 10;

  private int shakeYDelta = 10;

  private int shakeDuration = 500;

  private int shakeCycleTimes = 3;

  public void performShakeAnim(View target, int orientation) {
    Animation shakeAnim = null;
    if (orientation == SHAKE_ORIENTATION_HORIZONTAL) {
      shakeAnim = new TranslateAnimation(0, shakeXDelta, 0, 0);
      shakeAnim.setDuration(shakeDuration);
      shakeAnim.setInterpolator(new CycleInterpolator(shakeCycleTimes));
    } else if (orientation == SHAKE_ORIENTATION_VERTICAL) {
      shakeAnim = new TranslateAnimation(0, 0, 0, shakeYDelta);
      shakeAnim.setDuration(shakeDuration);
      shakeAnim.setInterpolator(new CycleInterpolator(shakeCycleTimes));
    }

    if (target != null && shakeAnim != null) target.startAnimation(shakeAnim);
  }

  public static class Builder {

    private final ShakeAnimHelper shakeAnimHelper;

    public Builder() {
      shakeAnimHelper = new ShakeAnimHelper();
    }

    public Builder setShakeXDelta(int shakeXDelta) {
      shakeAnimHelper.shakeXDelta = shakeXDelta;
      return this;
    }

    public Builder setShakeYDelta(int shakeYDelta) {
      shakeAnimHelper.shakeYDelta = shakeYDelta;
      return this;
    }

    public Builder setShakeDuration(int shakeDuration) {
      shakeAnimHelper.shakeDuration = shakeDuration;
      return this;
    }

    public Builder setShakeCycleTimes(int shakeCycleTimes) {
      shakeAnimHelper.shakeCycleTimes = shakeCycleTimes;
      return this;
    }

    public ShakeAnimHelper build() {
      return shakeAnimHelper;
    }
  }
}
