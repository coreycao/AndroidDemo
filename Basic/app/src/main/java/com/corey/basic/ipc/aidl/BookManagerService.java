package com.corey.basic.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by sycao on 2018/5/21.
 */

public class BookManagerService extends Service {

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }
    };

    CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    private static final String TAG = BookManagerService.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate");
        super.onCreate();
        bookList.add(new Book(1, "java"));
        bookList.add(new Book(2, "android"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mBinder;
    }
}
