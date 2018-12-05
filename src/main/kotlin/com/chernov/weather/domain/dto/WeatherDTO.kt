package com.chernov.weather.domain.dto

data class WeatherDTO(val id: String, val dt: String, val clouds: Clouds, val coord: Coord, val wind: Wind,
                      val cod: String, val visibility: String, val sys: Sys, val name: String, val base: String,
                      val weather: Array<Weather>, val main: Main
)

data class Sys(val message: String, val id: String, val sunset: String, val sunrise: String,
               val type: String, val country: String
)

data class Clouds(val all: String)

data class Coord(val lon: String, val lat: String)

data class Wind(val gust: String, val speed: String, val deg: String)

data class Weather(val id: String, val icon: String, val description: String, val main: String)

data class Main(val humidity: String, val pressure: String, val temp_max: String,
                val temp_min: String, val temp: String
)

