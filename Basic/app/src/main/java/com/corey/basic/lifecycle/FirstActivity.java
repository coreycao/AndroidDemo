package com.corey.basic.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.corey.basic.base.BaseActivity;

/**
 * Created by sycao on 2018/5/17.
 */

public class FirstActivity extends BaseActivity {

  private final static String TAG = FirstActivity.class.getSimpleName();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    LinearLayout ll = new LinearLayout(this);
    ll.setOrientation(LinearLayout.VERTICAL);
    Button btnGo = new Button(this);
    btnGo.setText("go to second");
    btnGo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    btnGo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(FirstActivity.this, SecondActivity.class));
      }
    });

    ll.addView(btnGo);

    setContentView(ll);
  }
}
