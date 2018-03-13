package com.example.there.weathermap.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {
    protected val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}