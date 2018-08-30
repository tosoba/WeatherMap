package com.example.there.weathermap.di.module

import android.arch.lifecycle.ViewModel
import com.example.there.weathermap.di.vm.ViewModelKey
import com.example.there.weathermap.map.MapViewModel
import com.example.there.weathermap.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun mainViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel
}