package com.ucar.sycao.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ucar.sycao.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by sycao on 2017/8/15.
 */

public class RxActivity7 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx7);

        textView = (TextView) findViewById(R.id.tv_rx7);

//        this.createBasicObservable();
        this.createCosumerObserable();
    }


    /**
     * 使用最基本的create方法创建被观察者
     * 在subscribe定义事件序列
     */
    private void createBasicObservable() {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("hello");
                e.onNext("world");
                e.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String o) {
                Log.d("onNext", o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 观察者不完整定义的回调
     */
    private void createCosumerObserable() {
        Observable.just("hello")
                .subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(@NonNull String s) throws Exception {
                                Log.d("Consumer", "onNext" + s);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Log.d("Consumer", "onError", throwable);

                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.d("Consumer onComplete", "onComplete");

                            }
                        },
                        new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                Log.d("Consumer onSubscribe", "onSubscribe");

                            }
                        });
    }


}
