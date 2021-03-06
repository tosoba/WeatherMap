package com.example.coreandroid.ui

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.badoo.mvicore.binder.Binder
import com.example.coreandroid.lifecycle.BinderComponent
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


abstract class BaseMviActivity<UiEvent : Any>(
        @LayoutRes protected val layoutResource: Int
) : AppCompatActivity(), UiEventSource<UiEvent>, HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    override val events: PublishSubject<UiEvent> = PublishSubject.create()

    private val binderComponent: BinderComponent by lazy(LazyThreadSafetyMode.NONE) {
        BinderComponent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        lifecycle.addObserver(binderComponent)
        binderComponent.setup { setup() }
    }

    abstract fun Binder.setup()
}