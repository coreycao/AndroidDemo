package me.demo.rx.subject_demo;

import android.annotation.SuppressLint;

import io.reactivex.subjects.CompletableSubject;

/**
 * @author caosanyang
 * @date 2019/4/8
 */
public class Test7_CompletableSubject {
    public static void main(String[] args) {
        new Test7_CompletableSubject().test();
    }

    @SuppressLint("CheckResult")
    private void test() {
        // 创建 completableSubject 实例
        CompletableSubject completableSubject = CompletableSubject.create();

        // 发送结束事件
        completableSubject.onComplete();

        // 发送异常事件
        // completableSubject.onError(new Exception("onError"));

        completableSubject.subscribe(() -> {
            System.out.println("A: onComplete");
        }, t -> {
            System.out.println("A:" + t.getMessage());
        });

        completableSubject.subscribe(() -> {
            System.out.println("B: onComplete");
        }, t -> {
            System.out.println("B:" + t.getMessage());
        });

    }
}
