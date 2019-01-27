package com.example.map.ui

import android.os.Bundle
import com.badoo.mvicore.binder.Binder
import com.badoo.mvicore.binder.using
import com.example.core.model.City
import com.example.core.model.Weather
import com.example.core.mvi.state.AsyncState
import com.example.coreandroid.ext.disposeWith
import com.example.coreandroid.lifecycle.ConnectivityComponent
import com.example.coreandroid.lifecycle.DisposablesComponent
import com.example.coreandroid.ui.BaseMviActivity
import com.example.map.R
import com.example.map.ext.latLngBounds
import com.example.map.ext.setWeather
import com.example.map.model.CityWeather
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_map.*
import javax.inject.Inject


class MapActivity : BaseMviActivity<MapUiEvent>(
        R.layout.activity_map
) {
    private var map: GoogleMap? = null
    private val currentMarkers = HashMap<City, Marker>()
    private val mapInitialized: PublishSubject<Unit> = PublishSubject.create()

    @Inject
    lateinit var viewModel: MapViewModel

    private val connectivityComponent: ConnectivityComponent by lazy(LazyThreadSafetyMode.NONE) {
        ConnectivityComponent(this, currentMarkers.size == 5, main_root_layout) {
            map?.let {
                it.clear()
                events.onNext(MapUiEvent.OnMapCameraMove(it.latLngBounds))
            }
        }
    }

    private val disposablesComponent: DisposablesComponent by lazy(LazyThreadSafetyMode.NONE) {
        DisposablesComponent()
    }

    private val citiesStateConsumer: CitiesStateConsumer by lazy(LazyThreadSafetyMode.NONE) {
        CitiesStateConsumer()
    }

    private val citiesWeatherStateConsumer: CitiesWeatherStateConsumer by lazy(LazyThreadSafetyMode.NONE) {
        CitiesWeatherStateConsumer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initMap()

        lifecycle.addObserver(connectivityComponent)
        lifecycle.addObserver(disposablesComponent)
    }

    override fun Binder.setup() {
        bind(this@MapActivity to viewModel.findCitiesInBoundsFeature using MapUiEvent.FindCitiesTransformer)
        bind(this@MapActivity to viewModel.loadWeatherForCitiesFeature using MapUiEvent.LoadWeatherTransformer)
        bind(viewModel.findCitiesInBoundsFeature to citiesStateConsumer)
        bind(viewModel.loadWeatherForCitiesFeature to citiesWeatherStateConsumer)
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

    inner class CitiesStateConsumer : Consumer<AsyncState<List<City>>> {
        override fun accept(citiesState: AsyncState<List<City>>?) {
            citiesState?.let { state ->
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
                                setWeather(Weather.loading, resources)
                                currentMarkers[city] = this
                            }
                        }
                    }

                    if (connectivityComponent.lastConnectionStatus)
                        events.onNext(MapUiEvent.OnCitiesLoaded(newCities))
                }.disposeWith(disposablesComponent)
            }
        }
    }


    inner class CitiesWeatherStateConsumer : Consumer<AsyncState<List<CityWeather>>> {
        override fun accept(citiesWeatherState: AsyncState<List<CityWeather>>?) {
            citiesWeatherState?.value?.forEach { cityWeather ->
                currentMarkers[cityWeather.city]?.setWeather(cityWeather.weather, resources)
            }
        }
    }
}