package me.demo.rx.subject_demo;

import android.annotation.SuppressLint;

import io.reactivex.subjects.MaybeSubject;

/**
 * @author caosanyang
 * @date 2019/4/8
 */
public class Test6_MaybeSubject {

    public static void main(String[] args) {
        new Test6_MaybeSubject().test();
    }

    @SuppressLint("CheckResult")
    private void test() {
        // 创建 maybeSubject 的实例
        MaybeSubject<Integer> maybeSubject = MaybeSubject.create();

        // 此处调用 onSuccess 发射数据，同时事件流也就终止，后续的任何发射都不再生效，包括 onComplete
        maybeSubject.onSuccess(0);

        // 订阅者 A: 在数据发射之后发起订阅，但是可以收到的相应的数据
        maybeSubject.subscribe(t -> {
            System.out.println("A:" + t);
        }, e -> {
            System.out.println("A:" + e.getMessage());
        }, () -> {
            System.out.println("A: onComplete");
        });

        // 若前面已经调用过 onSuccess/onComplete 结束了事件流，那么此处的调用不再生效
        maybeSubject.onSuccess(1);

        // 若前面已经调用过 onSuccess/onComplete 结束了事件流，那么此处的调用不再生效
        // 调用 onSuccess 虽然结束了事件流，但是并不会触发下游的 onComplete，只有调用 onComplete 才会触发下游的 onComplete
        maybeSubject.onComplete();

        // 调用 onError 同样可以结束事件流
        //maybeSubject.onError(new Exception("onError"));

        // 订阅者 B
        maybeSubject.subscribe(t -> {
            System.out.println("B:" + t);
        }, e -> {
            System.out.println("B:" + e.getMessage());
        }, () -> {
            System.out.println("B: onComplete");
        });
    }
}
