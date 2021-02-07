package com.gilangarinata.weathroo

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gilangarinata.sehatqecommerce.ui.viewmodel.WeatherViewModel
import com.gilangarinata.sehatqecommerce.ui.viewmodel.WeatherViewModelProviderFactory
import com.gilangarinata.weathroo.repository.WeatherRepository
import com.gilangarinata.weathroo.utils.Resource
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: WeatherViewModel
    private var latitude = -6.2088
    private var longitude = 106.8456
    private var units = "metric"

    private var locationManager : LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize(units)
        initView()
        showProgressBar()
    }

    private fun hideProgressBar(){
        lytContent.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun showProgressBar(){
        lytContent.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun initView(){
        viewModel.weatherData.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { weather ->
                        val symbol = if (units == "metric") "\u2103" else "\u2109"
                        tvCity.text = weather.name
                        tvTemp.text = "${weather.main.temp.toInt()}$symbol"
                        tvWeather.text = weather.weather[0].main
                        when (weather.weather[0].main) {
                            "Rain" -> imgWeather.setBackgroundResource(R.drawable.rainy)
                            "Clear" -> imgWeather.setBackgroundResource(R.drawable.sun)
                            else -> imgWeather.setBackgroundResource(R.drawable.cloudy)
                        }
                    }
                }
                is Resource.Error -> {

                }
            }
        })

        tvTemp.setOnClickListener {
            units = if(units == "metric"){
                "standard"
            }else{
                "metric"
            }
            initialize(units)
            viewModel.getHomeData()
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            latitude = location.latitude
            longitude = location.longitude
            initialize(units)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun initialize(unit: String){
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        try {
            locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }
        val repository = WeatherRepository(
            longitude = longitude,
            latitude = latitude,
            units = unit
        )
        val factory = WeatherViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
        viewModel.getHomeData()
    }

    private fun initLocation(){
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            // Request location updates
            lm?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available")
        }
    }


}