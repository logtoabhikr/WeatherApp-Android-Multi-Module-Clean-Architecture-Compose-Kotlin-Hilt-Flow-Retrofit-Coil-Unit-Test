package com.lbg.techtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.domain.model.WeatherModel
import com.lbg.domain.usecase.WeatherUseCase
import com.lbg.domain.utils.DispatcherProvider
import com.lbg.domain.utils.Resource
import com.lbg.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<WeatherModel>>(Resource.Loading())
    val state: StateFlow<Resource<WeatherModel>> get() = _state.asStateFlow()

    fun getCurrentWeather() = viewModelScope.launch(dispatcherProvider.main) {
        weatherUseCase.invoke()
            .flowOn(dispatcherProvider.io)
            .catch { _state.value = Resource.Error(it.message.toString()) }
            .collect { response ->
                when (response) {
                    is Result.Loading -> _state.value = Resource.Loading()
                    is Result.Error -> _state.value = Resource.Error(response.message)
                    is Result.Success -> _state.value = Resource.Success(response.data)
                }
            }
    }

}