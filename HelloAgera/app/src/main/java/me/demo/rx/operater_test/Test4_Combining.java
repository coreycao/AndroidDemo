package me.demo.rx.operater_test;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import java.util.concurrent.TimeUnit;

/**
 * @author caosanyang
 * @date 2018/10/22
 * Combining
 */
public class Test4_Combining {
  public static void main(String[] args) {
    //test_startwith();
    //test_merge();
    //test_mergedelayerror();
    //test_zip();
    test_combinelatest();
  }

  private static void test_startwith() {
    Observable.just(1, 2, 3)
        .startWith(0)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_merge() {
    Observable.just(1, 2, 3)
        .mergeWith(Observable.just(4, 5, 6))
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_mergedelayerror() {
    Observable.mergeDelayError(
        Observable.just(1, 2, 3),
        Observable.error(new IllegalArgumentException()))
        .subscribe(System.out::println, System.err::println,
            () -> System.out.println("onComplete"));
  }

  private static void test_zip() {
    Observable.just(1, 2, 3)
        .zipWith(Observable.just(4, 5, 6), new BiFunction<Integer, Integer, Object>() {
          @Override public Object apply(Integer integer, Integer integer2) throws Exception {
            return integer + integer2;
          }
        })
        .doOnNext(System.out::println)
        .subscribe();
  }

  /**
   * ?
   */
  private static void test_combinelatest() {
    Observable<Long> newsRefreshes = Observable.interval(100, TimeUnit.MILLISECONDS);
    Observable<Long> weatherRefreshes = Observable.interval(50, TimeUnit.MILLISECONDS);
    Observable.combineLatest(newsRefreshes, weatherRefreshes,
        (newsRefreshTimes, weatherRefreshTimes) ->
            "Refreshed news " + newsRefreshTimes + " times and weather " + weatherRefreshTimes)
        .subscribe(item -> System.out.println(item));
  }

  private static void test_join(){

  }

  private static void test_groupjoin(){

  }

}
