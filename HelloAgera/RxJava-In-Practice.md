# RxJava & RxAndroid 在 Android 中的一些常见应用场景

## Ref: 
1. https://appkfz.com/2017/09/01/best-scene-use-rxjava/
2. https://medium.com/devnibbles/rxjava-the-first-3-patterns-4c112a85b689
3. https://gank.io/post/560e15be2dca930e00da1083#toc_31

## Intro

本文演示 RxJava 在 Android 开发中一些常用场景，这些场景均涉及异步网络请求，具体场景如下：
- 单请求异步调用，使用 Scheduler 方便地实现线程切换
- 使用 Zip 操作符实现多异步请求合并处理
- 使用 FlatMap 实现多异步请求连续调用
- 使用 interval 实现轮询
- 不使用 Retrofit，将传统的回调式网络请求包装为 Observable
- 使用 RxLifeCycle 控制事件流的生命周期

本文并不会介绍 RxJava 以及相关工具类库的基本使用，因此需要了解如下这些前序知识：
- OkHttp & Retrofit 的基本使用
- RxJava 的基本使用
- Lambda 表达式

本文涉及 Demo 的仓库地址为：https://github.com/coreycao/AndroidDemo/tree/master/HelloAgera

## Demo 项目的基本配置和依赖

首先，在 gradle 中做如下配置，以支持 Java8 的 Lambda 表达式等特性
```
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
```

其次，添加如下依赖
```
  implementation 'com.squareup.okhttp3:okhttp:3.10.0'

  implementation 'com.google.code.gson:gson:2.8.2'

  implementation 'com.squareup.retrofit2:retrofit:2.4.0'

  implementation 'com.squareup.retrofit2:converter-gson:2.4.0'

  implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'

  implementation 'io.reactivex.rxjava2:rxjava:2.2.0'

  implementation 'com.squareup.retrofit2:adapter-rxjava2:2.4.0'

  implementation 'com.squareup.okhttp3:logging-interceptor:3.9.0'

  implementation 'com.trello.rxlifecycle2:rxlifecycle-android-lifecycle:2.2.2'
```

最后，所有的网络请求都基于如下接口来演示
```java
  @GET("article/list/{page}/json") Observable<Article> getArticle(@Path("page") int page);
```
- BaseUrl 是 http://www.wanandroid.com/
- getArticle 接口返回一个 Observable<Article> 对象
- 其中 Article 的 data 字段中包含一个文章列表数据，文章有标题、作者字段信息。

## Demo 的具体实现

### 单请求异步调用，使用 Scheduler 方便地实现线程切换

Android 开发中需要进行大量的异步操作，即需要在非 UI 线程中进行网络、数据库等 IO 类的耗时操作，然后在 UI 线程中进行相应的 UI 更新操作。这就涉及到了不同线程间的消息通信及任务的线程切换，Android 提供了 Handler 机制以及在此基础上的 HandlerThread、IntentServie、AsyncTask 等工具使我们可以方便地实现线程切换的需求。

Android 提供的这些机制和工具已经能够完成我们项目开发中的需求，但是在代码可读性、可维护性方面依旧没有 RxJava 来得清晰。使用 RxJava 配合 Retrofit 能够轻松实现异步处理和线程切换等复杂需求，并且随着逻辑的复杂性增加，依旧能够保持代码的可读性和可维护性。

下述代码实现了一个最基本的需求：请求接口 -> loading -> 将获取的数据进行基本的处理 -> 隐藏 loading -> 在列表中显示数据，若请求数据的过程中发生异常则会隐藏 loading 并弹出 toast 提示。

```Java
  final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);
  disposable = service.getArticle(1)
      .subscribeOn(Schedulers.io())
      // map操作将 Article 对象映射成 List<String>, 即文章的标题列表
      .map(article -> {
        List<String> titles = new ArrayList<>();
        for (Article.DataBean.DatasBean bean : article.getData().getDatas()) {
          titles.add(bean.getTitle());
        }
        return titles;
      })
      // 将订阅者任务执行的线程切换到 Android UI 线程
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe((disposableDoOnSub) -> {
        Log.d(TAG, "doOnSubscribe...");
        showLoading();
      })
      .doOnNext(this::showList)
      .doOnError(throwable -> {
            Log.e(TAG, "doOnError...");
            showErrorMessage();
            hideLoading();
          }
      )
      .subscribe();
```
完整的代码请参考 RxjActivity1。
可见，一条链式地调用下来，便逻辑清晰地实现了数据请求、数据处理、线程切换、UI 逻辑、异常处理等需求。

