package com.example.map.ext

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds

val GoogleMap.latLngBounds: LatLngBounds
    get() = projection.visibleRegion.latLngBounds