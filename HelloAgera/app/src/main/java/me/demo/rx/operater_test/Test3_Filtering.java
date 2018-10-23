package me.demo.rx.operater_test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.concurrent.TimeUnit;

/**
 * @author caosanyang
 * @date 2018/10/20
 * Filtering
 */
public class Test3_Filtering {

  public static void main(String[] args) {
    //test_filter();
    //test_takelast();
    //test_last();
    //test_skip();
    //test_skiplast();
    //test_take();
    //test_first();
    //test_elementAt();
    //test_firstElement();
    //test_throttlefirst();
    //test_throttlelast();
    //test_throttlelatest();
    //test_sample();
    //test_timeout();
    //test_distinct();
    //test_distinctUtilChanged();
    //test_oftype();
    test_ignore();
  }

  private static void test_filter() {
    Observable.just(1, 2, 3, 4, 5)
        .filter((integer) -> integer % 2 == 0)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_takelast() {
    Observable.just(1, 2, 3, 4, 5)
        .takeLast(2)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_last() {
    Observable.just(1, 2, 3)
        .last(0)
        .doOnSuccess(System.out::print)
        .subscribe();
  }

  private static void test_skip() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .skip(3)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_skiplast() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .skipLast(3)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_take() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .take(2)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_first() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .first(0)
        .doOnSuccess(System.out::println)
        .subscribe();
  }

  private static void test_elementAt() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .elementAt(3, 0)
        .doOnSuccess(System.out::println)
        .subscribe();
  }

  private static void test_firstElement() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .firstElement()
        .doOnSuccess(System.out::println)
        .subscribe();
  }

  private static void test_throttlefirst() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .throttleFirst(1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();
  }

  /**
   * ?
   */
  private static void test_throttlelast() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .throttleLast(1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();
  }

  private static void test_throttlelatest() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .throttleLatest(1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();
  }

  /**
   * ?
   */
  private static void test_sample() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .sample(1, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();
  }

  /**
   *
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

  private static void test_distinct() {
    Observable.just(1, 2, 3, 1, 2, 3)
        .distinct()
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_distinctUtilChanged() {
    Observable.just(1, 2, 3, 1, 2, 3, 4, 4, 4, 4)
        .distinctUntilChanged()
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_oftype() {
    Observable.just(1, "2")
        .ofType(String.class)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_ignore() {
    Observable.just(1, 2, 3)
        .ignoreElements()
        .doOnComplete(() -> System.out.println("onComplete"))
        .doOnError(System.err::println)
        .subscribe();
  }
}
