package com.corey.customview.table;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

/**
 * @author caosanyang
 * @date 2018/11/5
 */
public class TableView extends TableLayout {

    public TableView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setStretchAllColumns(true);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
    }

    public void setData(List<List<String>> data) {

        TableRow.LayoutParams lp2 = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (List<String> row : data) {
            TableRow tableRow = new TableRow(getContext());
            for (String s : row) {
                TextView tv1 = new TextView(tableRow.getContext());
                tv1.setText(s);
                tv1.setLayoutParams(lp2);
                tableRow.addView(tv1);
            }
            addView(tableRow);
        }

    }
}
