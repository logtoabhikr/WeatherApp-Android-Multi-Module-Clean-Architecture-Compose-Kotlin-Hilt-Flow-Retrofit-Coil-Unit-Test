package com.lbg.techtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lbg.data.utils.Resource
import com.lbg.domain.usecase.ForecastingUseCase
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
class ForecastingViewModel @Inject constructor(private val useCase: ForecastingUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val state: StateFlow<Resource<Any>> = _state


    fun getForecastedWeather() = viewModelScope.launch {

        try {
            _state.value = Resource.Loading()
            val result = useCase.getForecastingDays()
            if (result.body() != null && result.isSuccessful)
                result.body()?.let {
                    _state.value = Resource.Success(it)
                }
            else
                _state.value = Resource.Error("Error Occurred!", result.message().toString())
        } catch (e: Exception) {
            _state.value = Resource.Error("Error Occurred!", e.message.toString())
        }

    }

    fun parseDateToDay(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
        val date: Date? = try {
            inputFormat.parse(dateString)
        } catch (e: ParseException) {
            return dateString
        }
        return outputFormat.format(date!!)
    }

}