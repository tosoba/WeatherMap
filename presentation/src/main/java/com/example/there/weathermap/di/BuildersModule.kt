package com.example.there.weathermap.di

import com.example.there.weathermap.map.MapActivity
import com.example.there.weathermap.map.MapModule
import com.example.there.weathermap.splash.SplashActivity
import com.example.there.weathermap.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector(modules = [MapModule::class])
    abstract fun bindMapActivity(): MapActivity

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun bindSplashActivity(): SplashActivity
}