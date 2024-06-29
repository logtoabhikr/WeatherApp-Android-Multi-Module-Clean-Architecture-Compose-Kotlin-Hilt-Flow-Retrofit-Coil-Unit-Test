package com.lbg.techtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.techtest.domain.usecase.ForecastingUseCase
import com.lbg.techtest.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ForecastingViewModel @Inject constructor(private val useCase: ForecastingUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<Any>?>(null)
    val state: StateFlow<Resource<Any>?> = _state


    fun getForecastedWeather() = viewModelScope.launch {

        _state.value = Resource.Loading()

        val result = useCase.getForecastingDays()

        if (result.isSuccessful && result.body()!=null)
            result.body()?.let {
                _state.value = Resource.Success(it)
            }
        else
            _state.value = Resource.Error("Unknown Error", result.message().toString())
    }

    fun parseDateToDay(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat = SimpleDateFormat("EEE, dd MMM")
        val date: Date? = try {
            inputFormat.parse(dateString)
        } catch (e: ParseException) {
            return dateString
        }
        return outputFormat.format(date)
    }

}