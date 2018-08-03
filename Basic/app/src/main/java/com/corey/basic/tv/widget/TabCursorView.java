package com.corey.basic.tv.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by caosanyang on 2018/7/23.
 * woking together with {@link NavigationTab}
 */
public class TabCursorView extends AppCompatImageView {

  public TabCursorView(Context context) {
    this(context, null);
  }

  public TabCursorView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TabCursorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setVisibility(INVISIBLE);
  }

  // just move
  public void move(int distance) {
    int realLocation = distance - getWidth() / 2;
    if (mLastLocation == realLocation) return;
    if (set.isRunning()) set.cancel();
    createAnimator(realLocation).start();
    mLastLocation = realLocation;
  }

  // move & scale to adjust self's size
  public void move(int distance, int width) {
    int realLocation = distance - getWidth() / 2;
    if (mLastLocation == realLocation) return;
    if (set.isRunning()) set.cancel();
    createAnimator(realLocation, width, null).start();
    mLastLocation = realLocation;
  }

  // initial appear
  public void appear(int distance, int width) {
    int realLocation = distance - getWidth() / 2;
    if (mLastLocation == realLocation) return;
    if (set.isRunning()) set.cancel();
    createAnimator(realLocation, width, new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {

      }

      @Override public void onAnimationEnd(Animator animation) {
        if (TabCursorView.this != null) {
          TabCursorView.this.setVisibility(VISIBLE);
        }
      }

      @Override public void onAnimationCancel(Animator animation) {

      }

      @Override public void onAnimationRepeat(Animator animation) {

      }
    }).start();
    mLastLocation = realLocation;
  }

  /**
   * @param width the new width
   * @deprecated resize self's size by change width property, deprecated now
   */
  public void resize(int width) {
    ViewWrapper viewWrapper = new ViewWrapper(this);
    ObjectAnimator.ofInt(viewWrapper, "width", width).start();
  }

  // enable the "width" property's animator
  private static class ViewWrapper {
    private View mTarget;

    public ViewWrapper(View view) {
      this.mTarget = view;
    }

    public void setWidth(int width) {
      mTarget.getLayoutParams().width = width;
      mTarget.requestLayout();
    }

    public int getWidth() {
      return mTarget.getLayoutParams().width;
    }
  }

  private long mDuration = 150L;
  private int mLastLocation = 0;
  private final AnimatorSet set = new AnimatorSet();

  public AnimatorSet createAnimator(int location) {
    ObjectAnimator translationX =
        ObjectAnimator.ofFloat(this, "translationX", (float) mLastLocation, (float) location);
    set.setDuration(mDuration);
    set.playTogether(translationX);
    return set;
  }

  public AnimatorSet createAnimator(int location, int width, Animator.AnimatorListener listener) {
    ObjectAnimator translationX =
        ObjectAnimator.ofFloat(this, "translationX", (float) mLastLocation, (float) location);
    ObjectAnimator scaleX =
        ObjectAnimator.ofFloat(this, "scaleX", 1.0f, (float) width / (float) getWidth());
    set.setDuration(mDuration);
    set.playTogether(scaleX, translationX);
    if (listener != null) {
      set.addListener(listener);
    }
    return set;
  }
}
