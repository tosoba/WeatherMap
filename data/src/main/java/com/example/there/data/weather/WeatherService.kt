package com.example.there.data.weather

import com.example.there.data.Keys
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("forecast/{key}/{position}")
    fun loadWeather(
            @Path("key") key: String = Keys.WEATHER,
            @Path("position") position: String
    ): Single<WeatherEntity>
}