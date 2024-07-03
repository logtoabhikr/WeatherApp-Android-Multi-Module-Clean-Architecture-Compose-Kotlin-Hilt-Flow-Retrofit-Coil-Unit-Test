package com.lbg.techtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.usecase.ForecastingUseCase
import com.lbg.domain.utils.Constants
import com.lbg.domain.utils.Resource
import com.lbg.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastingViewModel @Inject constructor(private val useCase: ForecastingUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<WeatherEntity>>(Resource.Loading())
    val state get() = _state.asStateFlow()

    init {
        getForecastedWeather()
    }

    fun getForecastedWeather() = viewModelScope.launch {

        useCase.execute(ForecastingUseCase.Params(days = Constants.FORECASTING_DAYS))
            .collect { response ->
                when (response) {
                    is Result.Loading -> _state.value = Resource.Loading()
                    is Result.Error -> _state.value = Resource.Error(response.message)
                    is Result.Success -> {
                        // if (!response.data.isNullOrEmpty())
                        _state.value = Resource.Success(response.data)
                    }
                }
            }
    }

}