package com.lbg.domain.repository

import com.lbg.domain.entity.WeatherEntity
import retrofit2.Response

interface LBGRepository {
    suspend fun getCurrentWeather(): Response<WeatherEntity>
    suspend fun getForecasting(days: String): Response<WeatherEntity>
}