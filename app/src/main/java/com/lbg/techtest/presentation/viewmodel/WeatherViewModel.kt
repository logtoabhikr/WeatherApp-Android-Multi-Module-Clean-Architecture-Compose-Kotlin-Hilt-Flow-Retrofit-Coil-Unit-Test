package com.lbg.techtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.data.utils.Resource
import com.lbg.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val state: StateFlow<Resource<Any>?> = _state


    fun getCurrentWeather() = viewModelScope.launch {
        _state.value = Resource.Loading()
        val result = weatherUseCase.getCurrentWeather()
        if (result.isSuccessful && result.body() != null)
            result.body()?.let {
                _state.value = Resource.Success(it)
            }
        else
            _state.value = Resource.Error("Error Occurred!", result.errorBody().toString())
    }

    fun parseDateToTime(dateString: String): String {
        return weatherUseCase.invoke(dateString)
    }

}