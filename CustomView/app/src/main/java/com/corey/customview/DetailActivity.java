package com.corey.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.corey.customview.layout.StretchedLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        setContentView(R.layout.layout_test2);

        StretchedLayout layout = findViewById(R.id.layout_stretched);
        List<String> data = new ArrayList<>(Arrays.asList("hello", "android", "java", "iOS"));
        MyDataAdapter myDataAdapter = new MyDataAdapter(data);
        layout.setAdapter(myDataAdapter);

        layout.setOnItemClickListener(new StretchedLayout.OnItemClickListener() {
            @Override
            public void onItemClick(StretchedLayout parent, View view, int position, long id) {
                Toast.makeText(DetailActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                myDataAdapter.update(Arrays.asList("ha","haha"));
            }
        });



    }
}
