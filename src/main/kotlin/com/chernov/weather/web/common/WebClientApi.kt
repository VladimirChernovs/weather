package com.chernov.weather.web.common

import io.netty.channel.ChannelOption
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

/**
 * WebClient connection's settings
 */
@Component
class WebClientApi(private val properties: SiteProperties) {

    fun getWebClient(): WebClient {

        val httpClient = HttpClient.create()
                .tcpConfiguration { client ->
                    client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.timeout)
                }

        return WebClient.builder()
                .clientConnector(ReactorClientHttpConnector(httpClient))
                .build()
    }

}
