package com.lbg.techtest.domain.repository

import com.lbg.techtest.data.entity.WeatherEntity
import retrofit2.Response

interface LBGRepository {
    suspend fun getCurrentWeather(): Response<WeatherEntity>
    suspend fun getForecasting(days: String): Response<WeatherEntity>
}