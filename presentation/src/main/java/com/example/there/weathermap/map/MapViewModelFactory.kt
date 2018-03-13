package com.example.there.weathermap.map

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.there.domain.city.CityInteractor

class MapViewModelFactory(private val interactor: CityInteractor): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(interactor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")    }
}