package com.chernov.weather.domain.repositories

import com.chernov.weather.domain.entities.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CityRepository : JpaRepository<City, Long> {
    fun findByCityId(id: Long?): Optional<City>
    fun findByName(name: String): City
    fun existsCityByName(city: String): Boolean
}