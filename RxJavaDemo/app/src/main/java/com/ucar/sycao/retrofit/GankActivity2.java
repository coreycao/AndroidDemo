package com.ucar.sycao.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ucar.sycao.R;
import com.ucar.sycao.retrofit.model.GankRandom;
import com.ucar.sycao.rx.SimpleStringAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sycao on 2017/8/21.
 * 使用GsonConverter对获取的json数据进行格式化处理
 */

public class GankActivity2 extends AppCompatActivity {

    TextView tvGank;
    RecyclerView rvGank;
    SimpleStringAdapter simpleStringAdapter;
    Disposable disposable;
    ProgressDialog progressDialog;

    final String pageSize = "10";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank2);
        findViewById(R.id.btn_gank2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        tvGank = (TextView) findViewById(R.id.tv_gank2);
        rvGank = (RecyclerView) findViewById(R.id.rv_gank2);
        rvGank.setLayoutManager(new LinearLayoutManager(this));
        simpleStringAdapter = new SimpleStringAdapter(this);
        rvGank.setAdapter(simpleStringAdapter);
        progressDialog = new ProgressDialog(this);
    }

    private void getData() {
        progressDialog.show();
        final String baseUrl = "http://gank.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GankService service = retrofit.create(GankService.class);
        Call<GankRandom> call = service.getEntityData("Android", pageSize);
        call.enqueue(new Callback<GankRandom>() {
            @Override
            public void onResponse(Call<GankRandom> call, Response<GankRandom> response) {
                List<String> data = new ArrayList<String>();
                for (GankRandom.ResultsBean bean : response.body().getResults()) {
                    data.add(bean.getDesc());
                }
                simpleStringAdapter.setStrings(data);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GankRandom> call, Throwable t) {
                Log.d("corey", "call.enqueue failed:" + t.toString());
                progressDialog.dismiss();
            }
        });
    }
}
