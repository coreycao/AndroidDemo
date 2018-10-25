package me.demo.rx.operater_test;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/**
 * @author caosanyang
 * @date 2018/10/22
 */
public class Test6_Utility {

  public static void main(String[] args) {
    //test_materialize();
    //test_dematerialize();
    //test_timestamp();
    //test_cache();
    //test_doXXX();
    //test_delay();
    //test_timeInterval();
    //test_using();
    //test_single();
    //test_repeat();
    test_repeatUtil();
    // todo retry
  }

  private static void test_materialize() {
    Observable.just(1, 2, 3)
        .materialize()
        .subscribe(System.out::println);
  }

  private static void test_dematerialize() {
    Observable.just(Notification.createOnNext(1), Notification.createOnNext(2),
        Notification.createOnNext(3))
        .dematerialize()
        .subscribe(System.out::println);
  }

  private static void test_timestamp() {
    Observable.just(1, 2, 3)
        .serialize()
        .timestamp()
        .subscribe(v -> {
          System.out.println(v.time() + " " + v.value());
        });
  }

  /**
   * todo
   */
  private static void test_serialize() {

  }

  private static void test_cache() {
    Observable.just(1, 2, 3)
        .cache()
        .subscribe(System.out::println);
  }

  private static void test_doXXX() {
    Observable.just("hello", "world")
        .doOnSubscribe(disposable -> System.out.println("doOnSubscribe"))
        .doOnNext(s -> System.out.println("doOnNext " + s))
        .doAfterNext(s -> System.out.println("doAfterNext " + s))
        .doOnComplete(() -> System.out.println("doOnComplete"))
        .doOnDispose(() -> System.out.println("doOnDispose"))
        .doOnError(throwable -> System.out.println("doOnError"))
        .doOnEach(
            stringNotification -> System.out.println("doOnEach " + stringNotification.getValue()))
        .doFinally(() -> System.out.println("doFinally"))
        .doOnTerminate(() -> System.out.println("doOnTerminate"))
        .doAfterTerminate(() -> System.out.println("doAfterTerminate"))
        .doOnLifecycle(disposable -> System.out.println("lifecycle onsub"),
            () -> System.out.println("lifecycle dispose"))
        .subscribe(s -> {
          System.out.println("onNext " + s);
        }, throwable -> {
          System.out.println("onError");
        }, () -> {
          System.out.println("onComplete");
        })
    ;
  }

  /**
   * todo
   */
  private static void test_delay() {
    Observable.just("delay")
        .subscribeOn(Schedulers.single())
        .delay(5, TimeUnit.SECONDS)
        .doOnNext(System.out::println)
        .doOnComplete(() -> System.out.println("onComplete"))
        .subscribe();
  }

  private static void test_timeInterval() {
    Observable.just(1, 2, 3)
        .timeInterval()
        .doOnNext(
            integerTimed -> System.out.println(integerTimed.time() + " " + integerTimed.value()))
        .subscribe();
  }

  /**
   * todo
   */
  private static void test_using() {
  }

  private static void test_single() {
    Observable.just(1, 2)
        .single(4)
        .subscribe(System.out::println, System.err::println);
  }

  private static void test_repeat() {
    Observable.just(1, 2)
        .repeat()
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_repeatUtil() {
    Observable.just(1, 2, 3)
        .repeatUntil(new BooleanSupplier() {
          @Override public boolean getAsBoolean() throws Exception {
            return false;
          }
        })
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_repeatWhen() {
    Observable.just(1)
        .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
          @Override public ObservableSource<?> apply(Observable<Object> objectObservable)
              throws Exception {
            return null;
          }
        });
  }
}
