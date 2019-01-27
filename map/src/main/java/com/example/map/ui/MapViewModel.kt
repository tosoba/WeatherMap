package com.example.map.ui

import android.arch.lifecycle.ViewModel
import com.example.map.domain.feature.FindCitiesInBoundsFeature
import com.example.map.domain.feature.LoadWeatherForCitiesFeature
import javax.inject.Inject

class MapViewModel @Inject constructor(
        val findCitiesInBoundsFeature: FindCitiesInBoundsFeature,
        val loadWeatherForCitiesFeature: LoadWeatherForCitiesFeature
) : ViewModel() {

    override fun onCleared() {
        findCitiesInBoundsFeature.dispose()
        loadWeatherForCitiesFeature.dispose()
        super.onCleared()
    }
}