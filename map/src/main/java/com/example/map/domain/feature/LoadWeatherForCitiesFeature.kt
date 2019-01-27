package com.example.map.domain.feature

import com.example.core.model.City
import com.example.core.model.Weather
import com.example.core.mvi.feature.SimpleStatefulAsyncFeature
import com.example.core.mvi.news.ErrorOnlyNews
import com.example.core.mvi.state.AsyncState
import com.example.map.domain.repo.IMapRepository
import com.example.map.model.CityWeather
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith


class LoadWeatherForCitiesFeature(
        repository: IMapRepository,
        transformer: ObservableTransformer<SimpleStatefulAsyncFeature.Effect, SimpleStatefulAsyncFeature.Effect>
) : SimpleStatefulAsyncFeature<List<City>, List<CityWeather>, List<CityWeather>, ErrorOnlyNews>(
        initialState = AsyncState(false, emptyList()),
        actor = SimpleStatefulAsyncFeature.ActorImpl(transformer) { previousState ->
            val input = this
            val citiesToLoad = filter { !previousState.value.map { cityWeather -> cityWeather.city }.contains(it) }

            if (citiesToLoad.isEmpty()) {
                Observable.just(SimpleStatefulAsyncFeature.Effect.Loaded(emptyList<CityWeather>()))
            } else {
                Single.zip(map {
                    repository.loadWeather(it.lat, it.lon).zipWith(Single.just(it))
                }) { results ->
                    results.map {
                        @Suppress("UNCHECKED_CAST")
                        it as Pair<Weather, City>
                    }.map { (weather, city) -> CityWeather(city, weather) }.toList()
                }
                        .toObservable()
                        .map { SimpleStatefulAsyncFeature.Effect.Loaded(it) }
            }
        },
        reducer = SimpleStatefulAsyncFeature.ReducerImpl { this },
        newsPublisher = SimpleStatefulAsyncFeature.ErrorNewsPublisher()
)

