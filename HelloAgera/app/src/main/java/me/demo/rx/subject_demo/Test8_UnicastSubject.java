package me.demo.rx.subject_demo;

import android.annotation.SuppressLint;

import io.reactivex.subjects.UnicastSubject;

/**
 * @author caosanyang
 * @date 2019/4/8
 */
public class Test8_UnicastSubject {

    public static void main(String[] args) {
        new Test8_UnicastSubject().test();
    }

    @SuppressLint("CheckResult")
    private void test() {
        // 创建 unicastSubject 实例
        UnicastSubject<Integer> unicastSubject = UnicastSubject.create();

        // unicastSubject 发射一系列数据
        unicastSubject.onNext(0);
        unicastSubject.onNext(1);

        // unicastSubject.onComplete();

        // unicastSubject.onError(new Exception("onError"));

        // 订阅者 A: 可以收到所有数据
        unicastSubject.subscribe(t -> {
            System.out.println("A: " + t);
        }, e -> {
            System.out.println("A: " + e.getMessage());
        }, () -> {
            System.out.println("A: onComplete");
        });

        // 订阅者 B: unicastSubject 只能有一个订阅者，B 会收到 IllegalStateException 异常
        unicastSubject.subscribe(t -> {
            System.out.println("B: " + t);
        }, e -> {
            System.out.println("B: " + e.getMessage());
        }, () -> {
            System.out.println("B: onComplete");
        });

    }
}
