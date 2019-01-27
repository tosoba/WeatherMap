package com.example.map.ext

import android.content.res.Resources
import com.example.core.model.Weather
import com.example.map.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker


fun Marker.setWeather(weather: Weather, resources: Resources) {
    val conf = android.graphics.Bitmap.Config.ARGB_8888
    val bmp = android.graphics.Bitmap.createBitmap(150, 150, conf)
    val canvas = android.graphics.Canvas(bmp)

    val color = android.graphics.Paint()
    color.textSize = 55f
    color.color = android.graphics.Color.BLACK

    val iconDrawable = when (weather.icon) {
        "clear-day", "clear-night" -> R.drawable.icon_clear
        "rain", "sleet" -> R.drawable.icon_rain
        "snow" -> R.drawable.icon_snow
        "cloudy", "wind" -> R.drawable.icon_clouds
        "partly-cloudy-day", "partly-cloudy-night" -> R.drawable.icon_light_clouds
        "fog" -> R.drawable.icon_fog
        "loading" -> R.drawable.loading
        else -> R.drawable.icon_default
    }
    canvas.drawBitmap(android.graphics.BitmapFactory.decodeResource(resources, iconDrawable), 0f, 0f, color)

    val text = if (weather == Weather.default || weather == Weather.loading) "" else weather.temperature.toString()
    canvas.drawText(text, 40f, 40f, color)

    setIcon(BitmapDescriptorFactory.fromBitmap(bmp))
}