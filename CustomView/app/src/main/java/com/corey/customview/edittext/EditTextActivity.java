package com.corey.customview.edittext;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.corey.customview.R;

/**
 * Created by sycao on 2017/8/11.
 */

public class EditTextActivity extends AppCompatActivity {

    EditText edCustom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_edittext);
        edCustom = (EditText) findViewById(R.id.et_custom);
        edCustom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = edCustom.getCompoundDrawables()[2];
                if (drawable == null){
                    return false;
                }

                if (event.getAction()!=MotionEvent.ACTION_UP){
                    return false;
                }

                if (event.getX()>(edCustom.getWidth()-edCustom.getPaddingRight()-drawable.getIntrinsicWidth())){
                    edCustom.setText("");
                }
                return false;
            }
        });
    }
}
