package com.chernov.weather.config

import com.chernov.weather.services.CityService
import com.chernov.weather.services.WeatherService
import com.chernov.weather.web.CityHandler
import com.chernov.weather.web.common.SiteProperties
import com.chernov.weather.web.common.WebClientApi
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationContext

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [Routes::class, CityHandler::class,
    WeatherService::class, SiteProperties::class, WebClientApi::class, CityService::class])
@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoutesTest {

    @Autowired
    private lateinit var context: ApplicationContext

    @MockBean(name = "userService")
    private lateinit var testClient: WebTestClient

    @BeforeAll
    fun setUp() {
        testClient = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    fun routes() {
        testClient.get().uri("/cities")
                .exchange()
                .expectStatus().isOk


    }

}

