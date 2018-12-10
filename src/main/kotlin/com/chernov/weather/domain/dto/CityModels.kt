package com.chernov.weather.domain.dto

/**
 *  DTOs
 */
data class CityDTO(val name: String)

data class CityWeatherDTO(val cityId: Long, val json: String, val xml: String)
