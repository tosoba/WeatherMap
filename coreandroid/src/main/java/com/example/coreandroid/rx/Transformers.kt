package com.example.coreandroid.rx

import com.example.core.rx.SymmetricFlowableTransformer
import com.example.core.rx.SymmetricObservableTransformer
import com.example.core.rx.SymmetricSingleTransformer
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher

class AsyncSymmetricObservableTransformer<T> : SymmetricObservableTransformer<T>() {
    override fun apply(
            upstream: Observable<T>
    ): ObservableSource<T> = upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

class AsyncSymmetricFlowableTransformer<T> : SymmetricFlowableTransformer<T>() {
    override fun apply(
            upstream: Flowable<T>
    ): Publisher<T> = upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

class AsyncSymmetricSingleTransformer<T> : SymmetricSingleTransformer<T>() {
    override fun apply(
            upstream: Single<T>
    ): SingleSource<T> = upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

class AsyncCompletableTransformer : CompletableTransformer {
    override fun apply(
            upstream: Completable
    ): CompletableSource = upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}