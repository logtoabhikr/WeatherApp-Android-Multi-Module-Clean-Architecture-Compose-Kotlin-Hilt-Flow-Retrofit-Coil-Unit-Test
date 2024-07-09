package com.lbg.domain.mapper

import com.lbg.domain.entity.WeatherDto
import com.lbg.domain.model.WeatherModel
import com.lbg.domain.provideWeatherDto
import com.lbg.domain.provideWeatherModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherMapperTest {
    @Mock
    private lateinit var weatherMapper: WeatherMapper

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `mapFrom with model data`() {
        val weatherDto = provideWeatherDto()
        val weatherModel = provideWeatherModel()
        runTest {
            Mockito.`when`(weatherMapper.mapFrom(weatherDto)).thenAnswer {
                weatherModel
            }
            val result = weatherMapper.mapFrom(weatherDto)
            Assert.assertEquals(result.location?.name, weatherDto.location?.name)
            Assert.assertEquals(result.current?.time, weatherDto.current?.time)
            Assert.assertEquals(
                result.forecast?.forecastday?.first()?.date,
                weatherDto.forecast?.forecastday?.first()?.date
            )
        }
    }

    @Test
    fun `mapFrom with mock data`() {
        val weatherDto = Mockito.mock(WeatherDto::class.java)
        val weatherModel = Mockito.mock(WeatherModel::class.java)
        runTest {
            Mockito.`when`(weatherMapper.mapFrom(weatherDto)).thenAnswer {
                weatherModel
            }
            val result = weatherMapper.mapFrom(weatherDto)
            Assert.assertEquals(result.location?.name, weatherDto.location?.name)
            Assert.assertEquals(result.current?.time, weatherDto.current?.time)
            Assert.assertEquals(
                result.forecast?.forecastday?.first()?.date,
                weatherDto.forecast?.forecastday?.first()?.date
            )
        }
    }
}