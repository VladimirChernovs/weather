package com.chernov.weather

import com.chernov.weather.services.CityService
import com.chernov.weather.web.Routes
import com.chernov.weather.web.cities.City
import com.chernov.weather.web.cities.CityHandler
import com.chernov.weather.web.common.dotenv
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import reactor.netty.DisposableServer
import reactor.netty.http.server.HttpServer
import reactor.test.test
import java.time.Duration

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

class Application(port: Int = dotenv["PORT"]?.toInt() ?: 8080) {

    private val httpHandler: HttpHandler
    private val server: HttpServer
    private var disposableServer: DisposableServer? = null

    fun start() {
        disposableServer = server.bindNow(Duration.ofSeconds(5))
    }

    fun startAndAwait() {
        server.bindUntilJavaShutdown(Duration.ofSeconds(5)) {
            disposableServer = it
        }
    }

    fun stop() {
        disposableServer?.disposeNow()
    }

    init {
        val context = GenericApplicationContext().apply {
            beans().initialize(this)
            refresh()
        }
        httpHandler = WebHttpHandlerBuilder.applicationContext(context).build()
        val adapter = ReactorHttpHandlerAdapter(httpHandler)
        server = HttpServer.create().host("localhost").handle(adapter).port(port) // port)
    }

    fun beans() = beans {
        bean<CityHandler>()
        bean<CityService>()
        bean<Routes>()
        bean(WebHttpHandlerBuilder.WEB_HANDLER_BEAN_NAME) {
            RouterFunctions.toWebHandler(ref<Routes>().router())
        }
    }

}
