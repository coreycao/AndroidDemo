package me.demo.rx.rxj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import corey.me.helloagera.R;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import me.demo.rx.data.RetrofitUtil;
import me.demo.rx.data.WebService;
import me.demo.rx.model.Article;

/**
 * @author caosanyang
 * @date 2018/9/29
 * 使用 FlatMap 实现多异步请求连续调用
 */
public class RxjActivity3 extends AppCompatActivity {

  private final static String TAG = RxjActivity3.class.getSimpleName();

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
   * 先请求第一页数据，待成功后再请求第二页数据，将两页数据合并成新的 Observable 发送给下游
   */
  private void fetchData() {

    final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);

    disposable = service.getArticle(1)
        .map(this::mapping2TitleList)
        .flatMap(strings ->
            service.getArticle(2)
                .map(article -> {
                  strings.addAll(RxjActivity3.this.mapping2TitleList(article));
                  return strings;
                })
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::showList, throwable ->
            Log.e(TAG, "doOnError...")
        );
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
    listAdapter = new ArrayAdapter<>(RxjActivity3.this,
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
