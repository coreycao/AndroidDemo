package com.corey.demo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by sycao on 2017/8/24.
 *
 */
class SecondActivity : Activity() {

    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = verticalLayout {
            val title = textView()
            button("this is a button") {
                onClick {
                    title.text = "test"
                    getData()
                }
            }.lparams {
                width = matchParent
                verticalMargin = dip(5)
            }
        }

        setContentView(layout)
    }

    private fun getData() {
        val baseUrl = "https://api.douban.com/v2/"
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val movieService = retrofit.create(MovieService::class.java)

        disposable = movieService.getMovieSubjectById("1292052")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<MovieEntity.SubjectsBean>() {
                    override fun onError(e: Throwable) {
                        Log.d("rxKotlin", "onError")
                    }

                    override fun onComplete() {
                        Log.d("rxKotlin", "onComplete")
                    }

                    override fun onNext(t: MovieEntity.SubjectsBean) {
                        toast(applicationContext, t.id)
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}