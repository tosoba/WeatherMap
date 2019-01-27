package com.example.api.entity

data class WeatherEntity(
        val latitude: Double,
        val longitude: Double,
        val timezone: String,
        val currently: WeatherCurrentlyEntity
)