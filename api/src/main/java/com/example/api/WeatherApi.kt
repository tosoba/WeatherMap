package com.example.api

import com.example.api.entity.WeatherEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

//TODO: check if api has to be an android library (or can it be converted to just a java module)
interface WeatherApi {
    @GET("forecast/{key}/{position}")
    fun loadWeather(
            @Path("key") key: String = Keys.WEATHER,
            @Path("position") position: String
    ): Single<WeatherEntity>
}