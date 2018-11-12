package com.corey.customview.pager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author caosanyang
 * @date 2018/11/11
 */
public class PagerDataModel {

    private PagerView view;

    private AtomicInteger pageNum;

    private int pageSize = 10;

    private int total = 40;

    Handler mHandler = new Handler(Looper.getMainLooper());

    public PagerDataModel(PagerView view) {
        this.view = view;
        pageNum = new AtomicInteger(0);
    }

    public void load() {
        Log.d("PagerDataModel", "load#" + pageNum.get());
        if (view.getTotal() >= total) {
            Log.d("PagerDataModel", "load#done#" + total);
            return;
        }
        view.showLoading();
        getTicketData(pageNum.get(), new Callback() {
            @Override
            public void onSuccess(List<MockBean> data) {
                Log.d("PagerDataModel", "load#onsuccess");
                view.addListData(data);
                view.hideLoading();
                if ((data.size() + pageNum.get() * pageSize) >= total) {
                    view.loadedAll();
                    Log.d("PagerDataModel", "load#onsuccess#done");
                } else {
                    view.loadingMore();
                    Log.d("PagerDataModel", "load#onsuccess#more");
                }
                pageNum.incrementAndGet();
            }

            @Override
            public void onFailure(Throwable throwable) {
                view.loadError();
                view.hideLoading();
            }
        });
    }


    private void getTicketData(int pageNum, Callback callback) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<MockBean> toReturn = new ArrayList<>();
                for (int i = 0; i < pageSize; i++) {
                    MockBean bean = new MockBean();
                    bean.title = "title:" + (i + pageSize * pageNum);
                    if ((i + pageSize * pageNum) > 3) {
                        bean.group = 1;
                    } else {
                        bean.group = 0;
                    }
                    toReturn.add(bean);
                }
                mHandler.post(() -> {
                    callback.onSuccess(toReturn);
                });


            }
        });
    }

    interface Callback {
        void onSuccess(List<MockBean> data);

        void onFailure(Throwable throwable);
    }

}
