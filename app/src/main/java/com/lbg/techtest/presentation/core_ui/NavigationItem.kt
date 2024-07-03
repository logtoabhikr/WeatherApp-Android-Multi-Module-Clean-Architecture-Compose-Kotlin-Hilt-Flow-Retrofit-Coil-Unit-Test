package com.lbg.techtest.presentation.core_ui

import com.lbg.domain.utils.Constants
import com.lbg.techtest.R

sealed class NavigationItem(val route: String, val icon: Int, val title: String) {
    data object CurrentWeather :
        NavigationItem(Routes.WEATHER, R.drawable.current_weather, Constants.WEATHER_TITLE)

    data object Forecasting :
        NavigationItem(Routes.FORECASTING, R.drawable.weather_forecasting, Constants.FORECAST_TITLE)

}

object Routes {
    const val WEATHER = "weather"
    const val FORECASTING = "forecasting"
}