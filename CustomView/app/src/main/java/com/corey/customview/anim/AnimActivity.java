package com.corey.customview.anim;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.corey.customview.R;

public class AnimActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 使用xml中的anim
        Button button = new Button(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.normal);
        button.startAnimation(animation);

        // 代码中设置anim
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(300);
        button.startAnimation(animation);

        // 为anim添加监听器
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        button.setBackgroundResource(R.drawable.frame_anim);

        AnimationDrawable animationDrawable = (AnimationDrawable) button.getBackground();
        animationDrawable.start();

        ObjectAnimator.ofFloat(button,"alpha",0,1)
                .setDuration(100)
                .start();

        button.animate().alpha(0).start();


    }
}
