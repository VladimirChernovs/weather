package com.chernov.weather.services

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdateService(private val cityService: CityService,
                    private val weatherService: WeatherService) {
    @Scheduled(fixedDelayString = "\${update.delay}")
    fun scheduler() {
        try {
            cityService.findAll().subscribe { city ->
                cityService.updateCity(weatherService.inMedias(city))
            }
        } catch (e: Exception) {
            //TODO
            println(e)
        }
    }
}