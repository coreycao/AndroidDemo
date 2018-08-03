package com.corey.basic.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.corey.basic.R;
import com.corey.basic.base.BaseActivity;
import com.corey.basic.tv.widget.TitleBar;
import com.corey.basic.tv.widget.TvViewPager;
import com.corey.basic.tv.widget.ViewPagerAdapter;

public class TvTabActivity extends BaseActivity {

  TvViewPager viewPager;

  TitleBar titleBar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tv_tab);
    titleBar = findViewById(R.id.title_bar);
    viewPager = findViewById(R.id.tv_viewpager);
    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
    adapter.addFragment(ContentFragment.newInstance("iot"), "iot");
    adapter.addFragment(ContentFragment.newInstance("app"), "app");
    adapter.addFragment(ContentFragment.newInstance("tensorflow"), "tensorflow");
    adapter.addFragment(ContentFragment.newInstance("ar&vr"), "ar&vr");
    viewPager.setAdapter(adapter);
    titleBar.setUpViewPager(viewPager);
    titleBar.setTitle(getString(R.string.app_name));
    titleBar.enableCursorView();
  }
}
