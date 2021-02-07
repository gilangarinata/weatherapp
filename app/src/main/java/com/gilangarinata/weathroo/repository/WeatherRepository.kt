package com.gilangarinata.weathroo.repository

import com.gilangarinata.weathroo.api.RetrofitInstance
import com.gilangarinata.weathroo.utils.Constants

/**
 * Created by Gilang Arinata on 01/02/21.
 * https://github.com/gilangarinata/
 */
class WeatherRepository(
    private val latitude : Double,
    private val longitude : Double,
    private val units : String
) {
    suspend fun getWeatherData() = RetrofitInstance.api.getWeatherData(
        latitude = latitude,
        longitude = longitude,
        appId = Constants.APP_ID,
        units = units
    )
}