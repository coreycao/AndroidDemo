package com.ucar.sycao.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ucar.sycao.R;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by sycao on 2017/8/3.
 */

public class RxActicity1 extends AppCompatActivity {

    private static final String TAG = "RxActivity1";

    RecyclerView recyclerView;
    SimpleStringAdapter simpleStringAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx1);
        recyclerView = (RecyclerView) findViewById(R.id.rv_rx1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleStringAdapter = new SimpleStringAdapter(this);
        recyclerView.setAdapter(simpleStringAdapter);
        createObservable();
    }

    private void createObservable() {
        Observable<List<String>> listObservable = Observable.just(getColorList());

        listObservable.subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<String> strings) {
                simpleStringAdapter.setStrings(strings);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private static List<String> getColorList() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("blue");
        colors.add("green");
        colors.add("red");
        colors.add("chartreuse");
        colors.add("Van Dyke Brown");
        return colors;
    }
}
