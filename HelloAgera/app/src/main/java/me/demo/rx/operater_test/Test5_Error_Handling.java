package me.demo.rx.operater_test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author caosanyang
 * @date 2018/10/22
 * Error Handling
 */
public class Test5_Error_Handling {
  public static void main(String[] args) {
    //test_onErrorReturn();
    //test_onErrorResumeNext();
    //test_onErrorResumeNext2();
    //test_onErrorReturnItem();
    test_onExceptionResumeNext();
  }

  private static Observable createObserable() {
    return Observable.create(new ObservableOnSubscribe<Object>() {
      @Override public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
        emitter.onNext(1);
        emitter.onNext(2);
        emitter.onError(new NullPointerException());
        emitter.onNext(3);
        emitter.onComplete();
      }
    });
  }

  private static void test_onErrorReturn() {
    createObserable().onErrorReturn(new Function() {
      @Override public Object apply(Object o) throws Exception {
        return 0;
      }
    })
        .subscribe(System.out::println, System.err::println,
            () -> System.out.println("onComplete"));
  }

  private static void test_onErrorResumeNext() {
    createObserable().onErrorResumeNext(Observable.just(5, 6, 7))
        .subscribe(System.out::println, System.err::println,
            () -> System.out.println("onComplete"));
  }

  private static void test_onErrorResumeNext2() {
    createObserable().onErrorResumeNext(new Function<Throwable, ObservableSource>() {
      @Override public ObservableSource apply(Throwable throwable) throws Exception {
        if (throwable instanceof IllegalArgumentException) {
          return Observable.just(0);
        } else {
          return Observable.just(10);
        }
      }
    })
        .subscribe(System.out::println, System.err::println,
            () -> System.out.println("onComplete"));
  }

  private static void test_onErrorReturnItem() {
    createObserable().onErrorReturnItem(11)
        .subscribe(System.out::println, System.err::println,
            () -> System.out.println("onComplete"));
  }

  private static void test_onExceptionResumeNext() {
    createObserable().onExceptionResumeNext(Observable.just(3, 4, 5))
        .subscribe(System.out::println, System.err::println,
            () -> System.out.println("onComplete"));
  }
}
