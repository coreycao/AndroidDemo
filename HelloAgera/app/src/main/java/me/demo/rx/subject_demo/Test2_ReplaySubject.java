package me.demo.rx.subject_demo;

import android.annotation.SuppressLint;

import io.reactivex.subjects.ReplaySubject;

/**
 * @author caosanyang
 * @date 2019/3/8
 */
public class Test2_ReplaySubject {


    public static void main(String[] args) {
        new Test2_ReplaySubject().test1();
//        new Test2_ReplaySubject().test2();
    }

    // private ReplaySubject<Integer> replaySubject1 = ReplaySubject.create();

    //private ReplaySubject replaySubject2 = ReplaySubject.create(10);

    //private ReplaySubject replaySubject3 = ReplaySubject.createWithSize()

    // private ReplaySubject replaySubject4 = ReplaySubject.createWithTime();

    // private ReplaySubject replaySubject5 = ReplaySubject.createWithTimeAndSize()

    @SuppressLint("CheckResult")
    private void test1() {
        // 使用工厂方法创建 Subject 实例
        ReplaySubject<Integer> replaySubject1 = ReplaySubject.create();

        // 订阅者 A：在 replaySubject1 还没有发射任何数据时便订阅，可以收到 replaySubject1 发射的所有数据
        replaySubject1.subscribe(i -> {
            System.out.println("A" + i);
        }, e -> {
            System.out.println("A:" + e.getMessage());
        }, () -> {
            System.out.println("A: onComplete");
        });

        // replaySubject1 的 hasValue() 方法可以用来判断其是否有缓存为发送的数据，此时还未发射任何数据，hasValue == false
        System.out.println("replaySubject1 hasValue: " + replaySubject1.hasValue());

        // replaySubject1 发射一系列数据
        replaySubject1.onNext(0);
        replaySubject1.onNext(1);
        replaySubject1.onNext(2);
        replaySubject1.onNext(3);

        // replaySubject1 的 hasValue() 方法可以用来判断其是否有缓存为发送的数据，此时已经发射了数据，hasValue == true
        System.out.println("replaySubject1 hasValue: " + replaySubject1.hasValue());

        // 订阅者 B：在 replaySubject1 发射一系列数据之后订阅，可以收到订阅前 replaySubject1 发射的数据
        replaySubject1.subscribe(i -> {
            System.out.println("B" + i);
        }, e -> {
            System.out.println("B:" + e.getMessage());
        }, () -> {
            System.out.println("B: onComplete");
        });

        // replaySubject1 再次发射一系列数据
        replaySubject1.onNext(4);
        replaySubject1.onNext(5);
        replaySubject1.onNext(6);
        replaySubject1.onNext(7);


        // replaySubject1 调用 onComplete 结束事件流
        replaySubject1.onComplete();

        // replaySubject1 调用 onError 结束事件流
        // replaySubject1.onError(new Exception("onError"));


        // 订阅者 C：即便前面调用了 onComplete/onError 结束了事件流，此处发起订阅的订阅者 C 仍然可以收到 replaySubject1 缓存的所有数据
        // 当订阅者 C 收到所有的数据后，紧接着才会收到 onComplete/onError 事件
        replaySubject1.subscribe(i -> {
            System.out.println("C" + i);
        }, e -> {
            System.out.println("C:" + e.getMessage());
        }, () -> {
            System.out.println("C: onComplete");
        });

        // 若 replaySubject1 调用了 onComplete/onError 结束掉了事件流，那么此处的数据无法发射出去，否则可以正常发送出去，且订阅者 A, B, C 均可以收到
        replaySubject1.onNext(8);

        // getValue() 方法可以得到 replaySubject1 事件流结束前、缓存的最近一个数据
        System.out.println("replaySubject1 getValue(): " + replaySubject1.getValue());

        // getValues() 方法可以得到 replaySubject1 事件流结束前、缓存的所有数据
        System.out.print("replaySubject1 getValue(): ");
        Object[] values = replaySubject1.getValues();
        for (int i = 0; i < values.length; i++) {
            System.out.print(values[i] + (i == values.length - 1 ? "" : ", "));
        }
    }

    @SuppressLint("CheckResult")
    private void test2() {
        // 创建一个容量为 3 的 replaySubject2
        ReplaySubject<Integer> replaySubject2 = ReplaySubject.createWithSize(3);

        // 在没有任何观察者订阅之前，replaySubject2 首先发射了 4 个数据，超过了其容量，因此缓冲区中只缓存了最近 3 个
        replaySubject2.onNext(0);
        replaySubject2.onNext(1);
        replaySubject2.onNext(2);
        replaySubject2.onNext(3);

        // 订阅者 A：replaySubject2 发射了 4 个数据，超过了其容量，因此订阅者 A 只会收到其订阅前、replaySubject2最近发射的 3 个数据
        replaySubject2.subscribe(i -> {
            System.out.println("A" + i);
        }, e -> {
            System.out.println("A:" + e.getMessage());
        }, () -> {
            System.out.println("A: onComplete");
        });

        replaySubject2.onNext(4);

        // 订阅者 B：同理，订阅者 B 只会收到其订阅前、replaySubject2 最近发射的最近 3 个数据
        replaySubject2.subscribe(i -> {
            System.out.println("B" + i);
        }, e -> {
            System.out.println("B:" + e.getMessage());
        }, () -> {
            System.out.println("B: onComplete");
        });

        replaySubject2.onNext(5);

        // 订阅者 C: 同上
        replaySubject2.subscribe(i -> {
            System.out.println("C" + i);
        }, e -> {
            System.out.println("C:" + e.getMessage());
        }, () -> {
            System.out.println("C: onComplete");
        });

    }
}
