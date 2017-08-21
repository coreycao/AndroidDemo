package com.ucar.sycao.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ucar.sycao.R;
import com.ucar.sycao.retrofit.model.GankRandom;
import com.ucar.sycao.rx.SimpleStringAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sycao on 2017/8/21.
 * Retrofit配合rxJava进行网络请求处理
 */

public class GankActivity3 extends AppCompatActivity {

    TextView tvGank;
    RecyclerView rvGank;
    SimpleStringAdapter simpleStringAdapter;
    Disposable disposable;
    ProgressDialog progressDialog;

    final String pageSize = "10";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank3);
        findViewById(R.id.btn_gank3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        tvGank = (TextView) findViewById(R.id.tv_gank3);
        rvGank = (RecyclerView) findViewById(R.id.rv_gank3);
        rvGank.setLayoutManager(new LinearLayoutManager(this));
        simpleStringAdapter = new SimpleStringAdapter(this);
        rvGank.setAdapter(simpleStringAdapter);
        progressDialog = new ProgressDialog(this);
    }

    private void getData() {
        final String baseUrl = "http://gank.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GankService service = retrofit.create(GankService.class);

        disposable = service.getObservableData("Android", pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GankRandom, List<String>>() {

                    @Override
                    public List<String> apply(@NonNull GankRandom gankRandom) throws Exception {
                        List<String> toReturn = new ArrayList<>();
                        for (GankRandom.ResultsBean gankBean : gankRandom.getResults()) {
                            toReturn.add(gankBean.getDesc());
                        }
                        return toReturn;
                    }
                })
                .subscribeWith(
                        new DisposableObserver<List<String>>() {
                            @Override
                            protected void onStart() {
                                progressDialog.show();
                            }

                            @Override
                            public void onNext(@NonNull List<String> titles) {
                                progressDialog.dismiss();
                                simpleStringAdapter.setStrings(titles);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onComplete() {
                            }
                        }
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }


}
