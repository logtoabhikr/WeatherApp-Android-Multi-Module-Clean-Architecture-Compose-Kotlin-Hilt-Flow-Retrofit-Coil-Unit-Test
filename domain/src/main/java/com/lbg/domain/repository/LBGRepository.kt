package com.lbg.domain.repository

import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface LBGRepository {
    suspend fun getCurrentWeather(): Flow<Result<WeatherEntity>>
    suspend fun getForecasting(days: String): Flow<Result<WeatherEntity>>
}