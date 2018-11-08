package com.corey.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author caosanyang
 * @date 2018/11/2
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        List<String> row = Arrays.asList("amazon", "google", "tencent", "alibaba", "facebook", "microsoft");
        List<String> row2 = Arrays.asList("amazon", "google", "tencent", "alibaba", "facebook", "microsoft");
        List<String> row3 = Arrays.asList("amazon", "google", "tencent", "alibaba", "facebook", "microsoft");
        List<List<String>> data = new ArrayList<>();
        data.add(row);
        data.add(row2);
        data.add(row3);
        TableView tableView = new TableView(this);
        tableView.setData(data);
        setContentView(tableView);*/

        setContentView(R.layout.activity_detail);
    }
}
