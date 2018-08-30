package com.example.there.weathermap.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.there.domain.city.City
import com.example.there.domain.weather.Weather
import com.example.there.weathermap.R
import com.example.there.weathermap.di.vm.ViewModelFactory
import com.example.there.weathermap.util.setWeather
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.AndroidInjection
import javax.inject.Inject


class MapActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MapViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)
    }

    private var map: GoogleMap? = null

    private val currentMarkers = ArrayList<MapMarker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupObservers()
        initMap(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArray(KEY_CURRENT_CITIES, currentMarkers.map { it.city }.toTypedArray())
    }

    private fun initMap(savedInstanceState: Bundle?) {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it

            findCitiesInBounds()

            map?.setOnCameraMoveListener {
                findCitiesInBounds()
            }

            savedInstanceState?.let {
                val cities = it.getParcelableArray(KEY_CURRENT_CITIES).map { it as City }.toList()
                updateMarkers(cities)
            }
        }
    }

    private fun setupObservers() {
        viewModel.citiesInCurrentBounds.observe(this, Observer { newCities ->
            if (newCities != null && map != null) {
                updateMarkers(newCities)
            }
        })
    }

    private fun updateMarkers(newCities: List<City>) {
        val markersToRemove = currentMarkers.filter { !newCities.contains(it.city) }
        markersToRemove.map { it.marker.remove() }
        currentMarkers.removeAll(markersToRemove)
        newCities.forEach { city ->
            if (!currentMarkers.map { it.city }.contains(city)) {
                val position = LatLng(city.lat, city.lon)
                val marker = map?.addMarker(MarkerOptions()
                        .position(position)
                        .title(city.name))
                        ?.apply { setWeather(Weather.loading, resources) }
                viewModel.loadWeather(marker!!, { weather, mark -> mark.setWeather(weather, resources) })
                currentMarkers.add(MapMarker(city, marker))
            }
        }
    }

    private fun findCitiesInBounds() {
        val currentBounds = map?.projection?.visibleRegion?.latLngBounds
        currentBounds?.let { viewModel.findCitiesInBounds(it) }
    }

    companion object {
        private const val KEY_CURRENT_CITIES = "KEY_CURRENT_CITIES"
    }
}
