package com.example.splash.di

import android.arch.lifecycle.ViewModelProviders
import com.example.coreandroid.di.ViewModelFactory
import com.example.splash.ui.SplashActivity
import com.example.splash.ui.SplashViewModel
import dagger.Module
import dagger.Provides

@Module
abstract class SplashModule {





    @Module
    companion object {
        @Provides
        @JvmStatic
        fun splashViewModel(
                factory: ViewModelFactory,
                activity: SplashActivity
        ): SplashViewModel = ViewModelProviders.of(activity, factory).get(SplashViewModel::class.java)


    }
}