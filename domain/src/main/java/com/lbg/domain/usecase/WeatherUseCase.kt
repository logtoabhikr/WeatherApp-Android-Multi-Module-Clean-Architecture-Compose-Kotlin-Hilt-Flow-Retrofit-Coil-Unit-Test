package com.lbg.domain.usecase

import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.repository.LBGRepository
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val lbgRepository: LBGRepository) {
    suspend fun getCurrentWeather(): Response<WeatherEntity> = lbgRepository.getCurrentWeather()

    operator fun invoke(time: String):String {
        val inputSDF = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault())
        val outputSDF = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date: Date? = try {
            inputSDF.parse(time)
        } catch (e: ParseException) {
            return time
        }
        return outputSDF.format(date!!)
    }
}