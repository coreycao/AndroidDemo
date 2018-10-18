package me.demo.rx.agera;

import android.support.annotation.NonNull;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import me.demo.rx.model.Article;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author caosanyang
 * @date 2018/9/27
 */
public class MainSupplier implements Supplier<Result<List<String>>> {

  private static final String TAG = "MainSupplier";

  @NonNull @Override public Result<List<String>> get() {
    Article article = getArticleList();
    if (article == null) {
      return Result.failure();
    } else {
      List<String> titles = new ArrayList<>();
      for (Article.DataBean.DatasBean bean : article.getData().getDatas()) {
        titles.add(bean.getTitle());
      }
      //return Result.success(titles);
      return Result.failure();
    }
  }

  private Article getArticleList() {
    String url = "http://www.wanandroid.com/article/list/1/json";
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    Request request = new Request.Builder().url(url).build();
    try {
      okhttp3.Response response = client.newCall(request).execute();
      Gson gson = new Gson();
      Article article = gson.fromJson(response.body().string(), Article.class);
      return article;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
