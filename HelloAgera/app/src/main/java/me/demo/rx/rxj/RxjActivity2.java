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
import me.demo.rx.data.RetrofitUtil;
import me.demo.rx.data.WebService;
import me.demo.rx.model.Article;

/**
 * @author caosanyang
 * @date 2018/9/29
 * 使用 Zip 操作符实现多异步请求合并处理
 */
public class RxjActivity2 extends AppCompatActivity {

  private final static String TAG = RxjActivity2.class.getSimpleName();

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
   * 两个请求同时发出，分别获取第一页和第二页的数据，只有两个请求都成功后才进行下一步的操作
   */
  private void fetchData() {
    final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);

    Observable<List<String>> source1 = service.getArticle(1)
        .map(this::mapping2TitleList);

    Observable<List<String>> source2 = service.getArticle(2)
        .map(this::mapping2TitleList);

    disposable = Observable.zip(source1, source2, this::combine)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::showList, throwable ->
            Log.e(TAG, "doOnError...")
        );
  }

  /**
   * 将两页数据合并
   * @param o
   * @param o2
   * @return
   */
  private List<String> combine(List<String> o, List<String> o2) {
    o.addAll(o2);
    return o;
  }

  /**
   * 将 article 映射成标题列表
   * @param article
   * @return
   */
  private List<String> mapping2TitleList(Article article) {
    List<String> titles = new ArrayList<>();
    for (Article.DataBean.DatasBean bean : article.getData().getDatas()) {
      titles.add(bean.getTitle());
    }
    return titles;
  }

  private void showList(List<String> strings) {
    Log.d(TAG, "doOnNext...");
    listAdapter = new ArrayAdapter<>(RxjActivity2.this,
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
