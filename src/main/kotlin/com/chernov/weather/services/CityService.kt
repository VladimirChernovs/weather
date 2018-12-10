package com.chernov.weather.services

import com.chernov.weather.domain.dto.CityDTO
import com.chernov.weather.domain.entities.City
import com.chernov.weather.domain.repositories.CityRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.empty
import reactor.core.publisher.Mono.just

@Service
class CityService(private val cityRepository: CityRepository) {

    fun findAll(): Flux<City> = Flux.fromIterable(cityRepository.findAll())

    fun findOne(name: String): Mono<City> = try {
        just(cityRepository.findByName(name))
    } catch (e: EmptyResultDataAccessException) {
        empty()
    }

    fun addOne(cityDto: CityDTO): Mono<City> {
        val cityName = cityDto.name
        if (cityRepository.existsCityByName(cityName)) throw CityExistException()
        return just(cityRepository.save(City(name = cityName)))
    }

    fun deleteOne(name: String): Mono<City> {
        try {
            val city = cityRepository.findByName(name)
            cityRepository.delete(city)
            return just(city)
        } catch (e: EmptyResultDataAccessException) {
            throw CityNotExistException()
        }
    }
}

class CityExistException : RuntimeException("City already exist!")
class CityNotExistException : IllegalArgumentException("Nothing to delete, city not found!")

