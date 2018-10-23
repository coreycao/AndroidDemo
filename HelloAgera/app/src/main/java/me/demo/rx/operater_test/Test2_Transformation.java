package me.demo.rx.operater_test;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import java.util.Arrays;
import java.util.List;

/**
 * @author caosanyang
 * @date 2018/10/19
 * Transformation
 */
public class Test2_Transformation {

  public static void main(String[] args) {
    test_map();
    //test_flatmap();
    //test_concatmap();
    //test_flatMapIterable();
    //test_switchMap();
    //test_scan();
    //test_groupby();
    //test_buffer();
    //test_window();
  }

  private static void test_map() {
    Observable.just(1, 2, 3)
        .map(v -> v * 2)
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_flatmap() {
    Observable.just(Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5, 6),
        Arrays.asList(7, 8, 9))
        .flatMap(Observable::fromIterable)
        .map(i -> i + ", ")
        .doOnNext(System.out::print)
        .subscribe();
  }

  private static void test_concatmap() {
    Observable.just(Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5, 6),
        Arrays.asList(7, 8, 9))
        .concatMap(new Function<List<Integer>, ObservableSource<?>>() {
          @Override public ObservableSource<?> apply(List<Integer> integers) throws Exception {
            return null;
          }
        })
        .map(i -> i + ", ")
        .doOnNext(System.out::print)
        .subscribe();
  }

  private static void test_flatMapIterable() {
    Observable.just(Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5, 6),
        Arrays.asList(7, 8, 9))
        .flatMapIterable(new Function<List<Integer>, Iterable<?>>() {
          @Override public Iterable<?> apply(List<Integer> integers) throws Exception {
            return integers;
          }
        })
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_switchMap() {
    Observable.just(Arrays.asList(1, 2, 3),
        Arrays.asList(4, 5, 6),
        Arrays.asList(7, 8, 9))
        .switchMap(new Function<List<Integer>, ObservableSource<?>>() {
          @Override public ObservableSource<?> apply(List<Integer> integers) throws Exception {
            return Observable.fromIterable(integers);
          }
        })
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_scan() {
    Observable.just(1, 2, 3, 4)
        .scan(1, new BiFunction<Integer, Integer, Integer>() {
          @Override public Integer apply(Integer integer, Integer integer2) throws Exception {
            return integer + integer2;
          }
        })
        .doOnNext(System.out::println)
        .subscribe();
  }

  private static void test_groupby() {
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
