package com.chernov.weather.services

import com.chernov.weather.domain.dto.CityDTO
import com.chernov.weather.domain.entities.City
import com.chernov.weather.domain.repositories.CityJpaRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just

/**
 *  Implementation of City's service for JPA repositories
 *
 *  Inserts-Gets-Deletes cities in the SQL's Data Bases
 */
@Transactional
class CityServiceJpaImp(private val cityRepository: CityJpaRepository) : CityService {

    /**
     *  Find all saved in the list cities
     */
    @Transactional(readOnly = true)
    override fun findAll(): Flux<City> = Flux.fromIterable(cityRepository.findAll())

    /**
     *  Returns the city of the given [name] from the saved list
     */
    @Transactional(readOnly = true)
    override fun findOne(name: String): Mono<City> = try {
        just(cityRepository.findByName(name))
    } catch (e: EmptyResultDataAccessException) {
        empty()
    }

    /**
     *  Inserts the city in the saved list from [cityDto] json object
     */
    override fun addOne(cityDto: CityDTO): Mono<City> {
        val cityName = cityDto.name
        if (cityRepository.existsCityByName(cityName)) throw CityExistException()
        return just(cityRepository.save(City(name = cityName)))
    }

    /**
     *  Deletes the city of the given [name] from the saved list
     */
    override fun deleteOne(name: String): Mono<City> {
        try {
            val city = cityRepository.findByName(name)
            cityRepository.delete(city)
            return just(city)
        } catch (e: EmptyResultDataAccessException) {
            throw CityNotExistException()
        }
    }

    /**
     *  Update weather for the city
     */
    override fun updateCity(city: City) {
        cityRepository.save(city)
    }

}

