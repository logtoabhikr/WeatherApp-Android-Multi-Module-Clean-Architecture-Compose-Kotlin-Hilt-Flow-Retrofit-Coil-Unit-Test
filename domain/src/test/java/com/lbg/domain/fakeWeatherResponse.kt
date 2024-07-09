package com.lbg.domain

import com.lbg.domain.entity.Condition
import com.lbg.domain.entity.Current
import com.lbg.domain.entity.Day
import com.lbg.domain.entity.Forecast
import com.lbg.domain.entity.Forecastday
import com.lbg.domain.entity.Location
import com.lbg.domain.entity.WeatherDto
import com.lbg.domain.model.ConditionModel
import com.lbg.domain.model.CurrentModel
import com.lbg.domain.model.DayModel
import com.lbg.domain.model.ForecastModel
import com.lbg.domain.model.ForecastdayModel
import com.lbg.domain.model.LocationModel
import com.lbg.domain.model.WeatherModel

fun provideWeatherModel(): WeatherModel {
    val conditionEntity = ConditionModel(text = "Sunny", icon = "icon_url")
    val currentEntity = CurrentModel(
        tempC = 25.0,
        condition = conditionEntity,
        uv = 10,
        humidity = 50,
        cloud = 50,
        feelslikeC = 25.0
    )
    val forecastDayEntity = ForecastdayModel(
        date = "2023-07-01",
        day = DayModel(maxtempC = 30.0, mintempC = 20.0, condition = conditionEntity),
        hour = listOf(currentEntity)
    )
    return WeatherModel(
        location = LocationModel(
            name = "New Delhi",
            country = "India",
            localtime = "2023-07-01 12:00"
        ),
        current = currentEntity,
        forecast = ForecastModel(forecastday = listOf(forecastDayEntity))
    )
}

fun provideWeatherDto(): WeatherDto {
    val conditionDto = Condition(text = "Sunny", icon = "icon_url")
    val currentDto = Current(
        tempC = 25.0,
        condition = conditionDto,
        uv = 10,
        humidity = 50,
        cloud = 50,
        feelslikeC = 25.0
    )
    val forecastDayDto = Forecastday(
        date = "2023-07-01",
        day = Day(maxtempC = 30.0, mintempC = 20.0, condition = conditionDto),
        hour = listOf(currentDto)
    )
    return WeatherDto(
        location = Location(
            name = "New Delhi",
            country = "India",
            localtime = "2023-07-01 12:00"
        ),
        current = currentDto,
        forecast = Forecast(forecastday = listOf(forecastDayDto))
    )
}