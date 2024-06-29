package com.lbg.techtest.presentation.core_ui

import com.lbg.techtest.R

sealed class NavigationItem(val route: String, val icon: Int, val title: String) {
    data object CurrentWeather :
        NavigationItem(Routes.WEATHER, R.drawable.current_weather, "Current Weather")

    data object Forecasting :
        NavigationItem(Routes.FORECASTING, R.drawable.weather_forecasting, "Forecasting")

}

object Routes {
    const val WEATHER = "weather"
    const val FORECASTING = "forecasting"
}