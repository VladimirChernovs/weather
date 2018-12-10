package com.chernov.weather.services

import com.chernov.weather.domain.dto.CityWeatherDTO
import com.chernov.weather.domain.dto.WeatherDTO
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
class OpenWeatherService(private val properties: SiteProperties,
                         private val webClientApi: WebClientApi) {

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

    private fun getUri(city: String, media: String): WebClient.RequestHeadersSpec<*> = webClientApi.getWebClient()
            .get()
            .uri {
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host(properties.host).path(properties.path!!)
                        .queryParam("q", city)
                        .queryParam("appid", properties.appid)
                        .queryParam("mode", media)
                        .encode()
                        .build()
                        .toUri()
            }

    fun inCityMedias(city: String): Mono<CityWeatherDTO> {

        val retrieve = getUri(city, "json").retrieve()
        val dto = retrieve.bodyToMono(WeatherDTO::class.java)
        val json = retrieve.bodyToMono(String::class.java)

        val xml = getUri(city, "xml").retrieve().bodyToMono(String::class.java)

        val zip = Mono.zip(dto, json, xml)
        return zip.map { n ->
            val cityId = java.lang.Long.parseLong(n.t1.id)
            CityWeatherDTO(cityId, n.t2, n.t3)
        }
    }
}
