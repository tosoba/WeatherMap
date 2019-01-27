package com.example.there.weathermap

import android.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.ClassRule

class MapViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val immediateSchedulerRule = RxImmediateSchedulerRule()

        @ClassRule
        @JvmField
        val taskExecutorRule = InstantTaskExecutorRule()
    }
}