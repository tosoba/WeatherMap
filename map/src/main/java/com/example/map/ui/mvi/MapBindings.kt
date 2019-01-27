package com.example.map.ui.mvi

import com.badoo.mvicore.android.AndroidBindings
import com.badoo.mvicore.binder.using
import com.example.core.di.ActivityScope
import com.example.map.ui.MapActivity
import com.example.map.ui.MapViewModel
import javax.inject.Inject

@ActivityScope
class MapBindings @Inject constructor(
        view: MapActivity,
        private val viewModel: MapViewModel
) : AndroidBindings<MapActivity>(view) {

    override fun setup(view: MapActivity) {
        binder.bind(view to viewModel.findCitiesInBoundsFeature using MapUiEvent.FindCitiesTransformer)
        binder.bind(viewModel.findCitiesInBoundsFeature to view.citiesStateConsumer)
        binder.bind(viewModel.loadWeatherForCitiesFeature to view.citiesWeatherStateConsumer)
    }
}