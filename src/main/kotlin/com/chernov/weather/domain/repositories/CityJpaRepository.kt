package com.chernov.weather.domain.repositories

import com.chernov.weather.domain.entities.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *  JPA repository for city entity
 */
@Repository
interface CityJpaRepository : JpaRepository<City, Long> {

    /**
     *  Get city by [id]
     */
    fun findByCityId(id: Long): City

    /**
     *  Get city by [name]
     */
    fun findByName(name: String): City

    /**
     *  Check city existence by [name]
     */
    fun existsCityByName(name: String): Boolean
}