package com.lbg.techtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.domain.model.WeatherModel
import com.lbg.domain.usecase.ForecastingUseCase
import com.lbg.domain.utils.Constants
import com.lbg.domain.utils.DispatcherProvider
import com.lbg.domain.utils.Resource
import com.lbg.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastingViewModel @Inject constructor(
    private val useCase: ForecastingUseCase,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<WeatherModel>>(Resource.Loading())
    val state: StateFlow<Resource<WeatherModel>> = _state

    fun getForecastedWeather() = viewModelScope.launch(dispatcherProvider.main) {
        useCase.invoke(Constants.FORECASTING_DAYS)
            .flowOn(dispatcherProvider.io)
            .catch { _state.value = Resource.Error(it.message.toString()) }
            .collect { response ->
                when (response) {
                    is Result.Success -> _state.value = Resource.Success(response.data)
                    is Result.Loading -> _state.value = Resource.Loading()
                    is Result.Error -> _state.value = Resource.Error(response.message)
                }
            }
    }
}