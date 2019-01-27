package com.example.map.ui

import android.os.Bundle
import com.badoo.mvicore.binder.Binder
import com.badoo.mvicore.binder.using
import com.example.core.model.City
import com.example.core.model.Weather
import com.example.core.mvi.state.AsyncState
import com.example.coreandroid.ui.BaseMviActivity
import com.example.map.R
import com.example.map.ext.latLngBounds
import com.example.map.ext.setWeather
import com.example.map.model.CityWeather
import com.example.map.ui.mvi.MapUiEvent
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MapActivity : BaseMviActivity<MapUiEvent>(
        R.layout.activity_map
) {
    private var map: GoogleMap? = null

    private val currentMarkers = HashMap<City, Marker>()

    private val mapInitialized: PublishSubject<Unit> = PublishSubject.create()

//    @Inject
//    lateinit var bindings: MapBindings

    private val binder: Binder by lazy { Binder() }

    @Inject
    lateinit var viewModel: MapViewModel

    //TODO: make this work somehow
//    private val connectivityComponent: ConnectivityComponent by lazy {
//        ConnectivityComponent(this, currentMarkers.size == 5, main_root_layout) {
//            map?.clear()
//            findCitiesInBounds()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        bindings.setup(this)
        binder.bind(this to viewModel.findCitiesInBoundsFeature using MapUiEvent.FindCitiesTransformer)
        binder.bind(this to viewModel.loadWeatherForCitiesFeature using MapUiEvent.LoadWeatherTransformer)
        binder.bind(viewModel.findCitiesInBoundsFeature to citiesStateConsumer)
        binder.bind(viewModel.loadWeatherForCitiesFeature to citiesWeatherStateConsumer)
        initMap()
//        lifecycle.addObserver(connectivityComponent)
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            mapInitialized.onComplete()

            events.onNext(MapUiEvent.OnMapCameraMove(googleMap.latLngBounds))

            googleMap.setOnCameraIdleListener {
                events.onNext(MapUiEvent.OnMapCameraMove(googleMap.latLngBounds))
            }
        }
    }

    val citiesStateConsumer: CitiesStateConsumer by lazy { CitiesStateConsumer() }

    inner class CitiesStateConsumer : Consumer<AsyncState<List<City>>> {
        override fun accept(citiesState: AsyncState<List<City>>?) {
            citiesState?.let { state ->
                //TODO: dispose this
                Observable.just(state.value).delaySubscription(mapInitialized).subscribe { newCities ->
                    val keysToRemove = currentMarkers.keys.filter { !newCities.contains(it) }
                    keysToRemove.forEach {
                        currentMarkers[it]?.remove()
                        currentMarkers.remove(it)
                    }
                    newCities.forEach { city ->
                        if (!currentMarkers.keys.contains(city)) {
                            val position = LatLng(city.lat, city.lon)
                            map?.addMarker(MarkerOptions()
                                    .position(position)
                                    .title(city.name)
                            )?.apply {
                                tag = city.markerTag
                                setWeather(Weather.loading, resources)
                                currentMarkers[city] = this
                            }
                        }
                    }
                    events.onNext(MapUiEvent.OnCitiesLoaded(newCities))
                }
            }
        }
    }

    val citiesWeatherStateConsumer: CitiesWeatherStateConsumer by lazy { CitiesWeatherStateConsumer() }

    inner class CitiesWeatherStateConsumer : Consumer<AsyncState<List<CityWeather>>> {
        override fun accept(citiesWeatherState: AsyncState<List<CityWeather>>?) {
            citiesWeatherState?.value?.forEach { cityWeather ->
                currentMarkers[cityWeather.city]?.setWeather(cityWeather.weather, resources)
            }
        }
    }
}