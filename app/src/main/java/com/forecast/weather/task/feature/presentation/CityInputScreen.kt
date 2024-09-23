package com.forecast.weather.task.feature.presentation

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.forecast.weather.task.feature.presentation.viewmodel.WeatherViewModel

@Composable
fun CityInputScreen(navController: NavHostController, viewModel: WeatherViewModel = hiltViewModel()) {
    var cityName by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val location by viewModel.location.observeAsState()

    // Initialize FusedLocationClient
    LaunchedEffect(Unit) {
        viewModel.initializeFusedLocationClient(context)
        val lastCity = viewModel.getLastSearchedCity()
        if (lastCity != null) {
            cityName = TextFieldValue(lastCity)
        }
    }

    // Location permission request launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                viewModel.getCurrentLocation(context)
            } else {
                Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Observe the location and navigate when available
    LaunchedEffect(location) {
        location?.let {
            navController.navigate("weatherDisplay/${it.latitude}/${it.longitude}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // TextField to input city name
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter City Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to search weather by city name
        Button(
            onClick = {
                if (cityName.text.isNotEmpty()) {
                    navController.navigate("weatherDisplay/${cityName.text}")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to use the current location for weather search
        Button(
            onClick = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PermissionChecker.PERMISSION_GRANTED
                ) {
                    // Location permission is already granted, fetch location
                    viewModel.getCurrentLocation(context)
                } else {
                    // Request location permission
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Use Current Location")
        }
    }
}