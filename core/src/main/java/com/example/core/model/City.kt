package com.example.core.model

data class City(
        val lat: Double,
        val lon: Double,
        val name: String,
        val population: Int
) {
    val markerTag: String
        get() = "$name$lat$lon"
}