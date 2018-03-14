package com.example.there.data.weather

data class WeatherEntity(
        val latitude: Double,
        val longitude: Double,
        val timezone: String,
        val currently: WeatherCurrentlyEntity
)