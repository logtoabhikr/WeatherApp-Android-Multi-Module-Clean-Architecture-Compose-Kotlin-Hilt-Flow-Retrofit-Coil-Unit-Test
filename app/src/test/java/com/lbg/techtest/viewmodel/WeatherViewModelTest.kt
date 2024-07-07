package com.lbg.techtest.viewmodel

import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.repository.LBGRepository
import com.lbg.domain.usecase.WeatherUseCase
import com.lbg.domain.utils.DispatcherProvider
import com.lbg.domain.utils.Resource
import com.lbg.domain.utils.Result
import com.lbg.techtest.TestDispatcherProvider
import com.lbg.techtest.presentation.viewmodel.WeatherViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @MockK
    private lateinit var repository: LBGRepository

    @MockK
    private lateinit var weatherUseCase: WeatherUseCase

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkConstructor(Regex::class, CoroutineExceptionHandler::class, Throwable::class)
        dispatcherProvider = TestDispatcherProvider()
        weatherUseCase = spyk(WeatherUseCase(repository))
        viewModel = spyk(WeatherViewModel(weatherUseCase, dispatcherProvider))
    }

    @Test
    fun `do call return loading`() {
        runTest {

            coEvery { weatherUseCase.invoke() } returns flow { emit(Result.Loading) }

            viewModel.getCurrentWeather()

            assertTrue(viewModel.state.value is Resource.Loading)
        }
    }

    @Test
    fun `do call return error`() {
        runTest {

            coEvery { weatherUseCase.invoke() } returns flow {
                emit(
                    Result.Error(
                        "",
                        0
                    )
                )
            }

            viewModel.getCurrentWeather()

            assertTrue(viewModel.state.value is Resource.Error)
        }
    }

    @Test
    fun `do call return exception`() {
        val exception = Mockito.mock(HttpException::class.java)
        runTest {

            coEvery { weatherUseCase.invoke() } returns flow {
                emit(
                    Result.Error(
                        exception.message(),
                        exception.code()
                    )
                )
            }

            viewModel.getCurrentWeather()

            assertTrue(viewModel.state.value is Resource.Error)
        }
    }

    @Test
    fun `do call return success`() {
        val weather = mockk<WeatherEntity>()

        runTest {
            coEvery { weatherUseCase.invoke() } returns flow { emit(Result.Success(weather)) }

            viewModel.getCurrentWeather()

            assertTrue(viewModel.state.value is Resource.Success)
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }


}