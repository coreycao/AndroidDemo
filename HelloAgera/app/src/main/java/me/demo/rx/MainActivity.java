package me.demo.rx;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import me.demo.rx.agera.AgeraActivity;
import me.demo.rx.rxj.RxjActivity1;
import me.demo.rx.rxj.RxjActivity2;
import me.demo.rx.rxj.RxjActivity3;
import me.demo.rx.rxj.RxjActivity4;
import me.demo.rx.rxj.RxjActivity5;
import me.demo.rx.rxj.RxjActivity6;

/**
 * @author caosanyang
 * @date 2018/9/25
 */
public class MainActivity extends AppCompatActivity {

  Class[] activities = {
      AgeraActivity.class,
      RxjActivity1.class,
      RxjActivity2.class,
      RxjActivity3.class,
      RxjActivity4.class,
      RxjActivity5.class,
      RxjActivity6.class
  };

  String[] btnTexts = {
      "Google Agera",
      "RxJava Basic",
      "RxJava Zip",
      "RxJava FlatMap",
      "RxJava Interval",
      "RxJava Create",
      "RxJava Lifecycle"
  };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    LinearLayout rootView = new LinearLayout(this);
    rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));
    rootView.setOrientation(LinearLayout.VERTICAL);

    LinearLayout.LayoutParams btnLp =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);

    for (int i = 0; i < activities.length; i++) {
      Button button = new Button(this);
      button.setText(btnTexts[i]);
      button.setLayoutParams(btnLp);
      final Class activity = activities[i];
      button.setOnClickListener(v -> {
        startActivity(new Intent(this, activity));
      });
      rootView.addView(button);
    }

    setContentView(rootView);
  }
}
