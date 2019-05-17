# RxJava 中的 Subject 详解

本文介绍了 RxJava 中提供的集中 Subject 的功能特性，代码示例可以在[这里](https://github.com/coreycao/AndroidDemo/tree/master/HelloAgera/app/src/main/java/me/demo/rx/subject_demo)看到。

## 什么是 Subject

RxJava 中的 Subject 继承自 Observable 类，同时实现了 Observer 接口。因此，它同时具备了 Observable 和 Observer 的能力，它既是“观察者”，同时又是“被观察者”。可将其类比为“管道”，它既可以一头接收数据，又可以在另一头发射数据。

## 各种 Subject 的简介
- PublishSubject
- ReplaySubject
- AsyncSubject
- BehaviorSubject

### PublishSubject

PublishSubject 是最功能最直观的一种 Subject，订阅者一旦订阅之后，那么只会接收到到订阅时间点之后发射出来的事件。一个更加生动的类比，可以把 PublishSubject 想像成一场正在直播的电视节目，没有任何订阅者时，它也在放送节目，而当观众（订阅者）打开电视观看这场直播时，也只能看到自己打开电视机（订阅操作）之后的节目。

抽象来讲，之所以说 PublishSubject 是功能最直观的一种 Subject，是因为其事件发射的时序与订阅者接收事件的时序是一致的。RxJava 提供的这几种 Subject 具有不同的功能特性，这些功能特性的主要差异就在于，订阅者订阅后接收到的事件，与该事件真实发射出的时机之间是否一致的差异。

上述描述还是比较抽象，我们通过下面的代码示例便可以非常直观地理解 Subject 是什么以及 PublishSubject 的功能特性了。

下述实例中，我们新建了一个 publishSubject 实例，发射简单的整形数据，然后使用了几个观察者在不同的时机发起订阅，可以看到，这些观察者们都只能收到其订阅时间点之后，发射出来的数据。这便是 PublishSubject。

```java
    // 使用工厂方法创建 Subject 实例
    PublishSubject<Integer> publishSubject = PublishSubject.create();

    // 此时还没有任何订阅者，因此 hasObservers() 返回 false
    System.out.println("publishSubject hasObservers:" + publishSubject.hasObservers());

    // 订阅者 A：在 publishSubject 还没有发射任何数据时便订阅，其可以收到 publishSubject 后续发射的所有数据
    publishSubject.subscribe(t -> {
        System.out.println("A:" + t);
    }, e -> {
        System.out.println("A:" + e.getMessage());
    }, () -> {
        System.out.println("A:onComplete");
    });

    // 此时已经有了订阅者，因此 hasObservers() 返回 true
    System.out.println("publishSubject hasObservers:" + publishSubject.hasObservers());

    // 发射一些数据
    publishSubject.onNext(0);
    publishSubject.onNext(1);
    publishSubject.onNext(2);
    publishSubject.onNext(3);
    publishSubject.onNext(4);

    // 订阅者 B，收不到上述数据，但可以收到后续数据
    publishSubject.subscribe(t->{
        System.out.println("B:" + t);
    }, e -> {
        System.out.println("B:" + e.getMessage());
    }, () -> {
        System.out.println("B:onComplete");
    });

    // 继续发射数据
    publishSubject.onNext(5);
    publishSubject.onNext(6);
    publishSubject.onNext(7);

    // 订阅者 C，收不到 1-7 所有数据，若后续还有数据，则可以收到
    publishSubject.subscribe(t->{
        System.out.println("C:" + t);
    }, e -> {
        System.out.println("C:" + e.getMessage());
    }, () -> {
        System.out.println("C:onComplete");
    });

    // 判断 Subject 是否到达了终止状态，并发射了 complete 事件
    System.out.println("publishSubject hasComplete:" + publishSubject.hasComplete());
    // 判断 Subject 是否到达了终止状态，并发射 error 事件
    System.out.println("publishSubject hasThrowable:" + publishSubject.hasThrowable());

    // 所有订阅者均可收到 onComplete 事件
    publishSubject.onComplete();
    // 所有订阅者均可收到 onError 事件
    // publishSubject.onError(new Exception("onError"));

    System.out.println("publishSubject hasComplete:" + publishSubject.hasComplete());
    System.out.println("publishSubject hasThrowable:" + publishSubject.hasThrowable());
    if (publishSubject.hasThrowable()){
        System.out.println("publishSubject hasThrowable, throwable = " + publishSubject.getThrowable());
    }

    // 订阅者 D，即便在 onCompete 或者 onError 之后订阅，但是仍然可以收到相应的终止事件
    publishSubject.subscribe(t -> {
        System.out.println("D:" + t);
    }, e -> {
        System.out.println("D:" + e.getMessage());
    }, () -> {
        System.out.println("D:onComplete");
    });

    // 最终再发射一条数据
    // 若已经发射了 onComplete 或者 onError 终止了事件流，那么此处发射的数据将会发射失败，没有任何订阅者可以收到
    // 若事件流没有被终止，那么 A, B, C, D 四个订阅者都可以收到该数据
    publishSubject.onNext(8);
```

> Subject 提供了 hasComplete, hasThrowable, hasObservers, getThrowable 等接口用来判断 Subject 的状态。

### ReplaySubject

若观察者订阅了 PublishSubject 则只会收到订阅时间点之后、数据源发射的事件。但是但我们打开电视收看节目时，不仅仅只想看那些实时直播给我们的节目，有时还需要能够“回看”已经播放过的节目，而 ReplaySubject 提供的功能则可以满足该需求。

ReplaySubject 中有一个缓冲区，当没有订阅者时，它不仅在发射数据，同时会把发射的这些数据缓存起来，一旦有订阅者订阅，则会首先把缓存下来的旧数据发射出去，然后按照正常的时序，将新的数据发射给已有的订阅者。通过下面的基础示例，可以更加直观地理解 ReplaySubject 的功能特性。

在下面的示例中，我们新建了一个 replaySubject 实例，并使用几个观察者在不同时间点对 replaySubject 进行订阅，可以看到，无论订阅者在什么时机发起订阅，均可以收到 replaySubject 发射的所有数据。

```java
    // 使用工厂方法创建 Subject 实例
    ReplaySubject<Integer> replaySubject1 = ReplaySubject.create();

    // 订阅者 A：在 replaySubject1 还没有发射任何数据时便订阅，可以收到 replaySubject1 发射的所有数据
    replaySubject1.subscribe(i -> {
        System.out.println("A" + i);
    }, e -> {
        System.out.println("A:" + e.getMessage());
    }, () -> {
        System.out.println("A: onComplete");
    });

    // replaySubject1 的 hasValue() 方法可以用来判断其是否有缓存为发送的数据，此时还未发射任何数据，hasValue == false
    System.out.println("replaySubject1 hasValue: " + replaySubject1.hasValue());

    // replaySubject1 发射一系列数据
    replaySubject1.onNext(0);
    replaySubject1.onNext(1);
    replaySubject1.onNext(2);
    replaySubject1.onNext(3);

    // replaySubject1 的 hasValue() 方法可以用来判断其是否有缓存为发送的数据，此时已经发射了数据，hasValue == true
    System.out.println("replaySubject1 hasValue: " + replaySubject1.hasValue());

    // 订阅者 B：在 replaySubject1 发射一系列数据之后订阅，可以收到订阅前 replaySubject1 发射的数据
    replaySubject1.subscribe(i -> {
        System.out.println("B" + i);
    }, e -> {
        System.out.println("B:" + e.getMessage());
    }, () -> {
        System.out.println("B: onComplete");
    });

    // replaySubject1 再次发射一系列数据
    replaySubject1.onNext(4);
    replaySubject1.onNext(5);
    replaySubject1.onNext(6);
    replaySubject1.onNext(7);

    // replaySubject1 调用 onComplete 结束事件流
    replaySubject1.onComplete();

    // replaySubject1 调用 onError 结束事件流
    // replaySubject1.onError(new Exception("onError"));

    // 订阅者 C：即便前面调用了 onComplete/onError 结束了事件流，此处发起订阅的订阅者 C 仍然可以收到 replaySubject1 缓存的所有数据
    // 当订阅者 C 收到所有的数据后，紧接着才会收到 onComplete/onError 事件
    replaySubject1.subscribe(i -> {
        System.out.println("C" + i);
    }, e -> {
        System.out.println("C:" + e.getMessage());
    }, () -> {
        System.out.println("C: onComplete");
    });

    // 若 replaySubject1 调用了 onComplete/onError 结束掉了事件流，那么此处的数据无法发射出去，否则可以正常发送出去，且订阅者 A, B, C 均可以收到
    replaySubject1.onNext(8);

    // getValue() 方法可以得到 replaySubject1 事件流结束前、缓存的最近一个数据
    System.out.println("replaySubject1 getValue(): " + replaySubject1.getValue());

    // getValues() 方法可以得到 replaySubject1 事件流结束前、缓存的所有数据
    System.out.print("replaySubject1 getValue(): ");
    Object[] values = replaySubject1.getValues();
    for (int i = 0; i < values.length; i++) {
        System.out.print(values[i] + (i == values.length - 1 ? "" : ", "));
    }
```

> ReplaySubject 也有 hasComplete, hasThrowable, hasObservers, getThrowable 等方法用来获取当前 Subject 的状态，此外，ReplaySubject 还有如下几个特有的方法：
> 1. hasValue 用来判断 ReplaySubject 当前是否有缓存的数据
> 2. getValue 可以获取事件流结束前，ReplaySubject 缓存的最近一个数据
> 3. getValues 可以获取事件流结束前，ReplaySubject 缓存的所有数据

默认地，ReplaySubject 中的“缓冲区”是无限大的，若考虑到性能等问题需要对该缓冲区进行一定的限制，那么 ReplaySubject 也提供了其他形式的工厂方法来构造出对缓冲区有限制的实例。

- create(n), 创建一个初始容量为 n 的 replaySubject，缓冲区仍然是无限大的，缓冲区的容量会随着使用而扩容，其扩容机制与 ArrayList 是一致的;
- createWithSize(n), 创建一个初始容量为 n 的 replaySubject， 意味着该 replaySubject 只会保留最近的 n 个数据;
- createWithTime(t, u), 只会保留最近 t 时间内的数据;
- createWithTimeAndSize(n, t, u), 只会保留最近 t 时间内的数据，且不超过 n 个。

```java
    // 创建一个容量为 3 的 replaySubject2
    ReplaySubject<Integer> replaySubject2 = ReplaySubject.createWithSize(3);

    // 在没有任何观察者订阅之前，replaySubject2 首先发射了 4 个数据，超过了其容量，因此缓冲区中只缓存了最近 3 个 
    replaySubject2.onNext(0);
    replaySubject2.onNext(1);
    replaySubject2.onNext(2);
    replaySubject2.onNext(3);

    // 订阅者 A：replaySubject2 发射了 4 个数据，超过了其容量，因此订阅者 A 只会收到其订阅前、replaySubject2最近发射的 3 个数据
    replaySubject2.subscribe(i -> {
        System.out.println("A" + i);
    }, e -> {
        System.out.println("A:" + e.getMessage());
    }, () -> {
        System.out.println("A: onComplete");
    });

    replaySubject2.onNext(4);

    // 订阅者 B：同理，订阅者 B 只会收到其订阅前、replaySubject2 最近发射的最近 3 个数据
    replaySubject2.subscribe(i -> {
        System.out.println("B" + i);
    }, e -> {
        System.out.println("B:" + e.getMessage());
    }, () -> {
        System.out.println("B: onComplete");
    });

    replaySubject2.onNext(5);

    // 订阅者 C: 同上
    replaySubject2.subscribe(i -> {
        System.out.println("C" + i);
    }, e -> {
        System.out.println("C:" + e.getMessage());
    }, () -> {
        System.out.println("C: onComplete");
    });
```

> 各类 Subject 都有 createXXX 这样的工厂方法来生成实例，而无需使用 new 关键字来初始化示例，一方面，使用工厂方法初始化实例能够保证 RxJava 的链式调用语法，另一方面，是因为使用命名直观的工厂方法直接创建具有不同特性的 Subject.

### BehaviorSubject

与 ReplaySubject 类似，BehaviorSubject 也是具有缓存功能的 Subject，不同之处在于如下两点：
1. BehaviorSubject 只会缓存最新的一个数据，从这点上看，它相当于是容量为 1 的 ReplaySubject;
2. 与容量为 1 的 ReplaySubject 不同之处在于，BehaviorSubject 收到 onError/onCompleted 之后，事件流终止，且缓存的数据会被丢弃，后来的订阅者只会收到 onError/onCompleted 了；而 ReplaySubject 则不同，即便使用 onError/onCompleted 终止了事件流，后来的订阅者也会收到事件流终止之前缓存下来的所有数据。

下面的示例演示了 BehaviorSubject 的基本使用。

```java
    // 创建一个有默认值的 BehaviorSubject 实例
    BehaviorSubject<String> behaviorSubject = BehaviorSubject.createDefault("this is default value");

    // 订阅者 A: behaviorSubject 尚未发射任何数据时, A 发起了订阅，因此会收到 default value
    behaviorSubject.subscribe(i -> {
        System.out.println("A:" + i);
    }, e -> {
        System.out.println("A:" + e.getMessage());
    }, () -> {
        System.out.println("A: onComplete");
    });

    // behaviorSubject 发射一系列数据
    behaviorSubject.onNext("0");
    behaviorSubject.onNext("1");

    // 订阅者 B: 能够收到 behaviorSubject 缓存的最近一条数据，以及订阅之后的所有数据
    behaviorSubject.subscribe(i -> {
        System.out.println("B:" + i);
    }, e -> {
        System.out.println("B:" + e.getMessage());
    }, () -> {
        System.out.println("B: onComplete");
    });

    // behaviorSubject 调用 onComplete 结束事件流
    behaviorSubject.onComplete();

    // behaviorSubject 调用 onError 结束事件流
    // behaviorSubject.onError(new Exception("onError"));

    // 订阅者 C: 若 behaviorSubject 调用了 onComplete/onError 结束了事件流，那么 C 不会收到任何缓存的数据以及后续数据，只会收到 onComplete/onError 事件
    // 否则，能够收到最近一条缓存数据和后续所有数据
    behaviorSubject.subscribe(i -> {
        System.out.println("C:" + i);
    }, e -> {
        System.out.println("C:" + e.getMessage());
    }, () -> {
        System.out.println("C: onComplete");
    });

    // 若 behaviorSubject 调用了 onComplete/onError 结束了事件流，那么该条数据无法发射出去
    // 否则，可以成功发射出去，且 A, B, C 均可以收到该条数据
    behaviorSubject.onNext("2");
```

> BehaviorSubject 使用 createDefault(defaultValue) 方法构造时可以传入一个默认值，当 BehaviorSubject 没有发射任何数据当有观察者订阅时，将会发射该默认值给对方。

### AsyncSubject

与 BehaviorSubject 类似，AsyncSubject 同样会记录收到的最后一个数据，不同之处在于，只有当使用 onComplete 终止事件流后，AsyncSubject 的订阅者才会收到最新的那个数据，并且紧接着有一个 onComplete 事件。

同样地，与 ReplaySubject 不同，如果我们给 AsyncSubject 发送了一个 onError 事件，那之前收到的数据都会被忽略，所有的订阅者都只会收到 onError 事件。

下面的示例演示了 AsyncSubject 基本使用。

```java
    // 使用工厂方法创建 Subject 实例
    AsyncSubject<Integer> asyncSubject = AsyncSubject.create();

    // 订阅者 A: 发起订阅
    asyncSubject.subscribe(i -> {
        System.out.println("A:" + i);
    }, e -> {
        System.out.println("A:" + e.getMessage());
    }, () -> {
        System.out.println("A: onComplete");
    });

    asyncSubject.onNext(0);
    asyncSubject.onNext(1);
    asyncSubject.onNext(2);

    // 只有 asyncSubject 调用了 onComplete 终止了事件流，A 和 B 才会收到事件，且只会收到最新的一个事件
    // 若事件流没有终止，则 A 和 B 不会收到任何事件
    asyncSubject.onComplete();

    // 若 asyncSubject 调用了 onError 终止了事件流，则 A 和 B 会收到 onError 通知，缓存的事件将会被忽略，不会发送给订阅者
    // asyncSubject.onError(new Exception("onError"));

    // 订阅者 B: 发起订阅
    asyncSubject.subscribe(i -> {
        System.out.println("B:" + i);
    }, e -> {
        System.out.println("B:" + e.getMessage());
    }, () -> {
        System.out.println("B: onComplete");
    });
```

### SingleSubject, MaybeSubject, CompletableSubject

我们回顾一下，RxJava2 提供的被观察者中，除了最常用的 Observable，以及支持背压的 Flowable 外，还提供了如下几种特殊的 Observable:
- Single: 它只能发射单个数据或者错误事件，即只有 onSucess 和 onError 方法来发送事件，一旦调用了 onSuccess，事件流就结束，无法再发射新的数据，无需、也没有相应的 omComplete 方法来结束事件流；
- Maybe: 能够发射单个数据或者什么数据都不发射，即可以调用 onSuccess, onComplete, onError 来发射事件以终止数据流；一旦调用了 onSuccess 发射数据，事件流即终止，此时无需再调用 onComplete 终止事件流，即便调用了 onComplete 也不会有任何作用；或者不发射任何数据，直接调用 onComplete 终止事件流，但是没有发射任何数据；
- Completable: 只有 onComplete, onError 这两个方法，也就是说不能发射任何数据，只能发射事件流的终止状态。

对应这三种 Observable，RxJava 还提供了三种 Subject
- SingleSubject
- MaybeSubject
- CompletableSubject

理解了Single, Maybe, Completable, 那么对应的 Subject 的使用也很容易理解，配合下述三个简单的示例，可以了解三者的基本使用。

首先是 SingleSubject 的基本使用示例：

```java
    // Test_SingleSubject.java

    // 创建 singleSubject 实例
    SingleSubject<Integer> singleSubject = SingleSubject.create();

    // 没有任何订阅者时便发射数据，同时事件流结束，后续的任何发射都不生效
    singleSubject.onSuccess(0);

    // 若在此处发射 onError 事件，那么事件流结束，后续的所有订阅者均可以收到 onError 事件
    // singleSubject.onError(new Exception("onError"));

    // 订阅者 A: 即便是在 singleSubject 发送事件之后才订阅，但是能够收到相应的数据，可见 singleSubject 是有类似 ReplaySubject 的缓存机制的
    singleSubject.subscribe(t -> {
        System.out.println("A:" + t);
    }, e -> {
        System.out.println("A:" + e.getMessage());
    });

    // 若在最开始已经发射了数据，那么事件流已经结束，此处的发射不再有效
    singleSubject.onSuccess(1);

    // 订阅者B
    singleSubject.subscribe(t -> {
        System.out.println("B:" + t);
    }, e -> {
        System.out.println("B:" + e.getMessage());
    });

    // 若之前发射了数据，那么事件流已经结束，此处的发射不再有效
    singleSubject.onSuccess(2);

    // 订阅者C
    singleSubject.subscribe(t -> {
        System.out.println("C:" + t);
    }, e -> {
        System.out.println("C:" + e.getMessage());
    });
```

> 需要注意的是：
> 1. Single 只能发射一次数据，因此一旦调用了 onSuccess 发射数据，那么数据流就结束，后续的发射都不再生效；
> 2. SingleSubject 类似 ReplaySubject，是有“缓存”机制的，也就是说，即便是在数据发射之后才发起订阅，那么这些订阅者依旧可以收到订阅之前发射的数据

其次是 MaybeSubject 的使用示例：

```java
    // Test_MaybeSubject.java

    // 创建 maybeSubject 的实例
    MaybeSubject<Integer> maybeSubject = MaybeSubject.create();

    // 此处调用 onSuccess 发射数据，同时事件流也就终止，后续的任何发射都不再生效，包括 onComplete
    maybeSubject.onSuccess(0);

    // 订阅者 A: 在数据发射之后发起订阅，但是可以收到的相应的数据
    maybeSubject.subscribe(t -> {
        System.out.println("A:" + t);
    }, e -> {
        System.out.println("A:" + e.getMessage());
    }, () -> {
        System.out.println("A: onComplete");
    });

    // 若前面已经调用过 onSuccess/onComplete 结束了事件流，那么此处的调用不再生效
    maybeSubject.onSuccess(1);

    // 若前面已经调用过 onSuccess/onComplete 结束了事件流，那么此处的调用不再生效
    // 调用 onSuccess 虽然结束了事件流，但是并不会触发下游的 onComplete，只有调用 onComplete 才会触发下游的 onComplete
    maybeSubject.onComplete();

    // 调用 onError 同样可以结束事件流
    //maybeSubject.onError(new Exception("onError"));

    // 订阅者 B
    maybeSubject.subscribe(t -> {
        System.out.println("B:" + t);
    }, e -> {
        System.out.println("B:" + e.getMessage());
    }, () -> {
        System.out.println("B: onComplete");
    });

```

> 需要注意的是：
> 1. 因此一旦调用了 onSuccess 发射数据，那么数据流就结束，后续的发射都不再生效；即便后续再调用 onComplete 也不会有效；
> 2. 单独调用 onComplete 才会触发下游观察者的 onComplete 事件；
> 3. MaybeSubject 也是有“缓存”机制的，只要订阅了，那么上游在任何时间点发射数据，下游都可以收到。

最后是 CompletableSubject 的使用示例，使用方式更为简单，也是有“缓存”机制，不论合适订阅，都能收到相应事件：
```java
    // 创建 completableSubject 实例
    CompletableSubject completableSubject = CompletableSubject.create();

    // 发送结束事件
    completableSubject.onComplete();

    // 发送异常事件
    // completableSubject.onError(new Exception("onError"));

    completableSubject.subscribe(() -> {
        System.out.println("A: onComplete");
    }, t -> {
        System.out.println("A:" + t.getMessage());
    });
    
    completableSubject.subscribe(() -> {
        System.out.println("B: onComplete");
    }, t -> {
        System.out.println("B:" + t.getMessage());
    });
```

> 如果稍微看一下源码的话，会发现 SingleSubject, MaybeSubject, CompletableSubject 与前面讲到的 PublishSubject, ReplaySubject, AsyncSubject, BehaviorSubject 还有一点不同之处在于，后面四者都是直接继承自 Subject，而前面三者并非直接继承自 Subject，其中 SingleSubject 继承自 Single，实现了 SingleObserver 接口，MaybeSubject 继承自 Maybe，实现了 MaybeObserver 接口，CompletableSubject 继承自 Completable，实现 CompletableObserver 接口。

### UnicastSubject

UnicastSubject 也与 ReplaySubject 类似，会缓存下来待发射的事件，一旦有订阅者将会把缓存的事件全部发送出去，特殊之处在于，UnicastSubject 只能被订阅一次，若有多个订阅者进行订阅，那么多余的订阅者将会收到 IllegalStateException 异常。

一个基本的示例如下：

```java
    // 创建 unicastSubject 实例
    UnicastSubject<Integer> unicastSubject = UnicastSubject.create();

    // unicastSubject 发射一系列数据
    unicastSubject.onNext(0);
    unicastSubject.onNext(1);

    // unicastSubject.onComplete();

    // unicastSubject.onError(new Exception("onError"));

    // 订阅者 A: 可以收到所有数据
    unicastSubject.subscribe(t -> {
        System.out.println("A: " + t);
    }, e -> {
        System.out.println("A: " + e.getMessage());
    }, () -> {
        System.out.println("A: onComplete");
    });

    // 订阅者 B: unicastSubject 只能有一个订阅者，B 会收到 IllegalStateException 异常
    unicastSubject.subscribe(t -> {
        System.out.println("B: " + t);
    }, e -> {
        System.out.println("B: " + e.getMessage());
    }, () -> {
        System.out.println("B: onComplete");
    });
```

### SerializedSubject

- SerializedSubject 是能够保证线程安全的 Subject，其中的 onNext, onError, onComplete 等方法都使用 synchronized 进行了同步处理；
- SerializedSubject 在 RxJava2 中并不是一个 public 的类，我们可以使用 Subject 的 toSerialized 方法，来获得一个同步的 SerializedSubject；
- 阅读源码会发现，SerializedSubject 实际上是对普通的 Subject 进行了“包装”处理，并对其中的方法进行同步控制。
- toSerialized 是 Subject 的方法，因此，并未继承自 Subject 的 SingleSubject, MaybeSubject 和 CompletableSubject 并没有此方法。

一个简单的使用示例如下：

```java
    PublishSubject<String> publishSubject = PublishSubject.create();
    publishSubject.toSerialized().onNext("hello SerializedSubject");
```

## Sum

本文只是介绍了 RxJava 提供的几种 Subject 的基本使用；后续文章中会进一步介绍其原理、应用，以及如何定义自己的 Subject。