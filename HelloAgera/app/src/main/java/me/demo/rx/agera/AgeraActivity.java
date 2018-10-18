package me.demo.rx.agera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.agera.Observable;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import corey.me.helloagera.R;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author caosanyang
 * @date 2018/9/29
 */
public class AgeraActivity extends AppCompatActivity implements Updatable {

  private ExecutorService networkExecutor;

  private Repository<Result<List<String>>> repository;

  SwipeRefreshLayout swipeRefreshLayout;

  ListView listView;

  ListAdapter listAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_agera);
    initViews();
    setUpRepository();
  }

  private void initViews() {
    swipeRefreshLayout = findViewById(R.id.srl_main);
    listView = findViewById(R.id.lv_main);
  }

  private void setUpRepository() {
    networkExecutor = Executors.newSingleThreadExecutor();
    repository = Repositories
        .repositoryWithInitialValue(Result.<List<String>>absent())
        .observe()
        .onUpdatesPerLoop()
        .goTo(networkExecutor)
        .getFrom(new MainSupplier())
        .thenGetFrom(new MainSupplier())

        .compile();
  }

  @Override public void update() {
    repository.get()
        .ifSucceededSendTo(value -> {
          listAdapter = new ArrayAdapter<>(this,
              android.R.layout.simple_list_item_1, repository.get().get());
          listView.setAdapter(listAdapter);
        })
        .ifFailedSendTo(t -> {
              Log.e("agera", t.toString());
              Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
            }
        )
    ;
  }

  @Override protected void onResume() {
    super.onResume();
    repository.addUpdatable(this);
  }

  @Override protected void onPause() {
    super.onPause();
    repository.removeUpdatable(this);
  }
}
