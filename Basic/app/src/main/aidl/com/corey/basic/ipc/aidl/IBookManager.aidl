// IBookManager.aidl
package com.corey.basic.ipc.aidl;

// Declare any non-default types here with import statements
import com.corey.basic.ipc.aidl.Book;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    List<Book> getBookList();

    void addBook(in Book book);
}
