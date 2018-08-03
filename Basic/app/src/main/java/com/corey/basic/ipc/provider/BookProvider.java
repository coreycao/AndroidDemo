package com.corey.basic.ipc.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by sycao on 2018/5/18.
 */

public class BookProvider extends ContentProvider {

  private static final String TAG = "BookProvider";

  public static final String AUTHORITY = "com.corey.basic.bookprovider";

  public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");

  public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");

  public static final int BOOK_URI_CODE = 0;

  public static final int USER_URI_CODE = 1;

  private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    mUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
    mUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
  }

  private Context mContext;

  private SQLiteDatabase mDB;

  private String getTableName(Uri uri) {
    String tableName = null;
    switch (mUriMatcher.match(uri)) {
      case BOOK_URI_CODE:
        tableName = DBOpenHelper.BOOK_TABLE_NAME;
        break;
      case USER_URI_CODE:
        tableName = DBOpenHelper.USER_TABLE_NAME;
        break;
    }
    return tableName;
  }

  private void initDBData() {
    mDB = new DBOpenHelper(mContext).getWritableDatabase();
    mDB.execSQL("delete from " + DBOpenHelper.BOOK_TABLE_NAME);
    mDB.execSQL("delete from " + DBOpenHelper.USER_TABLE_NAME);
    mDB.execSQL("insert into book values(3,'Android');");
    mDB.execSQL("insert into book values(4,'Thinking in Java');");
    mDB.execSQL("insert into book values(5,'Hello World');");
    mDB.execSQL("insert into user values(1,'user_A',0);");
    mDB.execSQL("insert into user values(2,'user_B',1);");
  }

  @Override
  public boolean onCreate() {
    Log.d(TAG, "current Thread is " + Thread.currentThread().getName());
    mContext = getContext();
    initDBData();
    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    Log.d(TAG, "current thread is " + Thread.currentThread().getName());
    String tableName = getTableName(uri);
    if (tableName == null) throw new IllegalArgumentException("unsuooprted URI:" + uri);

    return mDB.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    Log.d(TAG, "getType");
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    Log.d(TAG, "insert");
    String tableName = getTableName(uri);
    if (tableName == null) throw new IllegalArgumentException("unsuooprted URI:" + uri);
    mDB.insert(tableName, null, values);
    mContext.getContentResolver().notifyChange(uri, null);
    return null;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    Log.d(TAG, "delete");
    String tableName = getTableName(uri);
    if (tableName == null) throw new IllegalArgumentException("unsuooprted URI:" + uri);
    int count = mDB.delete(tableName, selection, selectionArgs);
    if (count > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return count;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    Log.d(TAG, "update");
    String tableName = getTableName(uri);
    if (tableName == null) throw new IllegalArgumentException("unsuooprted URI:" + uri);
    int row = mDB.update(tableName, values, selection, selectionArgs);
    if (row > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return row;
  }
}
