package com.example.there.weathermap

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.there.domain.city.City
import com.example.there.domain.city.CityInteractor
import com.example.there.domain.weather.Weather
import com.example.there.domain.weather.WeatherInteractor
import com.example.there.weathermap.map.MapViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MapViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val immediateSchedulerRule = RxImmediateSchedulerRule()

        @ClassRule
        @JvmField
        val taskExecutorRule = InstantTaskExecutorRule()
    }

    @Mock
    private lateinit var weatherInteractor: WeatherInteractor

    @Mock
    private lateinit var cityInteractor: CityInteractor

    private lateinit var mapViewModel: MapViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mapViewModel = MapViewModel(cityInteractor, weatherInteractor)
    }

    @Test
    fun givenAnyBounds_whenFindCitiesInBounds_interactorFindInBoundsIsCalledAndCorrectValueIsReturned() {
        val testCity = City(10.0, 15.0, "abc", 100)
        val toReturn = listOf(testCity)
        whenever(cityInteractor.findInBounds(any(), any(), any(), any())).thenReturn(Single.just(toReturn))

        mapViewModel.findCitiesInBounds(LatLngBounds(LatLng(0.0, 0.0), LatLng(1.0, 1.0)))

        verify(cityInteractor).findInBounds(0.0, 1.0, 0.0, 1.0)
        assert(mapViewModel.citiesInCurrentBounds.value == toReturn)
    }

    @Mock
    private lateinit var testMarker: Marker

    @Mock
    private lateinit var onLoadWeatherSuccess: (Weather, Marker) -> Unit

    @Test
    fun givenAnyMarker_whenLoadWeatherIsCalled_weatherIsReturnedFromInteractorAndOnSuccessIsCalled() {
        val testMarkerPosition = LatLng(0.0, 0.0)
        val weatherToReturn = Weather(20, "")
        whenever(testMarker.position).thenReturn(testMarkerPosition)
        whenever(weatherInteractor.loadWeather(any(), any())).thenReturn(Single.just(weatherToReturn))

        mapViewModel.loadWeather(testMarker, onLoadWeatherSuccess)

        verify(weatherInteractor).loadWeather(any(), any())
        verify(onLoadWeatherSuccess).invoke(weatherToReturn, testMarker)
    }
}