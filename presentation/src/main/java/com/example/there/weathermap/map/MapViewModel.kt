package com.example.there.weathermap.map

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.there.domain.city.City
import com.example.there.domain.city.CityInteractor
import com.example.there.domain.weather.Weather
import com.example.there.domain.weather.WeatherInteractor
import com.example.there.weathermap.base.BaseViewModel
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapViewModel @Inject constructor(
        private val cityInteractor: CityInteractor,
        private val weatherInteractor: WeatherInteractor
) : BaseViewModel() {

    val citiesInCurrentBounds: MutableLiveData<List<City>> = MutableLiveData()

    private val findCitiesDisposables = CompositeDisposable()
    private val loadWeatherDisposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        findCitiesDisposables.clear()
        loadWeatherDisposables.clear()
    }

    fun findCitiesInBounds(bounds: LatLngBounds) {
        val minLat = bounds.southwest.latitude
        val maxLat = bounds.northeast.latitude
        val minLon = bounds.southwest.longitude
        val maxLon = bounds.northeast.longitude

        findCitiesDisposables.clear()

        findCitiesDisposables.add(cityInteractor.findInBounds(minLat, maxLat, minLon, maxLon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ citiesInCurrentBounds.value = it }, {}))
    }

    fun loadWeather(marker: Marker, onSuccess: (Weather, Marker) -> Unit) {
        loadWeatherDisposables.clear()

        disposables.add(weatherInteractor.loadWeather(marker.position.latitude, marker.position.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccess(it, marker)
                }, {
                    val e = it
                    Log.e("ERROR", e.message)
                }))
    }
}