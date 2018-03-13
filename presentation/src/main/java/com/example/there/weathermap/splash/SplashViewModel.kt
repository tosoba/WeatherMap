package com.example.there.weathermap.splash

import android.util.Log
import com.example.there.domain.city.City
import com.example.there.domain.city.CityInteractor
import com.example.there.weathermap.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SplashViewModel(private val interactor: CityInteractor) : BaseViewModel() {

    fun loadCityData(data: String, onCompleted: () -> Unit) {
        isDbComplete(onDbComplete = {
            onCompleted()
        }, onDbIncomplete = {
            readCityDataFromFile(data, onCompleted = { insertCityData(it, onCompleted) })
        })
    }

    private fun isDbComplete(onDbComplete: () -> Unit, onDbIncomplete: () -> Unit) {
        disposables.add(interactor.isDbComplete()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isComplete ->
                    if (!isComplete) {
                        onDbIncomplete()
                    } else {
                        onDbComplete()
                    }
                }, {
                    Log.e("isDbComplete", it.message)
                }))
    }

    private fun readCityDataFromFile(data: String, onCompleted: (List<City>) -> Unit) {
        disposables.add(interactor.readCityDataFromFile(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCompleted(it) }, {
                    Log.e("readCityDataFromFile", it.message)
                }))
    }

    private fun insertCityData(cities: List<City>, onCompleted: () -> Unit) {
        disposables.add(interactor.insertMany(cities)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onCompleted() }, {
                    Log.e("insertCityData", it.message)
                }))
    }
}