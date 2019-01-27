package com.example.splash.ui

import android.os.Bundle
import com.badoo.mvicore.binder.Binder
import com.badoo.mvicore.binder.named
import com.badoo.mvicore.binder.using
import com.example.core.mvi.feature.SimpleStatelessAsyncFeature
import com.example.coreandroid.routing.Router
import com.example.coreandroid.ui.BaseMviActivity
import com.example.splash.R
import io.reactivex.functions.Consumer
import javax.inject.Inject


class SplashActivity : BaseMviActivity<SplashUiEvent>(
        R.layout.activity_splash
), Consumer<SimpleStatelessAsyncFeature.News> {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            events.onNext(SplashUiEvent.OnCreate(resources.openRawResource(R.raw.city_data)))
    }

    override fun Binder.setup() {
        bind(this@SplashActivity to viewModel.loadCitiesDataFeature using SplashUiEvent.Transformer named "SplashActivity.Events")
        bind(viewModel.loadCitiesDataFeature.news to this@SplashActivity named "SplashActivity.News")
    }

    //TODO: maybe extract that into a listener class and inject SplashActivity as SplashContract.View there with fun showMap()
    override fun accept(news: SimpleStatelessAsyncFeature.News?) {
        when (news) {
            is SimpleStatelessAsyncFeature.News.Success -> router.showMap(this)
            is SimpleStatelessAsyncFeature.News.Error -> {

            }
            else -> {

            }
        }
    }
}
