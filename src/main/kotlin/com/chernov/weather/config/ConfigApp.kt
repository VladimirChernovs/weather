package com.chernov.weather.config

import com.chernov.weather.services.CityService
import com.chernov.weather.services.CityServiceJpaImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableCaching
@EnableScheduling
class ConfigApp {

    @Autowired
    private lateinit var cityServiceJpaImp: CityServiceJpaImp

    @Bean
    fun cityService(): CityService = cityServiceJpaImp

}
