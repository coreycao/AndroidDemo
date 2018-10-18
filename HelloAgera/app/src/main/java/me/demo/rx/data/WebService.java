package me.demo.rx.data;

import io.reactivex.Observable;
import me.demo.rx.model.Article;
import me.demo.rx.model.Banner;
import me.demo.rx.model.LoginInfo;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author caosanyang
 * @date 2018/9/29
 */
public interface WebService {

  @GET("article/list/{page}/json") Observable<Article> getArticle(@Path("page") int page);

  @GET("lg/collect/list/0/json") Observable<Article> listCollection
      (@Header("Cookie") String username, @Header("Cookie") String password);

  @FormUrlEncoded
  @POST("user/login") Observable<LoginInfo> login(@Field("username") String username,
      @Field("password") String password);

  @GET("banner/json") Observable<Banner> getBanner();
}
