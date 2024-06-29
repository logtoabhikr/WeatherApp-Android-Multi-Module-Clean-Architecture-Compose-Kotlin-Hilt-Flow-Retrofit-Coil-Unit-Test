package com.lbg.data.repository

import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.repository.LBGRepository
import com.lbg.data.network.LBGService
import retrofit2.Response
import javax.inject.Inject

class LBGRepositoryImpl @Inject constructor(
    private val lbgService: LBGService
) : LBGRepository {

    override suspend fun getCurrentWeather(): Response<WeatherEntity> {
        return lbgService.getCurrentWeather()
    }

    override suspend fun getForecasting(days: String): Response<WeatherEntity> {
        return lbgService.getCurrentWeather(days = days)
    }

}