package com.lbg.techtest.domain.repository

import com.lbg.techtest.data.entity.WeatherEntity
import com.lbg.techtest.network.LBGService
import retrofit2.Response
import javax.inject.Inject

class LBGRepositoryImpl @Inject constructor(
    private val lbgService: LBGService
) : LBGRepository {

    override suspend fun getCurrentWeather(): Response<WeatherEntity> {
        return lbgService.getCurrentWeather()
    }

    override suspend fun getForecasting(days: String): Response<WeatherEntity> {
        return lbgService.getCurrentWeather()
    }

}