package com.lbg.techtest.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lbg.techtest.data.entity.Day
import com.lbg.techtest.data.entity.WeatherEntity
import com.lbg.techtest.domain.utils.Resource
import com.lbg.techtest.domain.utils.toast
import com.lbg.techtest.presentation.core_ui.FullScreenLoading
import com.lbg.techtest.presentation.viewmodel.ForecastingViewModel

@Composable
fun ForecastingScreen(
    viewModel: ForecastingViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getForecastedWeather()
    }

    val state by viewModel.state.collectAsState()

    var forecastedWeatherState by remember {
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
                forecastedWeatherState = pageState.data as WeatherEntity
        }

        else -> {}
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            forecastedWeatherState.forecast?.forecastday?.let {
                items(items = it) { forecastDay ->
                    DaysItem(
                        date = forecastDay.date!!,
                        days = forecastDay.day!!,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DaysItem(
    viewModel: ForecastingViewModel,
    date: String,
    days: Day
) {
    Card(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text( //date
                    text = viewModel.parseDateToDay(date),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text( //condition
                    text = days.condition?.text!!,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            GlideImage(
                contentDescription = "Weather icon",
                model = "https:${days.condition?.icon}",
                /*placeholder = painterResource(
                    id = R.drawable.current_weather
                ),*/
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text( //Max
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.0.sp,
                                fontWeight = FontWeight(700.0.toInt()),
                                fontStyle = FontStyle.Normal,
                            )
                        ) {
                            append((days.maxtempC ?: "..").toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontSize = 8.0.sp,
                                fontWeight = FontWeight(300.0.toInt()),
                                fontStyle = FontStyle.Normal,
                            )
                        ) { // AnnotatedString.Builder
                            append("o")
                        }
                    },
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text( //Min
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.0.sp,
                                fontWeight = FontWeight(700.0.toInt()),
                                fontStyle = FontStyle.Normal,
                            )
                        ) {
                            append((days.mintempC ?: "..").toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                baselineShift = BaselineShift.Superscript,
                                fontSize = 8.0.sp,
                                fontWeight = FontWeight(300.0.toInt()),
                                fontStyle = FontStyle.Normal,
                            )
                        ) { // AnnotatedString.Builder
                            append("o")
                        }
                    },
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

        }

    }
}