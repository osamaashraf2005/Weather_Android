Here is a detailed and structured **README** file for your weather application project:

---

# Weather Application

## Overview

This is a **Weather App** built using **Kotlin**, **Jetpack Compose**, and **MVVM architecture**. The app allows users to search for weather information by entering a city name or by using their current location. The weather data is fetched from the **OpenWeatherMap API** and displayed in a user-friendly interface, including weather icons, temperature, humidity, and other relevant details.

## Features

- Search weather by **city name**.
- Fetch weather data using the **current location**.
- Display detailed weather information including:
  - City name
  - Temperature (°C)
  - Humidity (%)
  - Weather condition (icon and description)
- **Auto-load** the last searched city on app launch.
- **Location permissions** request for fetching weather by current location.
- **Error handling** for network or location access issues.
- **Caching of weather icons** to reduce network calls.
  
## Tech Stack

- **Kotlin**: Programming language for Android development.
- **Jetpack Compose**: For building UI.
- **MVVM (Model-View-ViewModel)**: Architecture pattern for separation of concerns.
- **Dagger Hilt**: For dependency injection.
- **Retrofit**: For network operations.
- **Coil**: For loading and caching weather icons.
- **SharedPreferences**: For saving the last searched city.
- **JUnit**: For unit testing.
- **MockK**: For mocking dependencies in unit tests.

## Project Structure

- **data**
  - `WeatherRepository.kt`: Defines the repository interface.
  - `WeatherRepositoryImpl.kt`: Implements the repository for fetching data from the API.
  - `WeatherService.kt`: Retrofit API service for making HTTP requests to OpenWeatherMap API.
  - `WeatherMapper.kt`: Maps the API response to the domain model.
  - `ExceptionTransformations.kt`: Contains custom transformations for handling exceptions.

- **domain**
  - `Weather.kt`: Domain model representing the weather data.
  - `FetchWeatherUseCase.kt`: Use case for fetching weather by city or coordinates.
  
- **presentation**
  - `MainActivity.kt`: Entry point of the app.
  - `CityInputScreen.kt`: Screen for entering a city name or fetching the weather by current location.
  - `WeatherDisplayScreen.kt`: Screen that displays the fetched weather details.
  - `WeatherNavHost.kt`: Handles navigation between screens.
  - `WeatherViewModel.kt`: ViewModel for managing weather data and interactions between the repository and UI.
  - `UiState.kt`: Represents the UI state for managing loading, success, and error states.

## API Integration

The app uses the **OpenWeatherMap API** to fetch real-time weather data. You need an API key to access the API.

### API Endpoints

- **By City Name**:
  ```
  https://api.openweathermap.org/data/2.5/weather?q={city_name}&appid={API_KEY}
  ```
  
- **By Coordinates**:
  ```
  https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API_KEY}
  ```

### Weather Icon

The weather icon is fetched using the `icon` value from the API response:
```
https://openweathermap.org/img/wn/{icon_code}@2x.png
```

## Permissions

The app requires the following permissions:
- **Internet Permission**: To fetch weather data from the OpenWeatherMap API.
- **Location Permission**: To fetch weather data based on the user's current location.

## How to Run the Project

1. **Clone the repository**:

    ```bash
    git clone https://github.com/your-repo-url.git
    ```

2. **Set up API key**:

   - Go to [OpenWeatherMap](https://openweathermap.org/) and create an account to get your free API key.
   - Add your API key in the `WeatherService.kt` or any other configuration file.

3. **Run the project** in Android Studio:
    - Open the project in Android Studio.
    - Sync Gradle and run the project on an emulator or physical device.

## Testing

Unit tests are written using **JUnit** and **MockK** for mocking the API and repository layers. Compose UI tests are written using the **Compose Testing Library**.

### Running Tests

- To run the unit tests:
    ```bash
    ./gradlew test
    ```
- To run the UI tests:
    ```bash
    ./gradlew connectedAndroidTest
    ```

### Key Test Files:

- **ViewModel Tests**: Test fetching and handling of weather data in `WeatherViewModel`.
- **Repository Tests**: Test the interaction with `WeatherApiService` and ensure correct data mapping.
- **UI Tests**: Test the UI behavior and navigation in `CityInputScreen` and `WeatherDisplayScreen`.

## Screenshots

### City Input Screen
*Add a screenshot here.*

### Weather Display Screen
*Add a screenshot here.*

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

This **README** file gives an overview of your weather app, the technology stack used, the project structure, and instructions for setup, testing, and running the app. Let me know if you need further adjustments!