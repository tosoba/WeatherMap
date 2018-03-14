package com.example.there.weathermap.map

import com.example.there.domain.city.CityInteractor
import com.example.there.domain.weather.WeatherInteractor
import dagger.Module
import dagger.Provides

@Module
class MapModule {
    @Provides
    fun mapViewModelFactory(cityInteractor: CityInteractor, weatherInteractor: WeatherInteractor) = MapViewModelFactory(cityInteractor, weatherInteractor)
}