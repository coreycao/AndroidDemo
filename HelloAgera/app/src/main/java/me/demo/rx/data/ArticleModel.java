package me.demo.rx.data;

import com.google.gson.Gson;
import io.reactivex.Observable;
import java.io.IOException;
import me.demo.rx.model.Article;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author caosanyang
 * @date 2018/10/11
 */
public class ArticleModel {

  interface Callback {

    void onSuccess(Article result);

    void onFailure(Throwable throwable);
  }

  /**
   * 传统的回调式网络请求
   */
  public void getArticleList(Callback callback) {
    String url = "http://www.wanandroid.com/article/list/1/json";
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    Request request = new Request.Builder().url(url).build();
    try {
      okhttp3.Response response = client.newCall(request).execute();
      Gson gson = new Gson();
      Article article = gson.fromJson(response.body().string(), Article.class);
      callback.onSuccess(article);
    } catch (Exception e) {
      e.printStackTrace();
      callback.onFailure(e);
    }
  }

  public void getArticleList2(Callback callback) {
    String url = "http://www.wanandroid.com/article/list/1/json";
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    Request request = new Request.Builder().url(url).build();
    client.newCall(request).enqueue(new okhttp3.Callback() {
      @Override public void onFailure(Call call, IOException e) {
        callback.onFailure(e);
        call.cancel();
      }

      @Override public void onResponse(Call call, Response response) throws IOException {

      }
    });
  }

  /**
   * 返回 Observable 的网络请求
   */
  public Observable<Article> getArticleList() {
    return Observable.create(emitter ->
        getArticleList(new Callback() {
          @Override public void onSuccess(Article result) {
            if (emitter.isDisposed()) return;
            emitter.onNext(result);
            emitter.onComplete();
          }

          @Override public void onFailure(Throwable throwable) {
            if (emitter.isDisposed()) return;
            emitter.onError(throwable);
          }
        })
    );
  }
}
