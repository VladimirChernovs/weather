package com.chernov.weather.cities

data class CityDTO(val name: String)
data class City(val name: String, val weather: String = "unknown")