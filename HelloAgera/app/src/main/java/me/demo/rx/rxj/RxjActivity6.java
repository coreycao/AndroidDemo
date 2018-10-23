package me.demo.rx.rxj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import corey.me.helloagera.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import me.demo.rx.data.RetrofitUtil;
import me.demo.rx.data.WebService;
import me.demo.rx.model.Article;

/**
 * @author caosanyang
 * @date 2018/9/29
 * 使用 Scheduler 方便地实现线程切换, 使用 RxLifeCycle 控制事件流的生命周期
 * 使用了 compose 操作符，引入了较多方法数量
 */
public class RxjActivity6 extends AppCompatActivity {

  private final static String TAG = RxjActivity6.class.getSimpleName();

  ListView listView;

  ListAdapter listAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);
    listView = findViewById(R.id.lv_main);
    fetchData();
  }

  /**
   *
   */
  private void fetchData() {

    final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);

    service.getArticle(1)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(AndroidLifecycle.createLifecycleProvider(this).bindToLifecycle())
        .map(this::mapping2TitleList)
        .doOnDispose(()->Log.d(TAG,"doOnDisposed..."))
        .doOnNext(this::showList)
        .doOnError(throwable -> Log.e(TAG,"doOnError"))
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
    listAdapter = new ArrayAdapter<String>(RxjActivity6.this,
        android.R.layout.simple_list_item_1, strings);
    listView.setAdapter(listAdapter);
  }

  /**
   * RxLifecycle 能够保证在合适的生命周期取消订阅
   */
  @Override protected void onDestroy() {
    super.onDestroy();
  }
}
