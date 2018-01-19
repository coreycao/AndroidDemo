package com.corey.basic.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.corey.basic.R;

/**
 * Created by sycao on 2017/8/14.
 */

public class MainActivity extends AppCompatActivity {

    String[] strings = {"java", "C", "C++", "js", "basic", "oc", "android", "iOS", "java", "C", "C++", "js", "basic", "oc", "android", "iOS"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_main_show_alert).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlert();
                    }
                }
        );
    }

    private void showAlert() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setAdapter(new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_single_choice
                        , strings
                ) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        View itemView = super.getView(position, convertView, parent);
                        if (position == 0) {
                            ((TextView) itemView).setTextColor(getResources().getColor(R.color.colorAccent));
                        }

                        return itemView;
                    }
                }, null)
                .create();
        dialog.show();
    }
}
