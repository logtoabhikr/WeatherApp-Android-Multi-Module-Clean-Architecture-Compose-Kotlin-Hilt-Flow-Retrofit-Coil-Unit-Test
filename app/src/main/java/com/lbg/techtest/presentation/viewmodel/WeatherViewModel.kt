package com.lbg.techtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.techtest.domain.usecase.WeatherUseCase
import com.lbg.techtest.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val currentWeatherUseCase: WeatherUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<Any>?>(null)
    val state: StateFlow<Resource<Any>?> = _state


    fun getCurrentWeather() = viewModelScope.launch {
        _state.value = Resource.Loading()
        val result = currentWeatherUseCase.getCurrentWeather()
        if (result.isSuccessful && result.body() != null)
            result.body()?.let {
                _state.value = Resource.Success(it)
            }
        else
            _state.value = Resource.Error("Error Occurred!", result.errorBody().toString())
        /*result.fold(
            onSuccess = {
                _state.value = Resource.Success(it)
            },
            onFailure = {
                _state.value = Resource.Error("Unknown Error", it)
            }
        )*/
    }

    fun parseDateToTime(time: String): String {
        val inputSDF = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
        val outputSDF = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date: Date? = try {
            inputSDF.parse(time)
        } catch (e: ParseException) {
            return time
        }
        return outputSDF.format(date!!)

    }

}