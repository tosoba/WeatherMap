package com.example.splash.ui.mvi

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.named
import com.badoo.mvicore.binder.using
import com.example.core.di.ActivityScope
import com.example.splash.ui.SplashActivity
import com.example.splash.ui.SplashViewModel
import javax.inject.Inject

@ActivityScope
class SplashBindings @Inject constructor(
        view: SplashActivity,
        private val viewModel: SplashViewModel
) : AndroidBindings<SplashActivity>(view) {

    override fun setup(view: SplashActivity) {
        binder.bind(view to viewModel.loadCitiesDataFeature using SplashUiEvent.Transformer named "SplashActivity.Events")
        binder.bind(viewModel.loadCitiesDataFeature.news to view named "SplashActivity.News")
    }
}