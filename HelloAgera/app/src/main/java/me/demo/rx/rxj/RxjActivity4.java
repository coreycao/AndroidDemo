package me.demo.rx.rxj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import corey.me.helloagera.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.demo.rx.data.RetrofitUtil;
import me.demo.rx.data.WebService;
import me.demo.rx.model.Article;

/**
 * @author caosanyang
 * @date 2018/9/29
 * 使用 interval 实现轮询
 */
public class RxjActivity4 extends AppCompatActivity {

  private final static String TAG = RxjActivity4.class.getSimpleName();

  Disposable disposable;

  ListView listView;

  ListAdapter listAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);
    listView = findViewById(R.id.lv_main);
    fetchData();
  }

  /**
   * 每隔3秒刷新一下当前页面，且页码递增
   */
  private void fetchData() {
    final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);
    disposable = Observable.intervalRange(1, 5, 0, 3, TimeUnit.SECONDS)
        .flatMap(aLong -> service.getArticle(aLong.intValue()))
        .map(this::mapping2TitleList)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(this::showList)
        .doOnError(throwable -> Log.e(TAG, "doOnError.."))
        .subscribe();
  }

  private List<String> mapping2TitleList(Article article) {
    List<String> titles = new ArrayList<>();
    for (Article.DataBean.DatasBean bean : article.getData().getDatas()) {
      titles.add(bean.getTitle());
    }
    return titles;
  }

  private void showList(List<String> strings) {
    Log.d(TAG, "doOnNext...");
    listAdapter = new ArrayAdapter<String>(RxjActivity4.this,
        android.R.layout.simple_list_item_1, strings);
    listView.setAdapter(listAdapter);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }
}
