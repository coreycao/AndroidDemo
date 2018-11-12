package com.corey.customview.pager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.corey.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caosanyang
 * @date 2018/11/11
 */
public class PagerActivity extends AppCompatActivity implements PagerView {

    PagerRV recyclerView;

    PagerAdapter pagerAdapter;

    PagerDataModel pagerDataModel;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pager);

        progressDialog = new ProgressDialog(this);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        pagerAdapter = new PagerAdapter();

        recyclerView.setAdapter(pagerAdapter);

        pagerDataModel = new PagerDataModel(this);

        recyclerView.setOnLoadMoreListener(new PagerRV.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pagerDataModel.load();
            }
        });

        pagerDataModel.load();

    }

    @Override
    public void addListData(List<MockBean> data) {
        if (data == null || data.isEmpty()) return;
        List<PagerAdapter.ItemData> dataList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            MockBean mockBean = data.get(i);
            PagerAdapter.ListData listData = new PagerAdapter.ListData();
            listData.desc = mockBean.title;
            listData.secondId = mockBean.group;
            listData.position = i;
            dataList.add(listData);
        }

        PagerAdapter.ListData item = (PagerAdapter.ListData) (dataList.get(0));
        int preSecondId = item.secondId;
        int target = -1;
        for (int i = 0; i < dataList.size(); i++) {
            PagerAdapter.ListData tmp = (PagerAdapter.ListData) (dataList.get(i));
            if (tmp.secondId != preSecondId) {
                target = i;
                break;
            }
        }
        if (target >= 0) {
            PagerAdapter.SectionHeaderData sectionHeaderData = new PagerAdapter.SectionHeaderData();
            sectionHeaderData.position = 0;
            sectionHeaderData.sectionName = "second";
            dataList.add(target, sectionHeaderData);
        }
        pagerAdapter.addData(dataList);
    }

    @Override
    public void loadedAll() {
        pagerAdapter.updateFooterStatus(PagerAdapter.FOOTER_STATUS_SUCCESS);
    }

    @Override
    public void loadingMore() {
        pagerAdapter.updateFooterStatus(PagerAdapter.FOOTER_STATUS_LOADING);
    }

    @Override
    public void loadError() {
        pagerAdapter.updateFooterStatus(PagerAdapter.FOOTER_STATUS_FAILURE);
    }

    @Override
    public void showLoading() {
//        progressDialog.show();
    }

    @Override
    public void hideLoading() {
//        progressDialog.hide();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public int getTotal() {
        return pagerAdapter.getDataSize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null)
            progressDialog.dismiss();
    }
}