### 使用 Zip 操作符实现多异步请求合并处理

我们在 Android 开发中经常会有这样的需求，即同时发出多个网络请求，这几个网络请求之间也没有什么依赖关系，但是需要这几个网络请求均返回结果、并对结果进行处理后再进行接下来的操作。因为涉及到了多线程，因此若不使用 RxJava 来实现，则需要使用繁琐的线程同步和锁机制来实现这样的需求。

RxJava 提供了丰富的操作符来对 Observable 进行各种处理，这些处理包括 Observable 的创建、变换、过滤等。应用这些操作符，能够以响应式编程的思想实现很多 Android 开发中的异步场景。其中，RxJava 提供的 zip 操作符能够将不同的 Observable 组合成一个新的 Observable，我们可以使用 zip 操作符轻松实现上述的“多异步请求合并处理”的需求。

使用 Zip 操作符实现多异步请求合并处理的实例代码如下：
```Java
  /**
   * 两个请求同时发出，分别获取第一页和第二页的数据，只有两个请求都成功后才进行下一步的操作
   */
  private void fetchData() {
    final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);
    Observable<List<String>> source1 = service.getArticle(1)
        .map(this::mapping2TitleList);
    Observable<List<String>> source2 = service.getArticle(2)
        .map(this::mapping2TitleList);

    disposable = Observable.zip(source1, source2, this::combine)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::showList, throwable ->
            Log.e(TAG, "doOnError...")
        );
  }

  /**
   * 将两页数据合并
   */
  private List<String> combine(List<String> o, List<String> o2) {
    o.addAll(o2);
    return o;
  }

  /**
   * 将 article 映射成标题列表
   */
  private List<String> mapping2TitleList(Article article) {
    List<String> titles = new ArrayList<>();
    for (Article.DataBean.DatasBean bean : article.getData().getDatas()) {
      titles.add(bean.getTitle());
    }
    return titles;
  }
```
完整的代码请参考 RxjActivity2。


### 使用 FlatMap 实现多异步请求连续调用

多异步请求连续调用的场景在客户端开发中的场景也很多，如
- token鉴权：请求获取token -> 使用该token进一步去请求其他一些接口
- 上传头像：从本地存储中解析图片 -> 将该图片上传 -> 上传成功后更新用户信息
- 等等...
这些场景中，多个异步的网络请求之间有前后的依赖关系，因此若用最基本的 Java 代码来实现，则避免不了要写出复杂的多层 callback 层层嵌套的代码，代码冗余且不易于维护。使用 RxJava 则逻辑清晰，基本的示例如下：

```Java
  /**
   * 先请求第一页数据，待成功后再请求第二页数据，将两页数据合并成新的 Observable 发送给下游
   */
  private void fetchData() {
    final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);
    disposable = service.getArticle(1)
        .map(this::mapping2TitleList)
        .flatMap(strings ->
            service.getArticle(2)
                .map(article -> {
                  strings.addAll(RxjActivity3.this.mapping2TitleList(article));
                  return strings;
                })
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::showList, throwable ->
            Log.e(TAG, "doOnError...")
        );
  }
```

### 使用 interval 实现轮询

Android 开发中还有许多场景需要我们轮询地去进行一个异步操作，比如在一些二维码支付的页面，需要轮询地去请求一个接口来判断用户是否支付成功。我们可以使用 Handler#postDelay 来实现轮询的操作，当然用 RxJava 亦可以简洁明了地实现轮询操作。

RxJava 中的 IntervalXXX 系列操作符能够按照我们设定的参数周期性地发射数据，利用这样的数据流便可以实现轮询操作，代码示例如下：
```Java
  private void fetchData() {
    final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);
    disposable = Observable.intervalRange(1, 5, 0, 3, TimeUnit.SECONDS)
        .flatMap(aLong -> service.getArticle(aLong.intValue()))
        .map(this::mapping2TitleList)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(this::showList)
        .doOnError(throwable -> Log.e(TAG, "doOnError.."))
        .subscribe();
  }
```
此处用到了 intervalRange 操作符，传入的各项参数的意义为：以3秒为间隔，周期地发送数字1 - 5，且首次发送没有延迟。具体的使用可以参考相关文档。
完整代码请参考 RxjActivity4

