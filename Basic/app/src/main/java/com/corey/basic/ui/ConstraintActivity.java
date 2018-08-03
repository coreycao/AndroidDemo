package com.corey.basic.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.corey.basic.R;

/**
 * Created by sycao on 2017/8/16.
 * 试用 ConstraintLayout 布局
 */

public class ConstraintActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_constraint);
  }
}
