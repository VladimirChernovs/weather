package com.chernov.weather.services

import com.chernov.weather.domain.entities.City
import com.chernov.weather.domain.entities.Weather
import com.chernov.weather.web.common.SiteProperties
import com.chernov.weather.web.common.WebClientApi
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

/**
 * API for weather site
 */
@Service
class WeatherService(private val properties: SiteProperties,
                     private val webClientApi: WebClientApi) {

    /**
     *  Get weather from the site
     *  [city] - city name
     *  [media] - media type
     */
    @Cacheable("weather")
    fun inCity(city: String, media: String): Mono<ServerResponse> = getUri(city, media)
            .exchange()
            .flatMap { mapper: ClientResponse ->
                ServerResponse.status(mapper.statusCode())
                        .headers { c ->
                            mapper.headers().asHttpHeaders()
                                    .forEach { key, value -> c[key] = value }
                        }
                        .body(mapper.bodyToMono(String::class.java), String::class.java)
                        .cache()
            }

    private fun getUri(city: String, media: String): WebClient.RequestHeadersSpec<*> =
            webClientApi.getWebClient()
            .get()
            .uri {
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host(properties.host).path(properties.path)
                        .queryParam("q", city)
                        .queryParam("appid", properties.appid)
                        .queryParam("mode", media)
                        .encode()
                        .build()
                        .toUri()
            }

    /**
     *  Get JSON & XML media for the [city] from the site
     */
    fun inMedias(city: City): Mono<City> {
        val retrieve = getUri(city.name, "json").retrieve()
        val json = retrieve.bodyToMono(String::class.java)
        val xml = getUri(city.name, "xml").retrieve().bodyToMono(String::class.java)
        val zip = Mono.zip(json, xml)
        return zip.map { n ->
            city.copy(weather = Weather(n.t1, n.t2))
        }
    }

}
