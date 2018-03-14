package com.example.there.weathermap.map

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.there.domain.city.CityInteractor
import com.example.there.domain.weather.WeatherInteractor

class MapViewModelFactory(private val cityInteractor: CityInteractor,
                          private val weatherInteractor: WeatherInteractor): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(cityInteractor, weatherInteractor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }
}