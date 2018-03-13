package com.example.there.weathermap.splash

import com.example.there.domain.city.CityInteractor
import dagger.Module
import dagger.Provides

@Module
class SplashModule {
    @Provides
    fun splashViewModelFactory(interactor: CityInteractor) = SplashViewModelFactory(interactor)
}