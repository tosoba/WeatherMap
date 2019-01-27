package com.example.map.domain.feature

import com.example.core.model.City
import com.example.core.mvi.feature.SimpleStatefulAsyncFeature
import com.example.core.mvi.news.ResultNews
import com.example.core.mvi.state.AsyncState
import com.example.map.domain.repo.IMapRepository
import com.google.android.gms.maps.model.LatLngBounds
import io.reactivex.ObservableTransformer


class FindCitiesInBoundsFeature(
        repository: IMapRepository,
        transformer: ObservableTransformer<SimpleStatefulAsyncFeature.Effect, SimpleStatefulAsyncFeature.Effect>
) : SimpleStatefulAsyncFeature<LatLngBounds, List<City>, List<City>, ResultNews>(
        initialState = AsyncState(false, emptyList()),
        actor = SimpleStatefulAsyncFeature.ActorImpl(transformer) { _ ->
            val minLat = southwest.latitude
            val maxLat = northeast.latitude
            val minLon = southwest.longitude
            val maxLon = northeast.longitude
            repository.findLargestCitiesInBounds(minLat, maxLat, minLon, maxLon)
                    .toObservable()
                    .map { SimpleStatefulAsyncFeature.Effect.Loaded(it) }
        },
        reducer = SimpleStatefulAsyncFeature.ReducerImpl { this },
        newsPublisher = SimpleStatefulAsyncFeature.AllResultsNewsPublisher()
)