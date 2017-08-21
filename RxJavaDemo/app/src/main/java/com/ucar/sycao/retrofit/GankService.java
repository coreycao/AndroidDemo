package com.ucar.sycao.retrofit;

import com.ucar.sycao.retrofit.model.GankRandom;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sycao on 2017/8/11.
 * retrofit 接口配置
 */

public interface GankService {

    // http://gank.io/api/random/data/分类/个数
    @GET("random/data/{category}/{number}")
    Call<ResponseBody> getResponseData(@Path("category") String category, @Path("number") String number);

    @GET("random/data/{category}/{number}")
    Call<GankRandom> getEntityData(@Path("category") String category, @Path("number") String number);

    @GET("random/data/{category}/{number}")
    Observable<GankRandom> getObservableData(@Path("category") String category, @Path("number") String number);
}
