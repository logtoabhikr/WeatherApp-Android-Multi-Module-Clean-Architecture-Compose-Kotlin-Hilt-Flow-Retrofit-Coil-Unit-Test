package com.lbg.techtest.network

import com.lbg.techtest.data.entity.WeatherEntity
import com.lbg.techtest.domain.utils.BaseURL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LBGService {

    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("key") key: String = BaseURL.apiKey,
        @Query("q") location: String = "London",
        @Query("days") days: String = "1",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
    ): Response<WeatherEntity>
}