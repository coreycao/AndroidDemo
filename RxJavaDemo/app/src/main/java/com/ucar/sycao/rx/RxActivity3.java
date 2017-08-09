package com.ucar.sycao.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ucar.sycao.R;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sycao on 2017/8/9.
 */

public class RxActivity3 extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tvErrorMessage;
    private RecyclerView recyclerView;
    private SimpleStringAdapter simpleStringAdapter;
    private RestClient restClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx3);
        restClient = new RestClient(this);
        progressBar = (ProgressBar) findViewById(R.id.pb_loader);
        tvErrorMessage = (TextView) findViewById(R.id.tv_rx3_error);
        recyclerView = (RecyclerView) findViewById(R.id.rv_rx3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleStringAdapter = new SimpleStringAdapter(this);
        recyclerView.setAdapter(simpleStringAdapter);
        createSingle();
    }

    private void createSingle() {
        Single<List<String>> tvShowSingle = Single.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return restClient.getFavoriteTvShowsWithException();
            }
        });

        // https://github.com/ReactiveX/RxJava/wiki/What%27s-different-in-2.0#single
        tvShowSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<String> strings) {
                        displayTvShows(strings);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        displayErrorMessage();
                    }
                });
    }

    private void displayTvShows(List<String> tvShows) {
        simpleStringAdapter.setStrings(tvShows);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void displayErrorMessage() {
        progressBar.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.VISIBLE);
    }
}
