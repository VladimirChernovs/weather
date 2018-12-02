package com.chernov.weather

import com.chernov.weather.cities.City
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.test.test

internal class IntegrationTests {

    private val application = Application(8181)
    private val client = WebClient.create("http://localhost:8181")

    @BeforeAll
    fun beforeAll() {
        application.start()
    }

    @Test
    fun `Find all cities on via cities endpoint`() {
        client.get().uri("/api/cities")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux<City>()
                .test()
                .expectNextMatches { it.name == "New York" && it.weather == "warm" }
                .expectNextMatches { it.name == "London" && it.weather == "wet" }
                .expectNextMatches { it.name == "Moscow" && it.weather == "cold" }
                .verifyComplete()
    }

    @AfterAll
    fun afterAll() {
        application.stop()
    }
}

