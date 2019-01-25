package com.example.there.weathermap

import com.example.there.domain.city.CityInteractor
import com.example.there.weathermap.splash.SplashViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class SplashViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val immediateSchedulerRule = RxImmediateSchedulerRule()
    }

    @Mock
    private lateinit var interactor: CityInteractor

    @Mock
    private lateinit var onCompleted: () -> Unit

    private lateinit var splashViewModel: SplashViewModel

    private lateinit var testDataToInsert: String

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        testDataToInsert = "toInsert"

        splashViewModel = SplashViewModel(interactor)
    }

    @Test
    fun givenIncompleteDb_whenLoadCityData_cityDataIsReadFromFileAndInsertedIntoDbAndOnCompletedIsCalled() {
        whenever(interactor.isDbComplete()).thenReturn(Single.just(false))
        whenever(interactor.readCityDataFromFile(any())).thenReturn(Single.just(emptyList()))
        whenever(interactor.insertMany(any())).thenReturn(Completable.complete())

        splashViewModel.loadCityData(testDataToInsert, onCompleted)

        verify(interactor).isDbComplete()
        verify(interactor).readCityDataFromFile(any())
        verify(interactor).insertMany(any())
        verify(onCompleted).invoke()
    }

    @Test
    fun givenCompleteDb_whenLoadCityData_onCompletedIsCalled() {
        whenever(interactor.isDbComplete()).thenReturn(Single.just(true))

        splashViewModel.loadCityData(testDataToInsert, onCompleted)

        verify(interactor).isDbComplete()
        verify(onCompleted).invoke()
    }
}
