package com.example.coreandroid.ext

import com.example.coreandroid.lifecycle.DisposablesComponent
import io.reactivex.disposables.Disposable

fun Disposable.disposeWith(disposablesComponent: DisposablesComponent) {
    disposablesComponent += this
}