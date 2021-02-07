package com.gilangarinata.sehatqecommerce.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilangarinata.weathroo.models.WeatherResponse

import com.gilangarinata.weathroo.repository.WeatherRepository
import com.gilangarinata.weathroo.utils.Resource
import kotlinx.coroutines.launch

import retrofit2.Response


/**
 * Created by Gilang Arinata on 07/02/21.
 * https://github.com/gilangarinata/
 */

class WeatherViewModel(
    val weatherRepository: WeatherRepository
) : ViewModel() {

    val weatherData: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()
    fun getHomeData() = viewModelScope.launch {
        weatherData.postValue(Resource.Loading())
        val response = weatherRepository.getWeatherData()
        weatherData.postValue(handleHomeDataResponse(response))
    }

    private fun handleHomeDataResponse(response: Response<WeatherResponse>): Resource<WeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}