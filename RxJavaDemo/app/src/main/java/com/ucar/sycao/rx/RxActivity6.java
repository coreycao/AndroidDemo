package com.ucar.sycao.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ucar.sycao.R;


import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by sycao on 2017/8/10.
 * 演示map的使用
 */

public class RxActivity6 extends AppCompatActivity {

    RestClient restClient;
    EditText etSearch;
    TextView tvNoData;
    RecyclerView recyclerView;
    SimpleStringAdapter simpleStringAdapter;

    PublishSubject<String> publishSubject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx6);
        restClient = new RestClient(this);
        etSearch = (EditText) findViewById(R.id.search_input);
        tvNoData = (TextView) findViewById(R.id.tv_rx6_no_result);
        recyclerView = (RecyclerView) findViewById(R.id.rv_rx6);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleStringAdapter = new SimpleStringAdapter(this);
        recyclerView.setAdapter(simpleStringAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                publishSubject.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        createObservables();
    }

    private void createObservables() {
        publishSubject = PublishSubject.create();
        publishSubject.debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(@NonNull String s) throws Exception {
                        return restClient.searchForCity(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<String> cities) {
                        if (cities.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            tvNoData.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            tvNoData.setVisibility(View.GONE);
                            simpleStringAdapter.setStrings(cities);
                        }
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
