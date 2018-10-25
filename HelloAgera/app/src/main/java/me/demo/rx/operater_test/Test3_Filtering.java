package me.demo.rx.operater_test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/**
 * @author caosanyang
 * @date 2018/10/20
 * Filtering
 */
public class Test3_Filtering {

  public static void main(String[] args) {
    //test_filter();
    //test_takeLast();
    //test_last();
    //test_skip();
    //test_skipLast();
    //test_take();
    //test_first();
    //test_elementAt();
    //test_firstElement();
    //test_throttleFirst();
    //test_throttleLast();
    //test_throttleLatest();
    //test_sample();
    //test_debounce();
    //test_timeout();
    //test_distinct();
    //test_distinctUntilChanged();
    //test_ofType();
    test_ignoreElements();
  }

  /**
   * 过滤掉不符合条件的
   */
  private static void test_filter() {
    Observable.just(1, 2, 3, 4, 5)
        .filter((integer) -> integer % 2 == 0)
        .doOnNext(System.out::println)
        .subscribe();
  }

  /**
   * 取前两个
   */
  private static void test_take() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .take(2)
        .doOnNext(System.out::println)
        .subscribe();
  }

  /**
   * 取后两个
   */
  private static void test_takeLast() {
    Observable.just(1, 2, 3, 4, 5)
        .takeLast(2)
        .doOnNext(System.out::println)
        .subscribe();
  }

  /**
   * 跳过前三个
   */
  private static void test_skip() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .skip(3)
        .doOnNext(System.out::println)
        .subscribe();
  }

  /**
   * 跳过后三个
   */
  private static void test_skipLast() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .skipLast(3)
        .doOnNext(System.out::println)
        .subscribe();
  }

  /**
   * 取第一个
   */
  private static void test_first() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .first(0)
        .doOnSuccess(System.out::println)
        .subscribe();
  }

  /**
   * 取最后一个
   */
  private static void test_last() {
    Observable.just(1, 2, 3)
        .last(0)
        .doOnSuccess(System.out::print)
        .subscribe();
  }

  /**
   * 取指定位置的元素
   */
  private static void test_elementAt() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .elementAt(3, 0)
        .doOnSuccess(System.out::println)
        .subscribe();
  }

  /**
   * 取第一个元素
   */
  private static void test_firstElement() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .firstElement()
        .doOnSuccess(System.out::println)
        .subscribe();
  }

  /**
   * 取一定时间段内的第一个
   */
  private static void test_throttleFirst() {
    Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
        .throttleFirst(1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();
  }

  /**
   * 取一定时间段内的最后一个
   */
  private static void test_throttleLast() {
    Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
        .throttleLast(1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();
  }

  private static void test_throttleLatest() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .throttleLatest(1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();
  }

  /**
   * sample 与 throttleLast 相同
   */
  private static void test_sample() {
    Observable.interval(500, TimeUnit.MILLISECONDS)
        .sample(1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * throttleWithTimeout 和 debounce 一样
   * 过滤掉指定时间段内的元素
   * interval 发射间隔为 500 ms，若 debounce timeout 大于等于 500 ms，那么不会有元素被打印出来，反之则全部能够被打印出来
   */
  private static void test_debounce() {
    Observable.interval(500, TimeUnit.MILLISECONDS,Schedulers.trampoline())
        .debounce(400, TimeUnit.MILLISECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();
  }

  /**
   * 超时
   */
  private static void test_timeout() {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
        emitter.onNext(1);
        Thread.sleep(2000);
        emitter.onNext(2);
        emitter.onComplete();
      }
    })
        .timeout(1, TimeUnit.SECONDS)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe(System.out::println, System.out::println);
  }

  /**
   * 去重
   */
  private static void test_distinct() {
    Observable.just(1, 2, 3, 1, 2, 3)
        .distinct()
        .doOnNext(System.out::println)
        .subscribe();
  }

  /**
   * 元素连续重复时才去重
   */
  private static void test_distinctUntilChanged() {
    Observable.just(1, 2, 3, 1, 2, 3, 4, 4, 4, 4)
        .distinctUntilChanged()
        .doOnNext(System.out::println)
        .subscribe();
  }

  /**
   * 去除类型不符合条件的元素
   */
  private static void test_ofType() {
    Observable.just(1, "2")
        .ofType(String.class)
        .doOnNext(System.out::println)
        .subscribe();
  }

  /**
   * 忽略所有元素
   */
  private static void test_ignoreElements() {
    Observable.just(1, 2, 3)
        .ignoreElements()
        .doOnComplete(() -> System.out.println("onComplete"))
        .doOnError(System.err::println)
        .subscribe();
  }
}
