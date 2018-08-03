package com.corey.basic.tv.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by caosanyang on 2018/7/26.
 * Ref https://blog.csdn.net/yiranhaiziqi/article/details/77937463
 */
public class TvListRV extends RecyclerView {

  private static final String TAG = "TvListRv";

  public TvListRV(Context context) {
    this(context, null);
  }

  public TvListRV(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TvListRV(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    // init();
  }

  // todo 一些优化
  private void init() {
    setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
    setHasFixedSize(true);
    setWillNotDraw(true);
    setOverScrollMode(View.OVER_SCROLL_NEVER);
    setChildrenDrawingOrderEnabled(true);

    setClipChildren(false);
    setClipToPadding(false);

    setClickable(false);
    setFocusable(true);
    setFocusableInTouchMode(true);
    /*
     防止RecyclerView刷新时焦点不错乱bug的步骤如下:
     (1)adapter执行setHasStableIds(true)方法
     (2)重写getItemId()方法,让每个view都有各自的id
     (3)RecyclerView的动画必须去掉
     */
    setItemAnimator(null);
  }

  /**
   * 目前只支持垂直方向的 LinearLayoutManager
   */
  @Override
  public void setLayoutManager(LayoutManager layout) {
    if (layout instanceof LinearLayoutManager &&
        ((LinearLayoutManager) layout).getOrientation() == LinearLayoutManager.VERTICAL) {
      super.setLayoutManager(layout);
    } else {
      throw new UnsupportedOperationException(
          "TvListRV's LayoutManager can only be a #VERTICAL# LinearLayoutManager");
    }
  }

  @Override
  public boolean isInTouchMode() {
    // 解决4.4版本抢焦点的问题
    if (Build.VERSION.SDK_INT == 19) {
      return !(hasFocus() && !super.isInTouchMode());
    } else {
      return super.isInTouchMode();
    }
  }

  public int getFirstVisiblePosition() {
    if (getChildCount() == 0) {
      return 0;
    } else {
      return getChildAdapterPosition(getChildAt(0));
    }
  }

  public int getLastVisiblePosition() {
    final int childCount = getChildCount();
    if (childCount == 0) {
      return 0;
    } else {
      return getChildAdapterPosition(getChildAt(childCount - 1));
    }
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    boolean result = super.dispatchKeyEvent(event);
    View focusView = this.getFocusedChild();
    if (focusView == null) {
      return result;
    } else {
      if (event.getAction() == KeyEvent.ACTION_UP) {
        return true;
      } else {
        switch (event.getKeyCode()) {
          case KeyEvent.KEYCODE_DPAD_RIGHT:
            View rightView =
                FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
            if (rightView != null) {
              rightView.requestFocus();
              return true;
            } else {
              return false;
            }
          case KeyEvent.KEYCODE_DPAD_LEFT:
            View leftView =
                FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
            // Log.i(TAG, "leftView is null:" + (leftView == null));
            if (leftView != null) {
              leftView.requestFocus();
              return true;
            } else {
              return false;
            }
          case KeyEvent.KEYCODE_DPAD_DOWN:
            // TODO: 2018/7/31 最后一项且完全可见时，抖动
            if (getCurrentItemViewPosition() == getLayoutManager().getItemCount() - 1
                && isFullyBottom()) {
              new ShakeAnimHelper.Builder().build()
                  .performShakeAnim(
                      getLayoutManager().findViewByPosition(getCurrentItemViewPosition()),
                      ShakeAnimHelper.SHAKE_ORIENTATION_VERTICAL);
              return true;
            }
            if (isLastItemVisible()) {
              this.smoothScrollToPosition(getLastVisiblePosition());
              return result;
            }
            View downView =
                FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_DOWN);
            // Log.i(TAG, " downView is null:" + (downView == null));
            if (downView != null) {
              downView.requestFocus();
              int downOffset = downView.getTop() + downView.getHeight() / 2 - getHeight() / 2;
              this.smoothScrollBy(0, downOffset);
            }
            return true;
          case KeyEvent.KEYCODE_DPAD_UP:
            View upView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_UP);
            // Log.i(TAG, "upView is null:" + (upView == null));
            if (upView != null) {
              upView.requestFocus();
              int upOffset = getHeight() / 2 - (upView.getBottom() - upView.getHeight() / 2);
              this.smoothScrollBy(0, -upOffset);
              return true;
            } else {
              return result;//返回false,否则第一行按上键回不到导航栏
            }
          case KeyEvent.KEYCODE_BACK:
            if (getLayoutManager().getItemCount() > 0) {
              smoothScrollToPosition(0);
              focusView.clearFocus();
            }

            return true;
        }
      }
    }
    return result;
  }

  //防止Activity时,RecyclerView崩溃
  @Override
  protected void onDetachedFromWindow() {
    if (getLayoutManager() != null) {
      super.onDetachedFromWindow();
    }
  }

  /**
   * 判断是否已经滑动到底部
   *
   * @return true if the bottom visible item is the last item in the data set
   */
  private boolean isLastItemVisible() {
    LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
    int visibleItemCount = layoutManager.getChildCount();
    int totalItemCount = layoutManager.getItemCount();
    return (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1);
  }

  private int getCurrentItemViewPosition() {
    View focusView = this.getFocusedChild();
    if (focusView != null && findContainingItemView(focusView) != null) {
      return findContainingViewHolder(focusView).getLayoutPosition();
    } else {
      return -1;
    }
  }

  /**
   * 判断是否完全滑到底部
   *
   * @return true, 如果最后一个完全可见
   */
  private boolean isFullyBottom() {
    LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
    int pos = layoutManager.findLastCompletelyVisibleItemPosition();
    return (layoutManager.getChildCount() > 0 && pos == layoutManager.getItemCount() - 1);
  }
}
