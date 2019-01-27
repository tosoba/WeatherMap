package com.example.coreandroid.ui

import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

interface UiEventSource<T> : ObservableSource<T> {

    val events: PublishSubject<T>

    fun onNext(t: T) = events.onNext(t)

    override fun subscribe(observer: Observer<in T>) = events.subscribe(observer)
}