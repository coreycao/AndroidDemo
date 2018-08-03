package com.corey.basic.ipc.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by sycao on 2018/5/18.
 */

public class ProviderActivity extends AppCompatActivity {

  private static final String TAG = "ProviderActivity";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Button btnGet = new Button(this);
    btnGet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    btnGet.setText("get");
    btnGet.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        qureyByContentPrivider();
      }
    });
    setContentView(btnGet);
  }

  private void qureyByContentPrivider() {
    Uri bookUri = Uri.parse("content://com.corey.basic.bookprovider/book");
    ContentValues contentValues = new ContentValues();
    contentValues.put("_id", 6);
    contentValues.put("name", "weex");
    getContentResolver().insert(bookUri, contentValues);
    Cursor bookCursor =
        getContentResolver().query(bookUri, new String[] { "_id", "name" }, null, null, null);
    while (bookCursor.moveToNext()) {
      Log.d(TAG, "book:" + bookCursor.getInt(0) + "|" + bookCursor.getString(1));
    }
    bookCursor.close();

    Uri userUri = Uri.parse("content://com.corey.basic.bookprovider/user");
    Cursor userCursor =
        getContentResolver().query(userUri, new String[] { "_id", "name", "sex" }, null, null,
            null);
    while (userCursor.moveToNext()) {
      Log.d(TAG,
          "user:" + userCursor.getInt(0) + "|" + userCursor.getString(1) + "|" + userCursor.getInt(
              2));
    }
    userCursor.close();
  }
}
