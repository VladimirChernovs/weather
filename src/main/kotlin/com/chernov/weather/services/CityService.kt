package com.chernov.weather.services

import com.chernov.weather.domain.entities.City
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CityService {

    fun findAll(): Flux<City> = Flux.empty()

    fun findOne(name: String): Mono<City> = Mono.empty()

    fun create(city: String): Mono<City> = Mono.empty()

    fun deleteOne(name: String): Mono<String> = Mono.empty()

}