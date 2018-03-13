package com.example.there.weathermap.map

import com.example.there.domain.city.CityInteractor
import dagger.Module
import dagger.Provides

@Module
class MapModule {
    @Provides
    fun mapViewModelFactory(interactor: CityInteractor) = MapViewModelFactory(interactor)
}