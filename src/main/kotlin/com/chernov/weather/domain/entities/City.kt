package com.chernov.weather.domain.entities

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

/**
 * City info
 */
@Entity
@Table(name = "CITIES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
data class City(

        @Id
        @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
        @Column(name = "ID")
        val id: Long
        ,
        @Column(name = "CITY_ID", unique = true)
        val cityId: Long
        ,
        @Column(name = "NAME")
        val name: String
        ,
        @Embedded
        val weather: Weather
        ,
        @UpdateTimestamp
        @Column(name = "TIME")
        val updateTime: LocalDateTime
)

