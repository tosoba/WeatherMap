package com.example.map.ui.mvi

import com.example.core.model.City
import com.example.core.mvi.wish.LoadAsyncWish
import com.google.android.gms.maps.model.LatLngBounds

sealed class MapUiEvent {
    class OnMapCameraMove(val bounds: LatLngBounds) : MapUiEvent()
    class OnCitiesLoaded(val cities: List<City>) : MapUiEvent()

    object FindCitiesTransformer : (MapUiEvent) -> LoadAsyncWish<LatLngBounds>? {
        override fun invoke(event: MapUiEvent): LoadAsyncWish<LatLngBounds>? = when (event) {
            is OnMapCameraMove -> LoadAsyncWish(event.bounds)
            else -> null
        }
    }

    object LoadWeatherTransformer : (MapUiEvent) -> LoadAsyncWish<List<City>>? {
        override fun invoke(event: MapUiEvent): LoadAsyncWish<List<City>>? = when (event) {
            is OnCitiesLoaded -> LoadAsyncWish(event.cities)
            else -> null
        }

    }
}