package com.corey.basic.tv.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.corey.basic.R;

/**
 * Created by caosanyang on 2018/7/23.
 */
public class TitleBar extends RelativeLayout {

  public TitleBar(Context context) {
    this(context, null);
  }

  public TitleBar(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  private TextView tvTitle;

  private NavigationTab navigationTab;

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.layout_titlebar, this, true);
    tvTitle = findViewById(R.id.tv_title);
    navigationTab = findViewById(R.id.navitab);
    navigationTab.setFocusable(true);
    navigationTab.requestFocus();
  }

  public void setTitle(String title) {
    if (TextUtils.isEmpty(title) || tvTitle == null) return;
    tvTitle.setText(title);
  }

  public void setUpViewPager(TvViewPager viewPager) {
    navigationTab.setupWithViewPager(viewPager);
  }

  public void setTabs(String[] tabs) {
    if (navigationTab == null) return;
    navigationTab.setTabs(tabs);
  }

  public void setOnTabChangeListener(NavigationTab.NavigationListener navigationListener) {
    navigationTab.setNavigationListener(navigationListener);
  }

  /**
   * use {@link ViewStub} to lazy load TabCursorView
   */
  public void enableCursorView() {
    ViewStub stubTabCursor = findViewById(R.id.stub_tab_cursor);
    View tabCursorView = stubTabCursor.inflate();
    navigationTab.setTabCursorView((TabCursorView) tabCursorView);
  }

  public NavigationTab getNavigationTab() {
    return navigationTab;
  }

  public TextView getTvTitle() {
    return tvTitle;
  }
}
