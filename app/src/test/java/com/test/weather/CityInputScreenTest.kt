package com.test.weather

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.forecast.weather.task.feature.presentation.CityInputScreen
import org.junit.Rule
import org.junit.Test

class CityInputScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `enter city name and click get weather button`() {
        // Set the Composable under test
        composeTestRule.setContent {
            CityInputScreen(navController = mockk(relaxed = true)) // Mock navController
        }

        // Enter a city name
        val cityName = "Test City"
        composeTestRule.onNodeWithText("Enter City Name").performTextInput(cityName)

        // Click the "Get Weather" button
        composeTestRule.onNodeWithText("Get Weather").performClick()

        // Verify the city name text field has the correct value
        composeTestRule.onNodeWithText(cityName).assertIsDisplayed()
    }

    @Test
    fun `last searched city is preloaded in text field`() {
        // Set the Composable under test with a preloaded city
        composeTestRule.setContent {
            CityInputScreen(navController = mockk(relaxed = true), viewModel = mockk(relaxed = true)) // Mock viewModel
        }

        // Verify that the text field is preloaded with the last searched city
        composeTestRule.onNodeWithText("Test City").assertExists()
    }
}