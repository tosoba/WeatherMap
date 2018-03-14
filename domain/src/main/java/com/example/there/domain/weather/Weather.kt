package com.example.there.domain.weather

data class Weather(val temperature: Int, val icon: String) {
    companion object {
        val default: Weather = Weather(0, "default")
        val loading: Weather = Weather(0, "loading")
    }
}