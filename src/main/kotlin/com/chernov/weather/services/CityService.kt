package com.chernov.weather.services

import com.chernov.weather.domain.dto.CityDTO
import com.chernov.weather.domain.entities.City
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *  Abstraction for all types of City's service repositories
 *
 *  Inserts-Gets-Deletes cities in the list
 */
interface CityService {

    /**
     *  Find all saved in the list cities
     */
    fun findAll(): Flux<City>

    /**
     *  Returns the city of the given [name] from the saved list
     */
    fun findOne(name: String): Mono<City>

    /**
     *  Inserts the city in the saved list from [cityDto] json object
     */
    fun addOne(cityDto: CityDTO): Mono<City>

    /**
     *  Deletes the city of the given [name] from the saved list
     */
    fun deleteOne(name: String): Mono<City>

    /**
     *  Update weather for the city
     */
    fun updateCity(city: City)

}