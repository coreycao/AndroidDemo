package com.ucar.sycao.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ucar.sycao.R;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by sycao on 2017/8/9.
 * map的基本使用
 */

public class RxActivity5 extends AppCompatActivity {

    TextView tvNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx5);
        tvNumber = (TextView) findViewById(R.id.tv_rx5_number);
        createSingle();
    }

    private void createSingle(){
        Single.just(4)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        return String.valueOf(integer*2);
                    }
                })
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String string) {
                        tvNumber.setText(string);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
}
