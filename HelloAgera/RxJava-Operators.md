# RxJava 常用操作符详解

## Ref
1. https://github.com/ReactiveX/RxJava/wiki
2. http://reactivex.io/documentation/operators.html
3. http://mushuichuan.com/2015/12/11/rxjava-operator-1/
4. https://blog.csdn.net/Job_Hesc/article/details/46495281

## Intro

RxJava 提供了上百个操作符，灵活地使用这些操作符可以方便地实现对 Observable 的创建、变换、过滤等操作。本文主要介绍常用操作符及个别复杂操作符的基本使用、使用细节、使用场景等，针对一些较为简易的操作符则一笔带过，多数操作符的使用都可参考 reactivex 官网文档及 RxJava 仓库 wiki 来学习了解。

> reactivex 的文档中大量地使用了 marble diagrams 这种图表来解释操作符的功能，非常直观，点击[这里](http://reactivex.io/documentation/observable.html) 查看如何阅读并理解 marble diagrams.

从功能上，可以将 RxJava 提供的操作符划分为如下几个大类，本文将按照这个类别来逐一介绍。

- Creation
- Transformation
- Filtering
- Combining
- Error Handling
- Utility
- Conditional & Boolean
- Mathematical & Aggregate

本文示例代码可以参见[这里](https://github.com/coreycao/AndroidDemo/tree/master/HelloAgera/app/src/main/java/me/demo/rx/operater_test)

## 正文

### Creation

创建类的操作符能够从各种来源创建 Observable，常见的创建类操作符如下所示：
- just
- create
- fromArray
- fromIterator
- fromCallable
- fromFuture
- defer
- range
- interval
- intervalRange
- timer
- never
- empty
- error

多数创建类操作符的使用较为简单，此处只介绍其中几个需要注意的操作符：

#### create

create 操作符是最为灵活的创建类操作符，一个基础的使用示例如下：
```Java
Observable.create(new ObservableOnSubscribe<String>() {
      @Override public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        emitter.onNext("h");
        emitter.onError(new IllegalArgumentException("test_create_onError"));
        emitter.onNext("i");
        emitter.onComplete();
      }
    }).subscribe(System.out::println,
        System.err::println,
        () -> System.out.println("test_create_onComplete"));
```

使用 create 操作符创建 Observable，可以在 subscribe 方法中实现复杂的逻辑，并且使用 Emitter 灵活地发送数据，因此常用其来将一些非 Reactive 的场景进行改造，如可以将传统的网络请求改造成 Reactive 的形式。具体示例可以参见 [RxJava-In-Practice](https://github.com/coreycao/AndroidDemo/blob/master/HelloAgera/RxJava-In-Practice.md) 一文。

#### [defer](https://github.com/ReactiveX/RxJava/wiki/Creating-Observables#defer)

Observable#defer 使用一个 Callable 作为参数来创建 Observalbe，它具有以下特性：
- 针对每一次订阅都会重新发送事件序列，如下文的例子，两次订阅会返回不同的时间
- 只有执行了订阅之后才会生成对应的 Observable
- 可以用来将 Hot-Observable 改造成为 Cold-Observable

> 所谓 Hot-Observable 是指 Obserable 即便没有被任何观察者订阅，也在发射数据或者事件；Cold-Observable 则反之，只有被订阅了之后才会开始发送数据和事件。

```Java
Observable<Long> observable = Observable.defer(() -> {
    long time = System.currentTimeMillis();
    return Observable.just(time);
});

observable.subscribe(time -> System.out.println(time));

Thread.sleep(1000);

observable.subscribe(time -> System.out.println(time));
```
上述代码实现的示例中，我们对同一个 Observable 订阅了两次，但是打印出来的时间不同，说明 defer 创建的 Observable 每次被订阅都发射了新的数据。

#### empty、error、never

- empty 创建的 Observable 不发送任何数据，一旦订阅直接发送 onComplete 事件；
- error 创建的 Observable 只会发送指定的异常；
- never 创建的 Observable 则更甚，onNext, onSuccess, onError, onComplete 均不会发送；

这几类操作符看上去都很特殊，通常使用在一些测试场景或者需要结束上游事件流的情况。

#### just 的局限性

Just 操作符创建 Observable 的形式如下
```Java
Observable.just(new View());
```
此时观察者还没有执行订阅操作，Observable 也没有发射数据，但是 just 中的语句 new View() 却已经执行了，相应的对象也生成了，just 的这种局限性使其只能创建同步的 Observable，不适合封装类似网络数据的异步操作，若要封装一些异步操作则需要使用 create, fromCallable, defer 等操作符。

### Transformation

变换类操作符用来对 Observable 发射的数据进行变换操作，此类操作符的使用也稍显抽象和复杂，将会主次分明地逐一介绍。

常见的变换类操作符如下：
- map
- flatMap
- concatMap
- flatMapIterable
- switchMap
- scan
- groupBy
- buffer
- window
- cast

#### map

map 操作符的作用是对 Observable 发射出来的 item 进行变换操作，变换的规则由我们传入的函数来指定。如果使用过其他函数式的编程语言或者 Java8 的 Stream 库，则应该对 map 操作符的用法不陌生。它也是我们在实际应用中使用频率较高的操作符之一。

一个简单的示例如下，我们使用 just 操作符创建了一个发射若干整数的 Observable，经过 map 操作符的变换后，这些被发射的整数值均翻了倍。map 操作符返回了一个新的 Observable，因此可以进行链式的调用。

```Java
Observable.just(1, 2, 3)
    .map(v -> v * 2)
    .doOnNext(System.out::println)
    .subscribe();

```

上面也提到了，map 操作符会返回一个新的 Observable，因此我们在进行变换操作时应该尽量在 map 的变换逻辑中一次性实现变换，而应该尽量避免如下形式的连续多次变换，因为产生过多的 Observable 对象也会对程序性能产生影响。

```Java
Observable.just(1, 2, 3)
    .map(v -> v * 2)
    .map(v2 -> String.valueOf(v2))
    .map(...)
    .map(...)
    .doOnNext(System.out::println)
    .subscribe();
```

实际上，所有的操作符在使用时都同理，实现同一段逻辑，应该用最合理的、尽量少的操作符来完成，应该避免上述形式的滥用。

#### flatMap
flatMap 也是对 Observable 发射的 item 进行变换操作，但其中的变换流程相较于 map 稍显复杂，其变换的流程描述如下：
- 首先，flatMap 将原始 Observable 发射的 item 映射为一个个新的 Observable；
- 然后，经过 flatMap 的变换操作之后，这些一个个新的 Observable 像是被压扁了似的，被映射成了一个新的、单独的 Observable

flatMap做的事情可以简单概括为如下流程：
> Observable_1 's **items** -> **Observables** -> Observable_2

> 注意区分单复数形式。

一个简单的示例如下:
```Java
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
```
该示例中，原始的 Observable 发射了三个 item，每个 item 都是一个 ArrayList；然后，在 flatMap 的变换函数中，使用 fromIterable 操作符把每一个 ArrayList 都映射成了一个新的 Observable，只此为止，原始的三个 item 将会被映射成三个新的 Observable；最后，经过 flatMap 的 "flat" 操作，这三个新的 Observable 会被“压扁”为一个新的、单独的 Observable。

整个变换的流程如下：
```
{[1,2,3],[4,5,6],[7,8,9]} -> {1,2,3},{4,5,6},{7,8,9} -> {1,2,3,4,5,6,7,8,9}
```

> 上述代码中，flatMap 的实现部分我们没有使用 Lambda 表达式，是为了更清晰地阅读出变换函数 Function 的入参与返回值，以便更好地理解 flatMap 的变换过程。

在实际应用中，我们常常将 flatMap 操作符用在 Android 客户端的异步网络嵌套场景中；之所以将 flatMap 用于这种场景主要原因就在于其能够将一个 Observable 变换为另一个 Observable 的特性。

#### flatMapIterable

flatMapIterable 与 flatMap 很类似，其转换的过程都是将一个 Observable 变换为了另外一个 Observable，不同之处在于中间变换的过程。flatMapIterable 将原始 Observable 发射的 item 转化为 Iterable，然后再将 Iterable “压扁”为一个新的、单独的 Observable。

flatMapIterable 做的事情可以简单概括为如下流程：
> Observable_1 's **items** -> **Iterables** -> Observable_2

> 注意区分单复数形式。

```Java
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
    .subscribe()
```

#### concatMap

concatMap 的功能与 flatMap 非常类似，差别在于“将多个 Observable ‘flat’ 为单个 Observable 的过程”。concatMap 将这多个 Observable 压扁的过程是按照顺序将所有的 items 进行拼接(concat)；而 flatMap 则无法保证这一点，flatMap 将所有的 items 进行归并(merge)，因此无法保证顺利，会发生错乱。通过下文的例子可以更加清晰地理解这一点。

首先，来演示 flatMap 是如何引起所谓“错乱”的：

```Java
/**
* 打印结果为：
* 20 21 30 31 10 11
*/
Observable
    .just(10, 20, 30)
    .flatMap(integer -> {
        long delay = 0;
        if (integer == 10) delay = 2000;
        return Observable.just(integer, integer + 1)
            .delay(delay, TimeUnit.MILLISECONDS);
    })
    .map((integer) -> integer + " ")
    .doOnNext(System.out::println)
    .subscribe();
```

该例子中，原始 Observable 发送的 item 10 映射出来的 Observable 会产生一个 2 秒的延迟，而其他两个 Observable 则会正常地、无延迟地发射，flatMap 在将这三个新的 Observable 压扁的时候，便无法保证所有新的 items 的先后顺序了，通过运行结果可以看到，延迟发送的 item 拼接到了顺序发送的 item 的后面。

而如果使用 concatMap，则可以保证新的 items 依然按照原序被拼接起来：
```Java
/**
* 打印结果为：
* 10 11 20 21 30 31
*/
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
```
#### switchmap

与 flatMap, concatMap 类似，switchMap 执行的操作也是将原始 Observable 转换成多个 Observables，然后再将多个 Observables 压扁为一个新的 Observable，差别在于，swtichMap 在“压扁”的过程中，对这多个 Observable 的顺序也是有“感知”的，当有一个新的 Observable 已经开始发射数据了，那么就会忽略掉前一个 Observable 的发射过程，而开始使用较新的这个 Observable 发射的数据。因此，switchMap 会造成数据的遗漏。参考下面的例子：

```Java
/**
* 打印结果为：
* 20 21 30 31
*/
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
```
在上面的示例中，原始 Observable 发送了三个 items，因此会映射成三个对应的 Observable，item 10 对应的 Observable 会延迟 300 ms 发射，item 20 对应 Observable 没有延迟直接发射，而 item 30 对应的 Observable 会延迟 150 ms 发射；由于本来排序在第一位的 Observable-item-10 被延迟到了最后，因此其发射的数据被忽略了，并没有被“压扁”进最终的 Observable 中。

#### 其他

groupBy, scan, buffer, window, cast 操作符比较好理解，建议参考官方文档以及本文配套项目中的 [demo](https://github.com/coreycao/AndroidDemo/blob/master/HelloAgera/app/src/main/java/me/demo/rx/operater_test/Test2_Transformation.java) 进行理解。

### Filtering

过滤类的操作符主要作用是对 Observable 发射的 items 进行选择和过滤。

RxJava 提供的常见过滤类操作符如下：
- filter
- take, takeLast
- skip, skipLast
- first, last
- elementAt, firstElement
- throttleFirst, throttleLatest
- sample, throttleLast
- debounce, throttleWithTimeout
- timeout
- distinct, distinctUntilChanged
- ofType
- ignoreElements

take, skip 等简单的操作符的功能不再赘述，可以参考官方的文档，此处仅介绍一些稍难理解的操作符。

#### filter

filter 操作符是最基本、也较灵活的过滤类操作符，我们可以在 Function 中自己实现灵活、复杂的逻辑，把不符合要求的数据过滤掉。

基本的使用示例如下：
```Java
Observable.just(1, 2, 3, 4, 5)
    .filter((integer) -> integer % 2 == 0)
    .doOnNext(System.out::println)
    .subscribe();
```

#### debounce/throttleWithTimeout

> throttleWithTimeout 是 debounce 的别名，二者是等价的。

debounce 操作符的作用是，针对源 Observable，在发射了某一个 item 之后，若规定的时间段内没有产生新的 item，则将该 item 发送给下游的观察者处理。该规则适用于所有发射出来的 item。上述描述显然是一个稍微绕口的递归性质的定义，那么便需要一个递归的出口：若最后一个 item 发送之后，紧接着在规定的时间段内 onComplete 被触发，那么这最后一个 item 也会被发送到下游。

示例如下：
```Java
Observable.interval(500, TimeUnit.MILLISECONDS,Schedulers.trampoline())
    .debounce(400, TimeUnit.MILLISECONDS)
    .doOnNext(System.out::println)
    .doOnComplete(() -> System.out.println("onComplete"))
    .subscribe();
```
interval 发射间隔为 500 ms，若 debounce timeout 大于等于 500 ms，那么不会有元素被打印出来，反之则全部能够被打印出来。

#### sample/throttleLast

> throttleLast 是 sample 的别名，二者是等价的。

sample 在这里取“采样”之中文释义。它的功能是对 Observable 发射的 items 序列进行定期的扫描，并对指定时间间隔内的 items 进行采样，选取该间隔内的最后一个 item 并发送给下游的观察者。

示例如下：
```Java
Observable.interval(500, TimeUnit.MILLISECONDS)
    .sample(1, TimeUnit.SECONDS)
    .doOnNext(System.out::println)
    .doOnComplete(() -> System.out.println("onComplete"))
    .subscribe();
```

上游的 Observable 每 500 ms 发射一个 item，sample 的周期为 1 s，因此经过采样后会获取到每个周期内最后发送的 item。

#### throttleFirst

与 sample 相反，throttleFirst 则是取指定时间间隔内的第一个 item。

示例如下：
```Java
Observable.interval(500, TimeUnit.MILLISECONDS, Schedulers.trampoline())
    .throttleFirst(1, TimeUnit.SECONDS)
    .doOnNext(System.out::println)
    .doOnComplete(() -> System.out.println("onComplete"))
    .subscribe();
```

#### ignoreElements

ignoreElements 正如其名，会忽略上游发射的所有数据，当发射结束后，下游仍会接受到 onComplete 事件。

## 其他使用要点
- RxJava2 中共有五种类型的观察者，每种观察者支持的操作符类型是有差别的，在使用过程中应该注意当前操作符返回的观察者类型。
- 每个操作符都会生成一个新的 Observable，所以在链式调用中应该尽量避免使用冗余的操作符
- 冷、热的区分
- 操作符都有其默认执行的 Scheduler，在使用时需时刻注意 
- 对于这上百个操作符，没有必要去记忆所有的操作符及其对应的功能，但是至少需要针对每一个操作符的基本功能和使用过一遍，待遇到合适的使用场景时能够想起有相应的操作符解决对应的问题，就像我们使用各种 SDK 的 API 一样。