package me.demo.rx.rxj;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import corey.me.helloagera.R;
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
 * 单个请求异步处理，使用 Scheduler 方便地实现线程切换
 */
public class RxjActivity1 extends AppCompatActivity {

  private final static String TAG = RxjActivity1.class.getSimpleName();

  Disposable disposable;

  ListView listView;

  ListAdapter listAdapter;

  ProgressDialog pdLoading;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);
    listView = findViewById(R.id.lv_main);
    pdLoading = new ProgressDialog(this);
    fetchData();
  }

  /**
   * doOnSubscribe -> onSubscribe -> doOnNext -> onNext -> doOnComplete -> onComplete
   * doXXX 系列的操作符与Observer的各个回调方法是相对应的
   */
  private void fetchData() {

    final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);

    disposable = service.getArticle(1)
        .subscribeOn(Schedulers.io())
        // map操作将 Article 对象映射成 List<String>, 即文章的标题列表
        .map(article -> {
          List<String> titles = new ArrayList<>();
          for (Article.DataBean.DatasBean bean : article.getData().getDatas()) {
            titles.add(bean.getTitle());
          }
          return titles;
        })
        // 将订阅者任务执行的线程切换到 Android UI 线程
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe((disposableDoOnSub) -> {
          Log.d(TAG, "doOnSubscribe...");
          showLoading();
        })
        .doOnNext(this::showList)
        .doOnError(throwable -> {
              Log.e(TAG, "doOnError...");
              showErrorMessage();
            }
        )
        .doOnDispose(()->Log.d(TAG,"doOnDisposed..."))
        .doOnComplete(() -> Log.d(TAG, "doOnComplete..."))
        .doFinally(this::hideLoading)
        .subscribe(strings -> Log.d(TAG, "onNext..."),
            throwable -> Log.d(TAG, "onError..."),
            () -> Log.d(TAG, "onComplete..."),
            disposableOnSub -> Log.d(TAG, "onSubscribe...")
        );
  }

  private void showList(List<String> strings) {
    Log.d(TAG, "doOnNext...");
    listAdapter = new ArrayAdapter<>(RxjActivity1.this,
        android.R.layout.simple_list_item_1, strings);
    listView.setAdapter(listAdapter);
  }

  private void showLoading() {
    pdLoading.show();
  }

  private void hideLoading() {
    pdLoading.dismiss();
  }

  private void showErrorMessage() {
    Toast.makeText(this, "network error", Toast.LENGTH_SHORT).show();
  }

  /**
   * 在适当的时机结束订阅，避免产生内存泄漏
   */
  @Override protected void onDestroy() {
    super.onDestroy();
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
    if (pdLoading != null) {
      pdLoading.cancel();
    }
  }
}
