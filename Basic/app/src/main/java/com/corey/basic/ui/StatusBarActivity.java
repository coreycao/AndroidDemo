package com.corey.basic.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.corey.basic.R;

/**
 * Created by Corey on 2017/8/29.
 * 状态栏的显示与隐藏
 */

public class StatusBarActivity extends AppCompatActivity {

  View decorView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bar);
    decorView = getWindow().getDecorView();
    //        hideActionBar();
    //        hideStatusBar();
    //        opticalStatusBar();
    //        hideNaviBar();
    //        opticalNaviBar();
  }

  // 隐藏 actionBar
  private void hideActionBar() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.hide();
    }
  }

  // 隐藏状态栏
  private void hideStatusBar() {
    int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
    if (decorView != null) {
      decorView.setSystemUiVisibility(option);
    }
  }

  // 透明状态栏
  private void opticalStatusBar() {
    int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    if (Build.VERSION.SDK_INT >= 21 && decorView != null) {
      decorView.setSystemUiVisibility(option);
      getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
  }

  // 隐藏导航栏
  private void hideNaviBar() {
    int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_FULLSCREEN;
    if (decorView != null) {
      decorView.setSystemUiVisibility(option);
    }
  }

  // 透明导航栏
  private void opticalNaviBar() {
    int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    if (Build.VERSION.SDK_INT >= 21 && decorView != null) {
      decorView.setSystemUiVisibility(option);
      getWindow().setNavigationBarColor(Color.TRANSPARENT);
    }
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus && Build.VERSION.SDK_INT >= 19) {
      if (decorView != null) {
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
      }
    }
  }
}
