/*
package com.chernov.weather.services

import com.chernov.weather.config.Routes
import com.chernov.weather.web.CityHandler
import com.chernov.weather.web.common.SiteProperties
import com.chernov.weather.web.common.WebClientApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
@SpringBootTest
class WeatherServiceTest() {

    private lateinit var weatherService: WeatherService

    @Autowired
    private lateinit var properties: SiteProperties

    private lateinit var webClientApi: WebClientApi

    @BeforeEach
    fun before() {

        webClientApi = WebClientApi(properties)

        weatherService = WeatherService(properties, webClientApi)
    }

    @Test
    fun inCity() {

        val serverResponseMono = weatherService.inCity("New York", "json")

        StepVerifier
                .create<ServerResponse>(serverResponseMono)
                .expectNextMatches { s -> s.statusCode() == HttpStatus.OK && s.headers().size == 9 }
                .verifyComplete()

    }

    @Test
    fun inMedias() {
    }
}*/
