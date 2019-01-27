package com.example.there.weathermap

import org.junit.ClassRule


class SplashViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val immediateSchedulerRule = RxImmediateSchedulerRule()
    }

}
