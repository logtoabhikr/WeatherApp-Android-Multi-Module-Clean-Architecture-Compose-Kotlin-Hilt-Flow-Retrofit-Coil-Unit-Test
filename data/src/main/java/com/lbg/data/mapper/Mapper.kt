package com.lbg.data.mapper

import com.lbg.data.model.Condition
import com.lbg.data.model.Current
import com.lbg.data.model.Day
import com.lbg.data.model.Forecast
import com.lbg.data.model.Forecastday
import com.lbg.data.model.Location
import com.lbg.data.model.WeatherDto
import com.lbg.data.utils.Mapper
import com.lbg.domain.entity.ConditionModel
import com.lbg.domain.entity.CurrentModel
import com.lbg.domain.entity.DayModel
import com.lbg.domain.entity.ForecastModel
import com.lbg.domain.entity.ForecastdayModel
import com.lbg.domain.entity.LocationModel
import com.lbg.domain.entity.WeatherEntity
import javax.inject.Inject

//mapper for WeatherDto
class WeatherMapper @Inject constructor() : Mapper<WeatherDto, WeatherEntity> {
    override fun mapFrom(from: WeatherDto): WeatherEntity {
        return WeatherEntity(
            location = from.location?.let { LocationModelMapper().mapFrom(it) },
            current = from.current?.let { CurrentModelMapper().mapFrom(it) },
            forecast = from.forecast?.let { ForecastModelMapper().mapFrom(it) }
        )
    }
}

class LocationModelMapper @Inject constructor() : Mapper<Location, LocationModel> {
    override fun mapFrom(from: Location): LocationModel {
        return LocationModel(
            name = from.name,
            country = from.country,
            localtime = from.localtime
        )
    }
}

class CurrentModelMapper @Inject constructor() : Mapper<Current, CurrentModel> {
    override fun mapFrom(from: Current): CurrentModel {
        return CurrentModel(
            tempC = from.tempC,
            condition = from.condition.let { ConditionModelMapper().mapFrom(it) },
            humidity = from.humidity,
            cloud = from.cloud,
            feelslikeC = from.feelslikeC,
            uv = from.uv,
            time = from.time
        )
    }
}

class ConditionModelMapper @Inject constructor() : Mapper<Condition, ConditionModel> {
    override fun mapFrom(from: Condition): ConditionModel {
        return ConditionModel(
            text = from.text,
            icon = from.icon
        )
    }
}

class ForecastModelMapper @Inject constructor() : Mapper<Forecast, ForecastModel> {
    override fun mapFrom(from: Forecast): ForecastModel {
        return ForecastModel(
            forecastday = from.forecastday.map { ForecastDayModelMapper().mapFrom(it) }
        )
    }
}


class ForecastDayModelMapper @Inject constructor() : Mapper<Forecastday, ForecastdayModel> {
    override fun mapFrom(from: Forecastday): ForecastdayModel {
        return ForecastdayModel(
            date = from.date,
            day = from.day.let { DayModelMapper().mapFrom(it) },
            hour = from.hour.map { CurrentModelMapper().mapFrom(it) }
        )
    }
}

class DayModelMapper @Inject constructor() : Mapper<Day, DayModel> {
    override fun mapFrom(from: Day): DayModel {
        return DayModel(
            maxtempC = from.maxtempC,
            mintempC = from.mintempC,
            condition = from.condition.let { ConditionModelMapper().mapFrom(it) }
        )
    }
}
