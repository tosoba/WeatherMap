package com.example.there.weathermap.routing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.example.coreandroid.routing.Router
import com.example.map.ui.MapActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRouter @Inject constructor() : Router {
    override fun showMap(activity: AppCompatActivity) {
        activity.startActivity(Intent(activity, MapActivity::class.java))
    }
}