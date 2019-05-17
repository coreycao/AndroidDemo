package me.demo.rx.subject_demo;

import android.annotation.SuppressLint;

import io.reactivex.subjects.SingleSubject;

/**
 * @author caosanyang
 * @date 2019/4/8
 */
public class Test5_SingleSubject {
    public static void main(String[] args) {
        new Test5_SingleSubject().test();
    }

    @SuppressLint("CheckResult")
    private void test() {
        // 创建 singleSubject 实例
        SingleSubject<Integer> singleSubject = SingleSubject.create();

        // 没有任何订阅者时便发射数据，同时事件流结束，后续的任何发射都不生效
        singleSubject.onSuccess(0);

        // 若在此处发射 onError 事件，那么事件流结束，后续的所有订阅者均可以收到 onError 事件
        // singleSubject.onError(new Exception("onError"));

        // 订阅者 A: 即便是在 singleSubject 发送事件之后才订阅，但是能够收到相应的数据，可见 singleSubject 是有类似 ReplaySubject 的缓存机制的
        singleSubject.subscribe(t -> {
            System.out.println("A:" + t);
        }, e -> {
            System.out.println("A:" + e.getMessage());
        });

        // 若在最开始已经发射了数据，那么事件流已经结束，此处的发射不再有效
        singleSubject.onSuccess(1);

        // 订阅者B
        singleSubject.subscribe(t -> {
            System.out.println("B:" + t);
        }, e -> {
            System.out.println("B:" + e.getMessage());
        });

        // 若之前发射了数据，那么事件流已经结束，此处的发射不再有效
        singleSubject.onSuccess(2);

        // 订阅者C
        singleSubject.subscribe(t -> {
            System.out.println("C:" + t);
        }, e -> {
            System.out.println("C:" + e.getMessage());
        });

    }
}
