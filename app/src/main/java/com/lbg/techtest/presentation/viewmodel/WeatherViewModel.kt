package com.lbg.techtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.usecase.WeatherUseCase
import com.lbg.domain.utils.Resource
import com.lbg.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<WeatherEntity>>(Resource.Loading())
    val state: StateFlow<Resource<WeatherEntity>> get() = _state.asStateFlow()

    init {
        getCurrentWeather()
    }

    fun getCurrentWeather() = viewModelScope.launch {
        weatherUseCase.invoke()
            .collect { response ->
                when (response) {
                    is Result.Loading -> _state.value = Resource.Loading()
                    is Result.Error -> _state.value = Resource.Error(response.message)
                    is Result.Success -> _state.value = Resource.Success(response.data)
                }
            }
    }

}