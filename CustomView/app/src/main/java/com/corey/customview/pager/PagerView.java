package com.corey.customview.pager;

import java.util.List;

/**
 * @author caosanyang
 * @date 2018/11/11
 */
public interface PagerView {

    void addListData(List<MockBean> data);

    void loadedAll();

    void loadingMore();

    void loadError();

    void showLoading();

    void hideLoading();

    void showEmpty();

    int getTotal();

}
