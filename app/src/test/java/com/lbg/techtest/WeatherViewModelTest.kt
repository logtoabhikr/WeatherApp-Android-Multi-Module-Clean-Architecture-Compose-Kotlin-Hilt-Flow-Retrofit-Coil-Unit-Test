package com.lbg.techtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.usecase.WeatherUseCase
import com.lbg.domain.utils.Resource
import com.lbg.domain.utils.Result
import com.lbg.techtest.presentation.viewmodel.WeatherViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val rule = MainDispatcherRule()

    @MockK
    private lateinit var viewModel: WeatherViewModel

    @MockK
    private lateinit var weatherUseCase: WeatherUseCase

    private val message = "Error Occurred!"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkConstructor(Regex::class, CoroutineExceptionHandler::class, Throwable::class)
        viewModel = spyk(WeatherViewModel(weatherUseCase))
    }

    @Test
    fun `getweather can get weather data`() {
        rule.launch {
            // given
            val weather = mockk<WeatherEntity>()
            viewModel.state.collect()

            coEvery { weatherUseCase.invoke() } returns mockk<Flow<Result<WeatherEntity>>>()
            // when
            viewModel.getCurrentWeather()
            coVerify { weatherUseCase.invoke() }

            //then
            viewModel.state.collect {
                //assertEquals(Resource.Loading(), viewModel.state.value)
                assertEquals(Resource.Success(weather), viewModel.state.value)
            }
        }
    }

    @Test
    fun `should emit loading response when api response loading`() {
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            Resource.Success(Any())
        }
        rule.launch(exceptionHandler) {
            coEvery { weatherUseCase.invoke() } throws Exception()
            rule.advanceUntilIdle()
            assertEquals(
                Resource.Success(Any()),
                viewModel.state.value
            )
        }
    }

    @Test
    fun `should emit error response when api response error`() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Resource.Error(message, throwable.message.toString())
        }
        rule.launch(exceptionHandler) {
            coEvery { weatherUseCase.invoke() } throws Exception()
            viewModel.getCurrentWeather()
            coVerify { weatherUseCase.invoke() }
            assertEquals(
                Resource.Error(message, Any()),
                viewModel.state.value
            )
        }
    }

    @Test
    fun `should emit success response when api response success`() {
        rule.launch {
            coEvery { weatherUseCase.invoke() } returns mockk()
            viewModel.getCurrentWeather()
            coVerify { weatherUseCase.invoke() }
            assertEquals(
                Resource.Success(Any()),
                Result.Success(Any())
            )
        }
    }

    /*@Test
    fun `parseDateToTime() correctly parses a date string into a time string`() {
        rule.launch {
            val expectedTimeString = "12:00 am"
            val actualTimeString = viewModel.parseDateToTime("2023-04-09 12:00")
            assertEquals(expectedTimeString, actualTimeString)
        }
    }

    @Test
    fun `parseDateToTime() returns the original date string if the date string is invalid`() {
        rule.launch {
            // Given
            val dateString = "Invalid date string"
            // When
            val actualTimeString = viewModel.parseDateToTime(dateString)
            // Then
            assertEquals(dateString, actualTimeString)
        }
    }*/

    @After
    fun tearDown() {
        unmockkAll()
    }


}