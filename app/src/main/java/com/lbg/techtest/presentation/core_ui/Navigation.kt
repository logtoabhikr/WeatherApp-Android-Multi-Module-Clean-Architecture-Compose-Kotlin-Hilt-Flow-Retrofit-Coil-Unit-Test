package com.lbg.techtest.presentation.core_ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lbg.techtest.presentation.ui.CurrentWeatherScreen
import com.lbg.techtest.presentation.ui.ForecastingScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationItem.CurrentWeather.route) {
        composable(NavigationItem.CurrentWeather.route) {
            CurrentWeatherScreen()
        }

        composable(NavigationItem.Forecasting.route) {
            ForecastingScreen()
        }

    }
}