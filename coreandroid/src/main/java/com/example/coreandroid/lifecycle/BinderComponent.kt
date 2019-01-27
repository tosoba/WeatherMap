package com.example.coreandroid.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.badoo.mvicore.binder.Binder

class BinderComponent : LifecycleObserver {

    private val binder: Binder by lazy { Binder() }

    fun setup(block: Binder.() -> Unit) = binder.block()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (!binder.isDisposed) binder.dispose()
    }
}