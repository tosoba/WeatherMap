package com.example.there.weathermap.di.module

import com.example.there.weathermap.map.MapActivity
import com.example.there.weathermap.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun bindMapActivity(): MapActivity

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity
}