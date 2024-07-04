package com.lbg.domain.usecase

import com.lbg.domain.repository.LBGRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class WeatherUseCaseTest {

    @Mock
    lateinit var weatherUseCase: WeatherUseCase

    @Mock
    lateinit var lbgRepository: LBGRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherUseCase = WeatherUseCase(lbgRepository)
    }

    @Test
    operator fun invoke() {
        runTest {
            val result = weatherUseCase.invoke()
            assertEquals(result, lbgRepository.getCurrentWeather())
        }
    }
}