package com.corey.basic.tv.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by caosanyang on 2018/7/26.
 * <p>
 * 目前仅能配合 GridLayoutManager 使用
 * 实现了焦点在左右边界的换行，以及 itemview 未加载出来时焦点的处理
 * 实现原理是自定义遥控器方向按钮事件的分发，当找不到下一个焦点 view 时，让 RV 向指定方向滚动并手动请求新的焦点
 */
public class TvGridRV extends RecyclerView {

  private static final String TAG = "TvGridRV";

  public TvGridRV(Context context) {
    this(context, null);
  }

  public TvGridRV(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TvGridRV(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  // todo 一些优化
  private void init() {

  }

  @Override
  public void setLayoutManager(LayoutManager layout) {
    if (!(layout instanceof GridLayoutManager)) {
      throw new UnsupportedOperationException(
          "TvGridRV only supports GridLayoutManager, it will not work well with other manager!");
    }
    super.setLayoutManager(layout);
  }

  private boolean needGetDownView;
  private boolean needGetUpView;
  private boolean needGetLeftView;
  private boolean needGetRightView;

  private void resetValue() {
    needGetDownView = false;
    needGetUpView = false;
    needGetLeftView = false;
    needGetRightView = false;
  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {

    if (event.getAction() == KeyEvent.ACTION_DOWN) {
      resetValue();
    } else {
      return super.dispatchKeyEvent(event);
    }

    if (this.getChildAt(0) == null) {
      return super.dispatchKeyEvent(event);
    }

    LayoutParams layoutParams = (LayoutParams) this.getChildAt(0).getLayoutParams();
    int offsetY =
        this.getChildAt(0).getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    int offsetX =
        this.getChildAt(0).getWidth() + layoutParams.leftMargin + layoutParams.rightMargin;

    LayoutManager layoutManager = getLayoutManager();
    if (layoutManager instanceof GridLayoutManager) {

      GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

      View focusView = this.getFocusedChild();

      if (focusView != null && findContainingItemView(focusView) != null) {

        // the postion of current focusview
        int curPos = findContainingViewHolder(focusView).getLayoutPosition();

        // last one
        if ((curPos == layoutManager.getItemCount() - 1)
            && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
          new ShakeAnimHelper.Builder().build()
              .performShakeAnim(gridLayoutManager.findViewByPosition(curPos),
                  ShakeAnimHelper.SHAKE_ORIENTATION_HORIZONTAL);
          return true;
        }

        // first one
        if ((curPos == 0) && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT
            || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP)) {
          return super.dispatchKeyEvent(event);
        }

        switch (event.getKeyCode()) {
          case KeyEvent.KEYCODE_DPAD_RIGHT:
            if ((curPos + 1) % gridLayoutManager.getSpanCount() == 0) {
              View next = gridLayoutManager.findViewByPosition(curPos + 1);
              if (next != null) {
                next.requestFocusFromTouch();
                next.requestFocus();
              } else {
                needGetRightView = true;
                this.smoothScrollBy(0, offsetY);
              }
              return true;
            }
            break;
          case KeyEvent.KEYCODE_DPAD_LEFT:
            if (curPos % gridLayoutManager.getSpanCount() == 0) {
              View previous = gridLayoutManager.findViewByPosition(curPos - 1);
              if (previous != null) {
                previous.requestFocusFromTouch();
                previous.requestFocus();
              } else {
                needGetLeftView = true;
                this.smoothScrollBy(0, -offsetY);
              }
              return true;
            }
            break;
          case KeyEvent.KEYCODE_DPAD_DOWN:
            // 如果在最后一行且完全可见，那么上下shake
            if (gridLayoutManager.findLastVisibleItemPosition() / 3
                == gridLayoutManager.getItemCount() / 3
                && curPos / 3 == gridLayoutManager.getItemCount() / 3) {
              new ShakeAnimHelper.Builder().build()
                  .performShakeAnim(gridLayoutManager.findViewByPosition(curPos),
                      ShakeAnimHelper.SHAKE_ORIENTATION_VERTICAL);
              return true;
            }
            View downView =
                FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_DOWN);
            if (downView != null) {
              downView.requestFocusFromTouch();
              downView.requestFocus();
              return true;
            } else {
              needGetDownView = true;
              this.smoothScrollBy(0, offsetY);
              return true;
            }
          case KeyEvent.KEYCODE_DPAD_UP:
            View upView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_UP);
            if (upView != null) {
              upView.requestFocusFromTouch();
              upView.requestFocus();
              return true;
            } else {
              needGetUpView = true;
              this.smoothScrollBy(0, -offsetY);
              return true;
            }
          case KeyEvent.KEYCODE_BACK:
            if (getLayoutManager().getItemCount() > 0) {
              smoothScrollToPosition(0);
              focusView.clearFocus();
            }

            return true;
        }
      }
    } else if (layoutManager instanceof LinearLayoutManager) {
      // do nothing for now
    }
    return super.dispatchKeyEvent(event);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public boolean onKeyLongPress(int keyCode, KeyEvent event) {
    return super.onKeyLongPress(keyCode, event);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    return super.onKeyUp(keyCode, event);
  }

  @Override
  public void onScrolled(int dx, int dy) {
    super.onScrolled(dx, dy);

    final View focusView = getFocusedChild();
    if (focusView != null && findContainingViewHolder(focusView) != null) {
      int curPos = findContainingViewHolder(focusView).getLayoutPosition();
      if (getLayoutManager() instanceof GridLayoutManager) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) getLayoutManager();
        if (needGetRightView) {
          View nextView = gridLayoutManager.findViewByPosition(curPos + 1);
          if (nextView != null) {
            needGetRightView = false;
            nextView.requestFocusFromTouch();
            nextView.requestFocus();
          }
        } else if (needGetLeftView) {
          View nextView = gridLayoutManager.findViewByPosition(curPos - 1);
          if (nextView != null) {
            needGetLeftView = false;
            nextView.requestFocusFromTouch();
            nextView.requestFocus();
          }
        } else if (needGetDownView) {
          View downView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_DOWN);
          if (downView != null) {
            needGetDownView = false;
            downView.requestFocusFromTouch();
            downView.requestFocus();
          }
        } else if (needGetUpView) {
          View upView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_UP);
          if (upView != null) {
            needGetUpView = false;
            upView.requestFocusFromTouch();
            upView.requestFocus();
          }
        }
      } else if (getLayoutManager() instanceof LinearLayoutManager) {
        // do nothing for now
      }
    }
  }
}
