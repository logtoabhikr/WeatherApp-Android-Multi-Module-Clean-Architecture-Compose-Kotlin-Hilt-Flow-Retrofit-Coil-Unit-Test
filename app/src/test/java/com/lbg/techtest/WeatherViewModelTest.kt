package com.lbg.techtest

import com.lbg.techtest.domain.usecase.WeatherUseCase
import com.lbg.techtest.presentation.viewmodel.WeatherViewModel
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherUseCase: WeatherUseCase

    @Before
    fun setup() {
        weatherUseCase = mockk<WeatherUseCase>()
        viewModel = WeatherViewModel(weatherUseCase)
    }

    @Test
    fun `parseDateToTime() correctly parses a date string into a time string`() {
        // Given
        val dateString = "2023-04-09 12:00"
        val expectedTimeString = "12:00 am"

        // When
        val actualTimeString = viewModel.parseDateToTime(dateString)

        // Then
        assertEquals(expectedTimeString, actualTimeString)
    }

    @Test
    fun `parseDateToTime() returns the original date string if the date string is invalid`() {
        // Given
        val dateString = "Invalid date string"

        // When
        val actualTimeString = viewModel.parseDateToTime(dateString)

        // Then
        assertEquals(dateString, actualTimeString)
    }


}