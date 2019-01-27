package com.example.map.model

import com.example.core.model.City
import com.example.core.model.Weather

data class CityWeather(
        val city: City,
        val weather: Weather
)