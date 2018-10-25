package me.demo.rx.operater_test;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author caosanyang
 * @date 2018/10/19
 * Transformation
 */
public class Test2_Transformation {

  public static void main(String[] args) {
    //test_map();
    //test_flatMap();
    //test_flatMap2();
    //test_concatMap();
    //test_concatMap2();
    //test_flatMapIterable();
    //test_switchMap();
    //test_scan();
    //test_scan2();
    //test_groupBy();
    //test_buffer();
    test_window();
  }

  private static void test_map() {
    Observable.just(1, 2, 3)
        .map(v -> v * 2)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_flatMap() {
    Observable
        .just(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6),
            Arrays.asList(7, 8, 9)
        )
        .flatMap(new Function<List<Integer>, ObservableSource<?>>() {
          @Override public ObservableSource<?> apply(List<Integer> integers) throws Exception {
            return Observable.fromIterable(integers);
          }
        })
        .map(i -> i + ", ")
        .doOnNext(System.out::print)
        .subscribe();
  }

  /**
   * 打印结果为：
   * 20 21 30 31 10 11
   */
  private static void test_flatMap2() {
    Observable
        .just(10, 20, 30)
        .flatMap(integer -> {
          long delay = 200;
          if (integer == 10) delay = 2000;
          return Observable.just(integer, integer + 1)
              .delay(delay, TimeUnit.MILLISECONDS);
        })
        .map((integer) -> integer + " ")
        .doOnNext(System.out::print)
        .subscribe();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void test_flatMapIterable() {
    Observable
        .just(
            "Android",
            "iOS",
            "Web"
        )
        .flatMapIterable(new Function<String, Iterable<?>>() {
          @Override public Iterable<?> apply(String s) throws Exception {
            if (s.equals("Android")) {
              return Arrays.asList("Java", "Kotlin");
            } else if (s.equals("iOS")) {
              return Arrays.asList("OC", "Swift");
            } else {
              return Arrays.asList("React", "Vue");
            }
          }
        })
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_concatMap() {
    Observable.just(Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5, 6),
        Arrays.asList(7, 8, 9))
        .concatMap(Observable::fromIterable)
        .map(i -> i + ", ")
        .doOnNext(System.out::print)
        .subscribe();
  }

  /**
   * 10 11 20 21 30 31
   */
  private static void test_concatMap2() {
    Observable
        .just(10, 20, 30)
        .concatMap(integer -> {
          long delay = 200;
          if (integer == 10) delay = 2000;
          return Observable.just(integer, integer + 1)
              .delay(delay, TimeUnit.MILLISECONDS);
        })
        .map((integer) -> integer + " ")
        .doOnNext(System.out::print)
        .subscribe();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 打印结果为：
   * 20 21 30 31
   */
  private static void test_switchMap() {
    Observable
        .just(10, 20, 30)
        .switchMap(integer -> {
          long delay = 0;
          if (integer == 10) {
            delay = 300;
          } else if (integer == 30) {
            delay = 150;
          }
          return Observable.just(integer, integer + 1)
              .delay(delay, TimeUnit.MILLISECONDS);
        })
        .map((integer) -> integer + " ")
        .doOnNext(System.out::print)
        .subscribe();

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void test_scan() {
    Observable.just(1, 2, 3, 4)
        .scan(100, new BiFunction<Integer, Integer, Integer>() {
          @Override public Integer apply(Integer integer, Integer integer2) throws Exception {
            return integer + integer2;
          }
        })
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_scan2() {
    Observable.just("h", "e", "l", "l", "o")
        .scan(new BiFunction<String, String, String>() {
          @Override public String apply(String s1, String s2) throws Exception {
            return s1 + s2;
          }
        })
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_groupBy() {
    Observable.just(1, 2, 3, 4)
        .groupBy(new Function<Integer, Object>() {
          @Override public Object apply(Integer integer) throws Exception {
            if (integer % 2 == 0) {
              return "even";
            } else {
              return "odd";
            }
          }
        }).subscribe(new Consumer<GroupedObservable<Object, Integer>>() {
      @Override
      public void accept(GroupedObservable<Object, Integer> objectIntegerGroupedObservable)
          throws Exception {
        if (objectIntegerGroupedObservable.getKey().equals("even")) {
          objectIntegerGroupedObservable.subscribe(System.out::println);
        }
      }
    });
  }

  private static void test_buffer() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .buffer(3)
        .subscribe(new Consumer<List<Integer>>() {
          @Override public void accept(List<Integer> integers) throws Exception {
            printList(integers);
          }
        });
  }

  private static void printList(List<Integer> list) {
    for (Integer i : list) {
      System.out.print(i + ",");
    }
    System.out.println();
  }

  private static void test_window() {
    Observable.just(1, 2, 3, 4, 5, 6)
        .window(3)
        .subscribe(new Consumer<Observable<Integer>>() {
          @Override public void accept(Observable<Integer> integerObservable) throws Exception {
            integerObservable
                .doOnComplete(() -> System.out.println("complete"))
                .subscribe(System.out::print);
          }
        });
  }
}
