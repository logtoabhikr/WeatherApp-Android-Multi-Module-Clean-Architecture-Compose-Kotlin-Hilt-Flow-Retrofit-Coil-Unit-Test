package com.lbg.data.network

import com.lbg.domain.entity.WeatherEntity
import com.lbg.data.utils.BaseURL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LBGService {

    @GET("forecast.json")
    suspend fun getCurrentWeather(
        @Query("key") key: String = BaseURL.APIKEY,
        @Query("q") location: String = "London",
        @Query("days") days: String = "1",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
    ): Response<WeatherEntity>
}