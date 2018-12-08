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
class WebClientApi(private val properties: SiteProperties) {

    fun getWebClient(timeout: Int = properties.timeout): WebClient {

        val httpClient = HttpClient.create()
                .tcpConfiguration { client ->
                    client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                }

        return WebClient.builder()
                .clientConnector(ReactorClientHttpConnector(httpClient))
                .build()
    }

}
