package com.ucar.sycao.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ucar.sycao.R;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sycao on 2017/8/4.
 */

public class RxActivity2 extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SimpleStringAdapter simpleStringAdapter;
    private RestClient restClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx2);
        restClient = new RestClient(this);
        progressBar = (ProgressBar) findViewById(R.id.pb_loader);
        recyclerView = (RecyclerView) findViewById(R.id.rv_rx2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleStringAdapter = new SimpleStringAdapter(this);
        recyclerView.setAdapter(simpleStringAdapter);
        createObservable();
    }

    private void createObservable() {
        Observable<List<String>> tvShowObservable = Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() {
                return restClient.getFavoriteTvShows();
            }
        });

        // https://github.com/ReactiveX/RxJava/wiki/What%27s-different-in-2.0#subscription
        tvShowObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<List<String>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull List<String> strings) {
                                displayTvShows(strings);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

    }

    private void displayTvShows(List<String> tvShows) {
        simpleStringAdapter.setStrings(tvShows);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
