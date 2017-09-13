package com.corey.demo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sycao on 2017/8/24.
 */

public interface MovieService {
    /**
     * 根据电影ID获取电影详细信息
     *
     * @param id 电影ID
     */
    @GET("movie/subject/{id}")
    Observable<MovieEntity.SubjectsBean> getMovieSubjectById(@Path("id") String id);

}
