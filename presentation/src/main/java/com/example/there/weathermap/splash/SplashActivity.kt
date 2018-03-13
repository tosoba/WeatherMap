package com.example.there.weathermap.splash

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.there.weathermap.R
import com.example.there.weathermap.map.MapActivity
import dagger.android.AndroidInjection
import org.apache.commons.io.IOUtils
import java.io.InputStream
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SplashViewModelFactory

    private val viewModel: SplashViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (savedInstanceState == null) {
            val rawCityData: InputStream = resources.openRawResource(R.raw.city_data)
            val s = IOUtils.toString(rawCityData)
            viewModel.loadCityData(s, onCompleted = { startMapActivity() })
        }
    }

    private fun startMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
}
