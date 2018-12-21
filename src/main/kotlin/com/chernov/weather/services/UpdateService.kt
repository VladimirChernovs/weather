package com.chernov.weather.services

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/**
 *  Update weather service
 */
@Service
class UpdateService(private val cityService: CityService, private val weatherService: WeatherService) {
    /**
     *  Scheduled weather's update
     */
    @Scheduled(fixedDelayString = "\${update.delay}")
    fun scheduler() {
        cityService.findAll().subscribe { city ->
            cityService.updateCity(weatherService.inMedias(city))
        }
    }
}