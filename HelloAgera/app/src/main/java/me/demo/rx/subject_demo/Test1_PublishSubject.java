package me.demo.rx.subject_demo;

import android.annotation.SuppressLint;

import io.reactivex.BackpressureStrategy;
import io.reactivex.subjects.PublishSubject;

/**
 * @author caosanyang
 * @date 2019/3/7
 */
public class Test1_PublishSubject {

    public static void main(String[] args) {
        new Test1_PublishSubject().test();
    }

    @SuppressLint("CheckResult")
    private void test() {

        // 使用工厂方法创建 Subject 实例
        PublishSubject<Integer> publishSubject = PublishSubject.create();

        // 此时还没有任何订阅者，因此 hasObservers() 返回 false
        System.out.println("publishSubject hasObservers:" + publishSubject.hasObservers());

        // 订阅者 A：在 publishSubject 还没有发射任何数据时便订阅，其可以收到 publishSubject 后续发射的所有数据
        publishSubject.subscribe(t -> {
            System.out.println("A:" + t);
        }, e -> {
            System.out.println("A:" + e.getMessage());
        }, () -> {
            System.out.println("A:onComplete");
        });

        // 此时已经有了订阅者，因此 hasObservers() 返回 true
        System.out.println("publishSubject hasObservers:" + publishSubject.hasObservers());

        // 发射一些数据
        publishSubject.onNext(0);
        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);
        publishSubject.onNext(4);

        // 订阅者 B，收不到上述数据，但可以收到后续数据
        publishSubject.subscribe(t -> {
            System.out.println("B:" + t);
        }, e -> {
            System.out.println("B:" + e.getMessage());
        }, () -> {
            System.out.println("B:onComplete");
        });

        // 继续发射数据
        publishSubject.onNext(5);
        publishSubject.onNext(6);
        publishSubject.onNext(7);

        // 订阅者 C，收不到 1-7 所有数据，若后续还有数据，则可以收到
        publishSubject.subscribe(t -> {
            System.out.println("C:" + t);
        }, e -> {
            System.out.println("C:" + e.getMessage());
        }, () -> {
            System.out.println("C:onComplete");
        });

        // 判断 Subject 是否到达了终止状态，并发射了 complete 事件
        System.out.println("publishSubject hasComplete:" + publishSubject.hasComplete());
        // 判断 Subject 是否到达了终止状态，并发射 error 事件
        System.out.println("publishSubject hasThrowable:" + publishSubject.hasThrowable());

        // 所有订阅者均可收到 onComplete 事件
        publishSubject.onComplete();
        // 所有订阅者均可收到 onError 事件
        // publishSubject.onError(new Exception("onError"));

        System.out.println("publishSubject hasComplete:" + publishSubject.hasComplete());
        System.out.println("publishSubject hasThrowable:" + publishSubject.hasThrowable());
        if (publishSubject.hasThrowable()) {
            System.out.println("publishSubject hasThrowable, throwable = " + publishSubject.getThrowable());
        }

        // 订阅者 D，即便在 onCompete 或者 onError 之后订阅，但是仍然可以收到相应的终止事件
        publishSubject.subscribe(t -> {
            System.out.println("D:" + t);
        }, e -> {
            System.out.println("D:" + e.getMessage());
        }, () -> {
            System.out.println("D:onComplete");
        });

        // 最终再发射一条数据
        // 若已经发射了 onComplete 或者 onError 终止了事件流，那么此处发射的数据将会发射失败，没有任何订阅者可以收到
        // 若事件流没有被终止，那么 A, B, C, D 四个订阅者都可以收到该数据
        publishSubject.onNext(8);

    }
}
