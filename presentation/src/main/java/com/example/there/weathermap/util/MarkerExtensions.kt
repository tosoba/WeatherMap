package com.example.there.weathermap.util

import android.content.res.Resources
import android.graphics.*
import android.util.Log
import com.example.there.domain.weather.Weather
import com.example.there.weathermap.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker

fun Marker.setWeather(weather: Weather, resources: Resources) {
    val conf = Bitmap.Config.ARGB_8888
    val bmp = Bitmap.createBitmap(150, 150, conf)
    val canvas = Canvas(bmp)

    val color = Paint()
    color.textSize = 55f
    color.color = Color.BLACK

    val iconDrawable = when (weather.icon) {
        "clear-day", "clear-night" -> R.drawable.icon_clear
        "rain", "sleet" -> R.drawable.icon_rain
        "snow" -> R.drawable.icon_snow
        "cloudy", "wind" -> R.drawable.icon_clouds
        "partly-cloudy-day", "partly-cloudy-night" -> R.drawable.icon_light_clouds
        "fog" -> R.drawable.icon_fog
        "loading" -> R.drawable.loading
        else -> {
            R.drawable.icon_default
            Log.e("ICON", weather.icon)
        }
    }
    canvas.drawBitmap(BitmapFactory.decodeResource(resources, iconDrawable), 0f, 0f, color)

    val text = if (weather == Weather.default || weather == Weather.loading) "" else weather.temperature.toString()
    canvas.drawText(text, 40f, 40f, color)

    this.setIcon(BitmapDescriptorFactory.fromBitmap(bmp))
}