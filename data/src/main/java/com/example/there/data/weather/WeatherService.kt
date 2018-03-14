package com.example.there.data.weather

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("forecast/{key}/{position}")
    fun loadWeather(@Path("key") key: String = "ff080b8072e357fdf21af5cb46593da3",
                    @Path("position") position: String): Observable<WeatherEntity>
}