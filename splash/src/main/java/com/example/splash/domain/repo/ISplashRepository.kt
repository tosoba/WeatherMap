package com.example.splash.domain.repo

import com.example.core.model.City
import io.reactivex.Completable
import io.reactivex.Single

interface ISplashRepository {
    val isCityDataImported: Single<Boolean>
    fun insertCities(cities: List<City>): Completable
}