package com.lbg.domain.repository

import com.lbg.domain.entity.WeatherDto
import com.lbg.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface LBGRepository {
    suspend fun getCurrentWeather(): Flow<Result<WeatherDto>>
    suspend fun getForecasting(days: String): Flow<Result<WeatherDto>>
}