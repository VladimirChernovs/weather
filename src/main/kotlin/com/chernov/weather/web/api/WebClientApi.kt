package com.chernov.weather.web.api

import io.netty.channel.ChannelOption
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

/**
 * WebClient connections settings
 */
@Component
class WebClientApi(val properties: SiteProperties) {

    internal fun createWebClient(): WebClient {
        return this.createWebClient(properties.timeout)
    }

    private fun createWebClient(timeout: Int): WebClient {

        val httpClient = getHttpClient(timeout)

        return WebClient.builder()
                .clientConnector(ReactorClientHttpConnector(httpClient))
                .build()
    }

    private fun getHttpClient(timeout: Int): HttpClient {
        return HttpClient.create()
                .tcpConfiguration { client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout) }
    }

}
