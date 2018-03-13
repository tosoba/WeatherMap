package com.example.there.weathermap

import android.app.Activity
import android.app.Application
import com.example.there.weathermap.di.AppModule
import com.example.there.weathermap.di.DaggerAppComponent
import com.example.there.weathermap.di.DatabaseModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class WeatherMapApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
                .builder()
                .application(this)
                .appModule(AppModule(this))
                .databaseModule(DatabaseModule(this))
                .build()
                .inject(this)
    }
}