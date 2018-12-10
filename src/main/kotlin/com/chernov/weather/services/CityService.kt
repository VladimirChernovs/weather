package com.chernov.weather.services

import com.chernov.weather.domain.entities.City
import com.chernov.weather.domain.entities.Weather
import com.chernov.weather.domain.repositories.CityRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class CityService(private val cityRepository: CityRepository) {

    private var cities = mutableListOf(
            City(1, 1, "New York", Weather("json", "xml"), LocalDateTime.now()),
            City(2, 2, "London", Weather("json", "xml"), LocalDateTime.now()),
            City(3, 3, "Moscow", Weather("json", "xml"), LocalDateTime.now())
    )

    fun findAll(): Flux<City> = Flux.fromIterable(cities)

    fun findOne(name: String): Mono<City> {
        val find = cities.find { it.name == name }
        return when (find) {
            null -> throw IllegalArgumentException("City not found!")
            else -> Mono.just<City>(find)
        }
    }

    fun create(city: City): Mono<City> {
        cities.add(city)
        return Mono.just(city)
    }

    fun deleteOne(name: String): Mono<String> {
        val find = cities.removeIf { it.name == name }
        return when (find) {
            true -> Mono.just("City $name removed")
            else -> throw IllegalArgumentException("Nothing to delete, city not found!")
        }
    }

}