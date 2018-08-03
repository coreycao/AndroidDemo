package com.corey.basic.tv.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.corey.basic.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * navigation tab for tv
 */
public class NavigationTab extends LinearLayout {

  /**
   * normal text color of tab title
   */
  private int textColor;

  /**
   * default text size of tab title
   */
  private float textSize;

  /**
   * the scale ratio of each item when focused
   */
  private float scaleRatio;

  /**
   * the margin between each tab item
   */
  private int spaceMargin;

  /**
   * compute and store each item's left distance from parent
   */
  private SparseIntArray leftDistances = new SparseIntArray();

  public NavigationTab(Context context) {
    this(context, null);
  }

  public NavigationTab(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public NavigationTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationTab);
    textColor = typedArray.getColor(R.styleable.NavigationTab_navi_text_color, Color.BLACK);
    //textSize = typedArray.getFloat(R.styleable.NavigationTab_navi_text_size, 20.0f);
    textSize = typedArray.getDimension(R.styleable.NavigationTab_navi_text_size, 20.0f);
    scaleRatio = typedArray.getFloat(R.styleable.NavigationTab_navi_text_scale_ratio, 1.05f);
    spaceMargin = typedArray.getInteger(R.styleable.NavigationTab_navi_text_space_margin, 10);
    typedArray.recycle();
  }

  /**
   * tab titles
   */
  private List<String> tabs;

  /**
   * current position
   */
  private int currentPosition = -1;

  /**
   * focused state code
   */
  private static final int STATE_FOCUSED = 0X01;

  /**
   * unfocused state code
   */
  private static final int STATE_UNFOCUSED = 0X02;

  /**
   * callback listener when tab state change
   */
  private NavigationListener navigationListener;

  /**
   * cursor of tabs
   */
  private TabCursorView tabCursorView;

  /**
   * the viewpager it works with.
   */
  private TvViewPager viewPager;

  /**
   * set tabs
   *
   * @param tabs tab titles
   */
  public void setTabs(List<String> tabs) {
    this.tabs = tabs;
    initViews();
  }

  public void setTabs(String[] tabs) {
    this.tabs = Arrays.asList(tabs);
    initViews();
  }

  /**
   * setup the viewpager
   *
   * @param viewPager should be {@link TvViewPager}
   */
  public void setupWithViewPager(TvViewPager viewPager) {

    if (viewPager == null) {
      throw new NullPointerException("viewpager cannot be null");
    }

    this.viewPager = viewPager;

    // create tabs by using viewpager adapter's titles

    if (viewPager.getAdapter() == null) {
      throw new NullPointerException("cannot get the adapter of viewpager");
    }

    int count = viewPager.getAdapter().getCount();

    List<String> list = new ArrayList<>(count);
    for (int i = 0; i < count; i++) {
      list.add(viewPager.getAdapter().getPageTitle(i).toString());
    }

    this.tabs = list;

    initViews();
  }

  public void setTabCursorView(TabCursorView cursorView) {
    this.tabCursorView = cursorView;
    // tabCursorView.setVisibility(VISIBLE);
  }

  /**
   * init the tabs with TextView
   */
  private void initViews() {
    for (String s : tabs) {
      addView(createTab(s));
    }

    if (tabs != null && tabs.size() > 0) {
      currentPosition = 0;
      changeTabState(currentPosition, STATE_FOCUSED);
    }

    // compute every item's left distance
    for (int i = 0; i < getChildCount(); i++) {
      final TextView child = (TextView) getChildAt(i);
      final int index = i;
      child.getViewTreeObserver()
          .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
              child.getViewTreeObserver().removeGlobalOnLayoutListener(this);
              int leftDistance = child.getWidth() / 2 + child.getLeft() + getLeft();
              leftDistances.put(index, leftDistance);
              if (index == 0 && leftDistance != 0 && tabCursorView != null) {
                tabCursorView.appear(leftDistance, child.getRight() - child.getLeft());
              }
            }
          });
    }
  }

  /**
   * create TextView tab
   *
   * @return new TextView
   */
  private TextView createTab(String title) {
    TextView textView = new TextView(getContext());
    LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    lp.setMargins(spaceMargin, 0, spaceMargin, 0);
    textView.setLayoutParams(lp);
    textView.setText(title);
    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    textView.setTextColor(textColor);
    return textView;
  }

  /**
   * change the state of tab with anim effect
   *
   * @param pos position of the tab
   * @param state state to change
   */
  private void changeTabState(int pos, int state) {
    View child = getChildAt(pos);
    if (child != null) {
      switch (state) {
        case STATE_FOCUSED:
          ViewCompat.animate(child).scaleX(scaleRatio).scaleY(scaleRatio).start();
          child.setSelected(true);
          break;
        case STATE_UNFOCUSED:
          ViewCompat.animate(child).scaleX(1.0f).scaleY(1.0f).start();
          child.setSelected(false);
          break;
      }
    }
  }

  /**
   * handle keydown event
   *
   * @param keyCode keycode
   * @param event keyevent
   * @return super
   */
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_DOWN) {
      switch (keyCode) {
        case KeyEvent.KEYCODE_DPAD_LEFT:
          if (currentPosition == 0 && getChildAt(currentPosition) != null) {
            new ShakeAnimHelper.Builder().build().performShakeAnim(getChildAt(currentPosition),
                ShakeAnimHelper.SHAKE_ORIENTATION_HORIZONTAL);
          } else if (currentPosition > 0) {
            changeTabState(currentPosition, STATE_UNFOCUSED);
            currentPosition--;
            if (viewPager != null) {
              viewPager.setCurrentItem(currentPosition, true);
            }
            changeTabState(currentPosition, STATE_FOCUSED);
            int leftDistance = leftDistances.get(currentPosition);
            if (leftDistance != 0 && tabCursorView != null && getChildAt(currentPosition) != null) {
              View child = getChildAt(currentPosition);
              tabCursorView.move(leftDistance, child.getRight() - child.getLeft());
            }
            if (navigationListener != null) {
              navigationListener.onNavigationChange(currentPosition, keyCode);
            }
          }
          return true;
        case KeyEvent.KEYCODE_DPAD_RIGHT:
          if (currentPosition == getChildCount() - 1 && getChildAt(currentPosition) != null) {
            new ShakeAnimHelper.Builder().build().performShakeAnim(getChildAt(currentPosition),
                ShakeAnimHelper.SHAKE_ORIENTATION_HORIZONTAL);
          } else if (currentPosition < getChildCount() - 1) {
            changeTabState(currentPosition, STATE_UNFOCUSED);
            currentPosition++;
            if (viewPager != null) {
              viewPager.setCurrentItem(currentPosition, true);
            }
            changeTabState(currentPosition, STATE_FOCUSED);
            int leftDistance = leftDistances.get(currentPosition);
            if (leftDistance != 0 && tabCursorView != null && getChildAt(currentPosition) != null) {
              View child = getChildAt(currentPosition);
              tabCursorView.move(leftDistance, child.getRight() - child.getLeft());
            }
            if (navigationListener != null) {
              navigationListener.onNavigationChange(currentPosition, keyCode);
            }
          }
          return true;
        case KeyEvent.KEYCODE_DPAD_UP:
          if (getChildAt(currentPosition) != null) {
            new ShakeAnimHelper.Builder().build().performShakeAnim(getChildAt(currentPosition),
                ShakeAnimHelper.SHAKE_ORIENTATION_VERTICAL);
          }
          return true;
        case KeyEvent.KEYCODE_DPAD_DOWN:
          // todo
      }
    }

    return super.onKeyDown(keyCode, event);
  }

  /**
   * callback when switching tabs
   */
  public interface NavigationListener {
    void onNavigationChange(int pos, int keyCode);
  }

  /**
   * set NavigationListener
   *
   * @param listener NavigationListener callback
   */
  public void setNavigationListener(NavigationListener listener) {
    this.navigationListener = listener;
  }

  @Override
  protected void onFocusChanged(boolean gainFocus, int direction,
      @Nullable Rect previouslyFocusedRect) {
    super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    if (tabCursorView != null) {
      tabCursorView.setVisibility(gainFocus ? View.VISIBLE : View.INVISIBLE);
    }
  }
}
