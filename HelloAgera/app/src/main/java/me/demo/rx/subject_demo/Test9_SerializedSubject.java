package me.demo.rx.subject_demo;

import android.annotation.SuppressLint;

import io.reactivex.subjects.PublishSubject;

/**
 * @author caosanyang
 * @date 2019/4/8
 */
public class Test9_SerializedSubject {

    public static void main(String[] args) {
    }

    @SuppressLint("CheckResult")
    private void test(){
        PublishSubject<String> publishSubject = PublishSubject.create();
        publishSubject.toSerialized().onNext("hello SerializedSubject");
    }
}
