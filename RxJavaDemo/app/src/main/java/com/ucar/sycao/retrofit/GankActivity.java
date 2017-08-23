package com.ucar.sycao.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ucar.sycao.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by sycao on 2017/8/11.
 * 使用gank.io的接口展示数据
 * 未使用Retrofit的converter或者其他json格式化工具
 */

public class GankActivity extends AppCompatActivity {

    TextView tvGank;

    final String pageSize = "10";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        findViewById(R.id.btn_gank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        tvGank = (TextView) findViewById(R.id.tv_gank);
    }

    private void getData() {

        // BaseUrl 需要以 '/' (slash)结尾
        final String baseUrl = "http://gank.io/api/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build();
        GankService service = retrofit.create(GankService.class);

        Call<ResponseBody> call = service.getResponseData("Android", pageSize);

        // call.execute();      // 同步调用
        // call.enqueue();      // 异步调用
        // call.cancel();       // 取消请求
        // call.isCanceled();   // 是否请求
        // call.isExecuted();   // 是否调用

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    tvGank.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("corey", "call.enqueue failed:" + t.toString());
            }
        });
    }
}
