package com.ucar.sycao.rx;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sycao on 2017/8/11.
 */

public interface GankService {

    // http://gank.io/api/random/data/分类/个数
    @GET("random/data/{category}/{number}")
    Call<String> listRandom(@Path("category") String category, @Path("number") String number);
}
