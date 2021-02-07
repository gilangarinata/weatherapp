package com.gilangarinata.sehatqecommerce.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gilangarinata.weathroo.repository.WeatherRepository


/**
 * Created by Gilang Arinata on 07/02/21.
 * https://github.com/gilangarinata/
 */
class WeatherViewModelProviderFactory(
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository) as T
    }
}