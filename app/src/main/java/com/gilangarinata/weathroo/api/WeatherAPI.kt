package com.gilangarinata.weathroo.api

import com.gilangarinata.weathroo.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Gilang Arinata on 07/02/21.
 * https://github.com/gilangarinata/
 */
interface WeatherAPI {
    @GET("data/2.5/weather")
    suspend fun getWeatherData(@Query("lat") latitude : Double,
                            @Query("lon") longitude : Double,
                            @Query("appid") appId : String,
                               @Query("units") units : String,
    ): Response<WeatherResponse>
}