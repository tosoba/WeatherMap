package com.example.there.weathermap.map

import com.example.there.domain.city.City
import com.google.android.gms.maps.model.Marker

data class MapMarker(val city: City, val marker: Marker, var isLoaded: Boolean = false)