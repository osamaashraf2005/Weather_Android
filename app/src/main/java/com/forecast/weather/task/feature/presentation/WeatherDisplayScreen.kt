package com.forecast.weather.task.feature.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.forecast.weather.R
import com.forecast.weather.task.feature.presentation.model.UiState
import com.forecast.weather.task.feature.presentation.viewmodel.WeatherViewModel

@Composable
fun WeatherDisplayScreen(
    cityName: String? = null,
    latitude: Double? = null,
    longitude: Double? = null,
    navController: NavController,  // Pass NavController to handle back navigation
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val weatherData by viewModel.weatherData.observeAsState()
    val error by viewModel.error.observeAsState()
    var isLoading by remember { mutableStateOf(true) }

    // Fetch weather data based on either city name or coordinates
    LaunchedEffect(cityName, latitude, longitude) {
        isLoading = true
        when {
            cityName != null -> viewModel.fetchWeatherByCity(cityName)
            latitude != null && longitude != null -> viewModel.fetchWeatherByLocation(latitude, longitude)
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally  // Center the icon and text
    ) {

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            // Display weather icon
            weatherData?.let { weather ->

                // Use Coil with caching policy
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = weather.icon)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                memoryCachePolicy(CachePolicy.ENABLED)
                                diskCachePolicy(CachePolicy.ENABLED)
                            }).build()
                    ),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(100.dp),  // Adjust size as needed
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                MainInfo(
                    uiState = weather
                )

                InfoTable(
                    uiState = weather
                )
            }

            // Handle errors
            error?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error)
            }

            // Back Button to go back to CityInputScreen
            Button(
                onClick = { navController.popBackStack() },  // Pop the back stack to go back to the previous screen
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                ).padding(top = 16.dp, bottom = 16.dp)
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
fun MainInfo(uiState: UiState) {
    Column(
        modifier = Modifier.padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${uiState.temp}°", color = Color.DarkGray, fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Text(
            text = uiState.name,
            color = Color.DarkGray,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = uiState.description + " with wind speed: " + uiState.windSpeed + " km/h",
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 24.dp)
        )
    }
}

@Composable
fun InfoTable(uiState: UiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(
                Color.LightGray
            )
    ) {
        Row(Modifier.padding(16.dp)) {
            InfoItem(
                iconRes = R.drawable.ic_humidity,
                title = "Humidity",
                subtitle = "H: ${uiState.humidity}%",
                modifier = Modifier.weight(
                    1f
                )
            )
            InfoItem(
                iconRes = R.drawable.ic_sun_full,
                title = "Pressure",
                subtitle = "P: " + uiState.pressure.toString(),
                modifier = Modifier.weight(
                    1f
                )
            )
        }
        Divider(color = Color.Gray, modifier = Modifier.padding(horizontal = 16.dp))
        Row(Modifier.padding(16.dp)) {
            InfoItem(
                iconRes = R.drawable.ic_sun_half,
                title = "Sunrise",
                subtitle = "Rise: 7:30 AM",
                modifier = Modifier.weight(
                    1f
                )
            )
            InfoItem(
                iconRes = R.drawable.ic_sun_half,
                title = "Sunset",
                subtitle = "Set: 4:28 PM",
                modifier = Modifier.weight(
                    1f
                )
            )
        }
    }
}


@Composable
fun InfoItem(@DrawableRes iconRes: Int, title: String, subtitle: String, modifier: Modifier) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 12.dp, end = 8.dp)
                .width(40.dp)
        )
        Column {
            Text(title, color = Color.Transparent)
            Text(subtitle, color = Color.Gray, fontWeight = FontWeight.Bold)
        }
    }
}
