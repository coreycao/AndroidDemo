package me.demo.rx.subject_demo;

import android.annotation.SuppressLint;

import io.reactivex.subjects.BehaviorSubject;

/**
 * @author caosanyang
 * @date 2019/3/22
 */
public class Test3_BehaviorSubject {

    public static void main(String[] args) {
        new Test3_BehaviorSubject().test();
    }

    // private BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

    // private BehaviorSubject<String> behaviorSubject2 = BehaviorSubject.createDefault("this is default value");

    @SuppressLint("CheckResult")
    private void test() {
        // 创建一个有默认值的 BehaviorSubject 实例
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.createDefault("this is default value");

        // 订阅者 A: behaviorSubject 尚未发射任何数据时, A 发起了订阅，因此会收到 default value
        behaviorSubject.subscribe(i -> {
            System.out.println("A:" + i);
        }, e -> {
            System.out.println("A:" + e.getMessage());
        }, () -> {
            System.out.println("A: onComplete");
        });

        // behaviorSubject 发射一系列数据
        behaviorSubject.onNext("0");
        behaviorSubject.onNext("1");

        // 订阅者 B: 能够收到 behaviorSubject 缓存的最近一条数据，以及订阅之后的所有数据
        behaviorSubject.subscribe(i -> {
            System.out.println("B:" + i);
        }, e -> {
            System.out.println("B:" + e.getMessage());
        }, () -> {
            System.out.println("B: onComplete");
        });

        // behaviorSubject 调用 onComplete 结束事件流
        behaviorSubject.onComplete();

        // behaviorSubject 调用 onError 结束事件流
        //behaviorSubject.onError(new Exception("onError"));

        // 订阅者 C: 若 behaviorSubject 调用了 onComplete/onError 结束了事件流，那么 C 不会收到任何缓存的数据以及后续数据，只会收到 onComplete/onError 事件
        // 否则，能够收到最近一条缓存数据和后续所有数据
        behaviorSubject.subscribe(i -> {
            System.out.println("C:" + i);
        }, e -> {
            System.out.println("C:" + e.getMessage());
        }, () -> {
            System.out.println("C: onComplete");
        });

        // 若 behaviorSubject 调用了 onComplete/onError 结束了事件流，那么该条数据无法发射出去
        // 否则，可以成功发射出去，且 A, B, C 均可以收到该条数据
        behaviorSubject.onNext("2");
    }
}
