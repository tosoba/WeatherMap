package com.example.map.model

import com.example.core.model.City
import com.example.core.model.Weather
import com.google.android.gms.maps.model.Marker

data class CityMarker(val city: City, val marker: Marker, var isLoaded: Boolean = false)

data class CityWeather(
        val city: City,
        val weather: Weather
)