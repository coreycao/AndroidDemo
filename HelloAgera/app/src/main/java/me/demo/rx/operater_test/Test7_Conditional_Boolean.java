package me.demo.rx.operater_test;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author caosanyang
 * @date 2018/10/22
 * Conditional & Boolean
 */
public class Test7_Conditional_Boolean {
  public static void main(String[] args) {
    //test_ambWith();
    //test_ambArray();
    //test_amb();
    //test_defaultIfEmpty();
    //test_switchIfEmpty();
    //test_takeUntil();
    //test_takeUntil2();
    //test_takeWhile();
    //test_skipUntil();
    //test_skipWhile();
    //test_all();
    //test_contains();
    //test_isEmpty();
    //test_any();
    test_sequenceEqual();
    //test_sequenceEqual2();
  }

  private static void test_ambWith() {
    Observable.just(1, 2, 3).delay(5, TimeUnit.SECONDS).ambWith(Observable.just(4, 5, 6))
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_ambArray() {
    Observable.ambArray(Observable.just(1, 2, 3).delay(5, TimeUnit.SECONDS),
        Observable.just(4, 5, 6))
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_amb() {
    Observable<Integer> source1 = Observable.just(1, 2, 3).delay(5, TimeUnit.SECONDS);
    Observable<Integer> source2 = Observable.just(4, 5, 6);
    Observable.amb(Arrays.asList(source1, source2))
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_defaultIfEmpty() {
    Observable
        .empty()
        .defaultIfEmpty(10)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_switchIfEmpty() {
    Observable
        .empty()
        .switchIfEmpty(Observable.just(1, 2, 3))
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_takeUntil() {
    Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline())
        .takeUntil(Observable.just(100).delay(4, TimeUnit.SECONDS))
        .doOnSubscribe(disposable -> System.out.println("onSub"))
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .doFinally(() -> {
          System.out.println("doFinally");
        })
        .subscribe();
  }

  private static void test_takeUntil2() {
    Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline())
        .takeUntil(aLong -> aLong == 5)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_takeWhile() {
    Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline())
        .takeWhile(aLong -> aLong != 5)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_skipUntil() {
    Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline())
        .skipUntil(Observable.just(100).delay(4, TimeUnit.SECONDS))
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_skipWhile() {
    Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline())
        .skipWhile(aLong -> aLong != 5)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_all() {
    Observable.just(1, 2, 13)
        .all(integer -> integer < 10)
        .doOnSuccess(System.out::println)
        .subscribe();
  }

  private static void test_contains() {
    Observable.just(1, 2, 13)
        .contains(3)
        .doOnSuccess(System.out::println)
        .subscribe();
  }

  private static void test_isEmpty() {
    //Observable.empty().isEmpty().doOnSuccess(System.out::println).subscribe();
    Observable.just(1).isEmpty().doOnSuccess(System.out::println).subscribe();
  }

  private static void test_any() {
    Observable.just(13).any(integer -> integer < 10).doOnSuccess(System.out::println).subscribe();
  }

  private static void test_sequenceEqual() {
    Observable<Integer> source1 = Observable.just(1, 2, 3);
    Observable<Integer> source2 = Observable.just(1, 2, 3);
    Observable.sequenceEqual(source1, source2)
        .doOnSuccess(System.out::println)
        .subscribe();
  }

  private static void test_sequenceEqual2() {
    Observable<Integer> source1 = Observable.just(1, 2, 3);
    Observable<Integer> source2 = Observable.just(2, 3, 4);
    Observable.sequenceEqual(source1, source2, (integer, integer2) -> integer == integer2 - 1)
        .doOnSuccess(System.out::println)
        .subscribe();
  }
}
