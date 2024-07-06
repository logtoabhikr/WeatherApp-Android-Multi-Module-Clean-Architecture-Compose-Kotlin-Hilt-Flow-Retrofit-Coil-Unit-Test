package com.lbg.techtest.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lbg.domain.entity.CurrentModel
import com.lbg.domain.entity.WeatherEntity
import com.lbg.domain.utils.Constants
import com.lbg.domain.utils.Resource
import com.lbg.domain.utils.parseDateTo
import com.lbg.domain.utils.toast
import com.lbg.techtest.R
import com.lbg.techtest.presentation.core_ui.FullScreenLoading
import com.lbg.techtest.presentation.viewmodel.WeatherViewModel

@Composable
fun CurrentWeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getCurrentWeather()
    }

    val state by viewModel.state.collectAsState()

    var currentWeatherSate by remember {
        mutableStateOf(WeatherEntity())
    }

    when (val pageState = state) {
        is Resource.Loading -> {
            FullScreenLoading()
        }

        is Resource.Error -> {
            LocalContext.current.toast(pageState.message.toString())
        }

        is Resource.Success -> {
            if (pageState.data != null)
                currentWeatherSate = pageState.data as WeatherEntity
        }

        else -> {}
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {

            Spacer(modifier = Modifier.height(16.dp))

            HeaderWeather(currentWeatherSate)

            currentWeatherSate.forecast?.forecastday?.get(0)?.hour?.let {
                HourlyForecasting(
                    it
                )
            }

        }
    }
}

@Composable
fun HeaderWeather(
    currentWeather: WeatherEntity
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            contentDescription = "",
            model = "${Constants.SCHEMA_URL}${currentWeather.current?.condition?.icon}",
            placeholder = painterResource(
                id = R.drawable.current_weather
            ),
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
        )
        Text(
            text = currentWeather.current?.condition?.text
                ?: LocalContext.current.getString(R.string.loading),
            modifier = Modifier,
            style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${currentWeather.location?.name ?: ""}, ${currentWeather.location?.country ?: ""}",
            modifier = Modifier,
            style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 32.0.sp,
                        fontWeight = FontWeight(700.0.toInt()),
                        fontStyle = FontStyle.Normal,
                    )
                ) {
                    append("${currentWeather.current?.tempC ?: "..."} ")
                }
                withStyle(
                    style = SpanStyle(
                        baselineShift = BaselineShift.Superscript,
                        fontSize = 8.0.sp,
                        fontWeight = FontWeight(300.0.toInt()),
                        fontStyle = FontStyle.Normal,
                    )
                ) { // AnnotatedString.Builder
                    append(LocalContext.current.getString(R.string.temp_degree))
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 32.0.sp,
                        fontWeight = FontWeight(300.0.toInt()),
                        fontStyle = FontStyle.Normal,
                    )
                ) {
                    append(LocalContext.current.getString(R.string.temp_celsius))
                }
            },
            modifier = Modifier,
            style = TextStyle(fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground),
        )

        Row {
            SubItems(
                title = LocalContext.current.getString(R.string.humidity),
                value = "${currentWeather.current?.humidity ?: ".."}%",
            )
            Spacer(modifier = Modifier.width(8.dp))
            SubItems(
                title = LocalContext.current.getString(R.string.str_uv),
                value = (currentWeather.current?.uv ?: "..").toString()
            )
            Spacer(modifier = Modifier.width(8.dp))
            SubItems(
                title = LocalContext.current.getString(R.string.str_feels_like),
                value = (currentWeather.current?.feelslikeC ?: "...").toString()
            )
        }
    }
}


@Composable
fun SubItems(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
        )
    }
}


@Composable
fun HourlyForecasting(hours: List<CurrentModel>) {

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = LocalContext.current.getString(R.string.hourly_status),
            style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(hours) { hour ->
                HourItem(
                    time = hour.time.toString().parseDateTo(
                        Constants.INPUT_DATE_TO_TIME_FORMAT,
                        Constants.OUTPUT_TIME_FORMAT
                    ),
                    icon = "${Constants.SCHEMA_URL}${hour.condition.icon}",
                    temp = hour.tempC.toString()
                )

                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }

}

@Composable
fun HourItem(
    time: String,
    icon: String,
    temp: String
) {
    Card {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.0.sp,
                            fontWeight = FontWeight(700.0.toInt()),
                            fontStyle = FontStyle.Normal,
                        )
                    ) {
                        append(temp)
                    }
                    withStyle(
                        style = SpanStyle(
                            baselineShift = BaselineShift.Superscript,
                            fontSize = 8.0.sp,
                            fontWeight = FontWeight(300.0.toInt()),
                            fontStyle = FontStyle.Normal,
                        )
                    ) { // AnnotatedString.Builder
                        append(LocalContext.current.getString(R.string.temp_degree))
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.0.sp,
                            fontWeight = FontWeight(300.0.toInt()),
                            fontStyle = FontStyle.Normal,
                        )
                    ) {
                        append(LocalContext.current.getString(R.string.temp_celsius))
                    }
                },
                modifier = Modifier,
                style = TextStyle(fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground),
            )

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                contentDescription = LocalContext.current.getString(R.string.weather_icon_description),
                model = icon,
                placeholder = painterResource(
                    id = R.drawable.current_weather
                ),
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
            )

            Text(
                text = time,
                style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
            )

        }
    }
}