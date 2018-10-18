package me.demo.rx.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author caosanyang
 * @date 2018/9/30
 */
public class RetrofitUtil {

  private static Retrofit retrofit;

  private static final String baseUrl = "http://www.wanandroid.com/";

  private RetrofitUtil() {
  }

  public static Retrofit INSTANCE() {
    if (retrofit == null) {

      HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
      httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

      OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
      okHttpClient.addInterceptor(httpLoggingInterceptor);

      retrofit = new Retrofit.Builder()
          .baseUrl(baseUrl)
          .client(okHttpClient.build())
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();
    }
    return retrofit;
  }
}
