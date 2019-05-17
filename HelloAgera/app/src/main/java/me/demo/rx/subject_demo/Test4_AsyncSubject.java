package me.demo.rx.subject_demo;

import android.annotation.SuppressLint;

import io.reactivex.subjects.AsyncSubject;

/**
 * @author caosanyang
 * @date 2019/4/3
 */
public class Test4_AsyncSubject {

    public static void main(String[] args) {
        new Test4_AsyncSubject().test();
    }

    @SuppressLint("CheckResult")
    private void test(){

        // 使用工厂方法创建 Subject 实例
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();

        // 订阅者 A: 发起订阅
        asyncSubject.subscribe(i -> {
            System.out.println("A:" + i);
        }, e -> {
            System.out.println("A:" + e.getMessage());
        }, () -> {
            System.out.println("A: onComplete");
        });

        asyncSubject.onNext(0);
        asyncSubject.onNext(1);
        asyncSubject.onNext(2);

        // 只有 asyncSubject 调用了 onComplete 终止了事件流，A 和 B 才会收到事件，且只会收到最新的一个事件
        // 若事件流没有终止，则 A 和 B 不会收到任何事件
        //asyncSubject.onComplete();

        // 若 asyncSubject 调用了 onError 终止了事件流，则 A 和 B 会收到 onError 通知，缓存的事件将会被忽略，不会发送给订阅者
        // asyncSubject.onError(new Exception("onError"));

        // 订阅者 B: 发起订阅
        asyncSubject.subscribe(i -> {
            System.out.println("B:" + i);
        }, e -> {
            System.out.println("B:" + e.getMessage());
        }, () -> {
            System.out.println("B: onComplete");
        });

        asyncSubject.onNext(3);

        // 订阅者 C: 发起订阅
        asyncSubject.subscribe(i -> {
            System.out.println("C:" + i);
        }, e -> {
            System.out.println("C:" + e.getMessage());
        }, () -> {
            System.out.println("C: onComplete");
        });
    }
}