### 不使用 Retrofit，将传统的回调式网络请求包装为 Observable

上述的网络请求均使用了 Retrofit 这个工具来实现，Retrofit 返回的结果是包装好的 Observable，因此上述示例中均没有涉及到 Observable 的创建。而在实际应用场景中，我们是可以自行根据需求创建 Observable 的。假设我们项目的网络请求没有使用 Retrofit，那么则可以遵循如下示例，将传统的回调式网络请求改造成返回 Obserable 的接口以适用于 RxJava 链式调用。

假设我们现在有如下回调式的网路请求：
```Java
public class ArticleModel {
  interface Callback {
    void onSuccess(Article result);
    void onFailure(Throwable throwable);
  }

  /**
   * 传统的回调式网络请求
   */
  public void getArticleList(Callback callback) {
    // do network & callback#invoke
    ...
  }
}
```

那么可以通过使用 RxJava 的 create 操作符手动创建 Observable 将该 getArticleList 接口改造成如下形式：
```Java
  /**
   * 返回 Observable 的网络请求
   */
  public Observable<Article> getArticleList() {
    return Observable.create(emitter ->
        getArticleList(new Callback() {
          @Override public void onSuccess(Article result) {
            if (emitter.isDisposed()) return;
            emitter.onNext(result);
            emitter.onComplete();
          }

          @Override public void onFailure(Throwable throwable) {
            if (emitter.isDisposed()) return;
            emitter.onError(throwable);
          }
        })
    );
  }
```

经过以上改造，我们便可以像配合 Retrofit 一样方便地使用 RxJava 了：
```Java
  new ArticleModel().getArticleList()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe()
```

### 使用 RxLifeCycle 控制事件流的生命周期

使用 RxJava 进行异步操作，需要良好地控制事件流的生命周期，即及时地结束事件流的发送或者取消订阅，否则在客户端开发中很容易发生内存泄漏。

我们可以手动地对每一次订阅进行控制，在合适的时机取消订阅。例如上述几个示例中，每次调用 subscribe 函数实际上都返回了一个 Disposable 对象，我们可以使用这个 Disposable 对象在页面的 onDestroy 生命周期中来及时地取消订阅。

```Java
  @Override protected void onDestroy() {
    super.onDestroy();
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }
```

实际上，针对 RxJava 生命周期的控制，业界也已经有比较成熟的方案，如 [RxLifecycle](https://github.com/trello/RxLifecycle) 和 [AutoDispose](https://github.com/uber/AutoDispose)，二者都是开源项目，都实现了对 RxJava 生命周期的控制，而且特别适用于在 Android 客户端上使用，在实现的思想上也比较类似；不同之处在于，RxLifecycle 只是在合适的时机结束了事件流，并没有真正取消订阅，而后者则是真正地取消了相应的订阅。

下面的代码演示了 RxLifecycle 的基本使用：
```Java
  /**
   * 使用 RxLifeCycle 控制事件流的生命周期
   */
  private void fetchData() {

    final WebService service = RetrofitUtil.INSTANCE().create(WebService.class);

    service.getArticle(1)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(AndroidLifecycle.createLifecycleProvider(this).bindToLifecycle())
        .map(this::mapping2TitleList)
        .subscribe(this::showList,
            throwable -> Log.e(TAG,"onError...")
        );
  }

```
LifecycleProvider 可以有多种，这里选择了 Google 官方推出的架构组件 Lifecycle。

## Sum

本文主要演示了 RxJava 在 Android 开发中的一些网络异步操作的实现，这些场景有：
- 单请求异步调用，使用 Scheduler 方便地实现线程切换
- 使用 Zip 操作符实现多异步请求合并处理
- 使用 FlatMap 实现多异步请求连续调用
- 使用 interval 实现轮询
- 不使用 Retrofit，将传统的回调式网络请求包装为 Observable
- 使用 RxLifeCycle 控制事件流的生命周期
可见，使用 RxJava 能够方便地实现很多复杂的场景，于此同时还依旧能够保持逻辑的清晰和代码的可维护性；RxJava 处理异步操作虽然有内存泄漏的风险，但是已经有成熟的工具能够解决生命周期相关的问题；RxJava 不是仅仅能够配合 Retrofit 这样的工具库来使用，我们可以通过改造传统的回调式网络请求也能够脱离 Retrofit 来使用 RxJava，也可以自行创建 Observable 来将其应用到更多的场景中。

