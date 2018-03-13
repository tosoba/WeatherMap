package com.example.there.weathermap.map

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.there.weathermap.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.AndroidInjection
import javax.inject.Inject

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: MapViewModelFactory

    private val viewModel: MapViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java) }

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupObservers()
        initMap()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        findCitiesInBounds()

        map?.setOnCameraMoveListener {
            findCitiesInBounds()
        }
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupObservers() {
        viewModel.citiesInCurrentBounds.observe(this, Observer {
            if (it != null && map != null) {
                map?.clear()
                it.forEach { map?.addMarker(MarkerOptions().position(LatLng(it.lat, it.lon)).title(it.name)) }
            }
        })
    }

    private fun findCitiesInBounds() {
        val currentBounds = map?.projection?.visibleRegion?.latLngBounds
        currentBounds?.let { viewModel.findCitiesInBounds(it) }
    }
}
