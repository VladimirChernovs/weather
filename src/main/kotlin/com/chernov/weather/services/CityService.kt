package com.chernov.weather.services

import com.chernov.weather.web.cities.City
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CityService {

    private var cities = mutableListOf(
            City("New York", "warm"),
            City("London", "wet"),
            City("Moscow", "cold")
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