package me.demo.rx.rxj;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import corey.me.helloagera.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import me.demo.rx.data.ArticleModel;
import me.demo.rx.model.Article;

/**
 * @author caosanyang
 * @date 2018/9/29
 * 不使用 Retrofit，将传统的回调式网络请求包装为 Observable
 */
public class RxjActivity5 extends AppCompatActivity {

  private final static String TAG = RxjActivity5.class.getSimpleName();

  Disposable disposable;

  ListView listView;

  ListAdapter listAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);
    listView = findViewById(R.id.lv_main);
    fetchData();
  }

  private void fetchData() {

    disposable = new ArticleModel().getArticleList()

        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Function<Article, List<String>>() {
          @Override public List<String> apply(Article article) throws Exception {
            article = (Article) article;
            List<String> titles = new ArrayList<>();
            for (Article.DataBean.DatasBean bean : article.getData().getDatas()) {
              titles.add(bean.getTitle());
            }
            return titles;
          }
        })
        .doOnError(new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            Log.e(TAG, "doOnError...");
          }
        })
        .doOnNext(new Consumer<List<String>>() {
          @Override public void accept(List<String> strings) throws Exception {
            Log.d(TAG, "doOnNext...");
            listAdapter = new ArrayAdapter<String>(RxjActivity5.this,
                android.R.layout.simple_list_item_1, strings);
            listView.setAdapter(listAdapter);
          }
        })
        .doFinally(() -> {
          Log.d(TAG, "sub doFinally...");
        })
        .subscribe(t -> {
          Log.d(TAG, "sub onNext...");
        }, t -> {
          Log.d(TAG, "sub onError...");
        });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }
}
