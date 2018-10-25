package me.demo.rx.operater_test;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
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
    //test_mergeDelayError();
    //test_zip();
    test_combineLatest();
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

  private static void test_mergeDelayError() {
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
  private static void test_combineLatest() {
    Observable<Long> newsRefreshes = Observable.interval(100, TimeUnit.MILLISECONDS,Schedulers.newThread());
    Observable<Long> weatherRefreshes = Observable.interval(50, TimeUnit.MILLISECONDS,Schedulers.newThread());
    Observable.combineLatest(newsRefreshes, weatherRefreshes,
        (newsRefreshTimes, weatherRefreshTimes) ->
            "Refreshed news " + newsRefreshTimes + " times and weather " + weatherRefreshTimes)
        .observeOn(Schedulers.trampoline())
        .subscribe(item -> System.out.println(item));
  }

  private static void test_join(){
    Observable<Integer> source1 = Observable.just(1,2,3);
    Observable<Integer> source2 = Observable.just(4,5,6);

  }

  private static void test_groupjoin(){

  }

}
