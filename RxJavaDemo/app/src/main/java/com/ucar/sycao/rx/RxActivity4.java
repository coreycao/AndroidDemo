package com.ucar.sycao.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucar.sycao.R;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by sycao on 2017/8/9.
 */

public class RxActivity4 extends AppCompatActivity {

    Button btnAdd;
    TextView tvCounter;
    PublishSubject<Integer> publishSubject;

    int count = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx4);
        btnAdd = (Button) findViewById(R.id.btn_rx4_add);
        tvCounter = (TextView) findViewById(R.id.tv_rx4_counter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishSubject.onNext(count);
                count++;
            }
        });
        createCounterEmitter();
    }

    private void createCounterEmitter(){
        publishSubject = PublishSubject.create();
        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                tvCounter.setText(String.valueOf(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
