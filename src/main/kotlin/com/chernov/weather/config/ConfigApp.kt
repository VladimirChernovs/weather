package com.chernov.weather.config

import com.chernov.weather.domain.repositories.CityJpaRepository
import com.chernov.weather.services.CityService
import com.chernov.weather.services.CityServiceImp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableCaching
@EnableAspectJAutoProxy
class ConfigApp {

    @Autowired
    private val cityRepository: CityJpaRepository? = null

    @Bean
    fun cityService(): CityService = CityServiceImp(cityRepository!!)

}
