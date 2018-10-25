package me.demo.rx.operater_test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author caosanyang
 * @date 2018/10/17
 * creation
 */
public class Test1_Creation {

  private static Disposable disposable;

  public static void main(String[] args) {
    test_just();
    //test_create();
    //test_create_onError();
    //test_fromArray();
    //test_fromIterator();
    //test_fromCallable();
    //test_fromFuture();
    //test_defer();
    //test_range();
    //test_interval();
    //test_interval_range();
    //test_timer();
    //test_never();
    //test_empty();
    //test_error();
  }

  private static void test_just() {
    disposable = Observable.just(1, 2, 3, 4)
        .subscribe(System.out::println);
  }

  /**
   * This method allows bridging the non-reactive, usually listener/callback-style world, with the reactive world.
   */
  private static void test_create() {
    disposable = Observable.create(new ObservableOnSubscribe<String>() {
      @Override public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("h");
        emitter.onNext("i");
        emitter.onComplete();
      }
    })
        .subscribe(System.out::println,
            System.err::println,
            () -> System.out.println("test_create_onComplete")
        );
  }

  private static void test_create_onError() {
    disposable = Observable.create(new ObservableOnSubscribe<String>() {
      @Override public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("h");
        emitter.onError(new IllegalArgumentException("test_create_onError"));
        emitter.onNext("i");
        emitter.onComplete();
      }
    }).subscribe(System.out::println,
        System.err::println,
        () -> System.out.println("test_create_onComplete"));
  }

  /**
   * RxJava does not support primitive arrays, only (generic) reference arrays
   */
  private static void test_fromArray() {
    Integer[] ints = new Integer[] { 1, 2, 3 };
    int[] primitiveInts = new int[] { 1, 2, 3 };
    disposable = Observable.fromArray(ints)
        .subscribe(System.out::println);
  }

  private static void test_fromIterator() {
    List<String> stringList = Arrays.asList("hello", "world");
    disposable = Observable.fromIterable(stringList)
        .subscribe(System.out::println);
  }

  private static void test_fromCallable() {
    disposable = Observable.fromCallable(() -> "hello world")
        .subscribe(System.out::println);
  }

  private static void test_fromFuture() {
    ScheduledExecutorService executor = Executors
        .newSingleThreadScheduledExecutor();
    Future<String> future = executor.schedule(() -> "hello future", 1, TimeUnit.SECONDS);
    disposable = Observable.fromFuture(future).subscribe(System.out::println);
    executor.shutdown();
  }

  /**
   * create cold observables
   * do not create the Observable until the observer subscribes, and create a fresh Observable for each observer
   */
  private static void test_defer() {
    Observable<Long> observable = Observable.defer(() -> {
      long time = System.currentTimeMillis();
      return Observable.just(time);
    });

    observable.subscribe(time -> System.out.println(time));

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    observable.subscribe(time -> System.out.println(time));
  }

  private static void test_range() {
    disposable = Observable.range(0, 10)
        .subscribe(System.out::println);
  }

  private static void test_interval() {
    disposable = Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline())
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_interval_range() {
    disposable = Observable.intervalRange(1, 10, 2, 1, TimeUnit.SECONDS, Schedulers.io())
        .subscribe(System.out::println);
  }

  /**
   * 5秒之后发射0L
   */
  private static void test_timer(){
    Observable.timer(5,TimeUnit.SECONDS)
        .blockingSubscribe(System.out::println);
  }

  /**
   * This type of reactive source is useful in testing or "disabling" certain sources in combinator operators
   */
  private static void test_never() {
    disposable = Observable.never()
        .subscribe(o -> System.out.println("test_never_onNext..."),
            throwable -> System.out.println("text_never_onError..."),
            () -> System.out.println("test_never_onComplete..."));
  }

  private static void test_empty() {
    disposable = Observable.empty()
        .subscribe(System.out::println,
            System.out::println,
            () -> System.out.println("onComplete"));
  }

  private static void test_error() {
    disposable = Observable.error(new NullPointerException("test_error"))
        .subscribe(System.out::println,
            System.out::println,
            () -> System.out.println("onComplete"));
  }
}
