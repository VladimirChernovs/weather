package com.chernov.weather.services

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdateService(private val cityService: CityService,
                    private val openWeatherService: OpenWeatherService
) {
    @Scheduled(fixedDelayString = "\${update.delay}")
    fun scheduler() {

        val cities = cityService.findAll()
        // TODO
    }
}