package com.corey.customview.chart;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caosanyang
 * @date 2018/10/31
 */
public class DataProvider {

    // boxOffices
    // 票房列表，[{  "date":"2018-10-01",  "value":1234 }...]
    // 票房数据的处理与展示
    // 日期数据的处理与展示:
    // 超过30条 则取最新的30条
    // 不足30条 则正常显示
    // 正常显示的逻辑是：先判断是否有跨月份的情形，若有，则要正确地在划分月份之处显示月份；若没有跨月份，则顺序显示即可
    public static List<Pair<String,Float>> boxHistory(){
        List<Pair<String,Float>> result = new ArrayList<>();

        result.add(new Pair<>("2018-09-25",100f));
        result.add(new Pair<>("2018-09-26",100f));
        result.add(new Pair<>("2018-09-27",100f));
        result.add(new Pair<>("2018-09-28",100f));
        result.add(new Pair<>("2018-09-29",100f));
        result.add(new Pair<>("2018-09-30",100f));
        result.add(new Pair<>("2018-10-01",100f));
        result.add(new Pair<>("2018-10-02",100f));
        result.add(new Pair<>("2018-10-03",100f));
        result.add(new Pair<>("2018-10-04",100f));
        result.add(new Pair<>("2018-10-05",100f));
        result.add(new Pair<>("2018-10-06",100f));

        return result;
    }


}
