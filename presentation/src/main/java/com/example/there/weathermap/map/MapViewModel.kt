package com.example.there.weathermap.map

import android.arch.lifecycle.MutableLiveData
import com.example.there.domain.city.City
import com.example.there.domain.city.CityInteractor
import com.example.there.weathermap.base.BaseViewModel
import com.google.android.gms.maps.model.LatLngBounds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MapViewModel(private val interactor: CityInteractor): BaseViewModel() {

    val citiesInCurrentBounds: MutableLiveData<List<City>> = MutableLiveData()

    fun findCitiesInBounds(bounds: LatLngBounds) {
        val minLat = bounds.southwest.latitude
        val maxLat = bounds.northeast.latitude
        val minLon = bounds.southwest.longitude
        val maxLon = bounds.northeast.longitude

        disposables.clear()

        disposables.add(interactor.findInBounds(minLat, maxLat, minLon, maxLon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ citiesInCurrentBounds.value = it }, {}))
    }
}