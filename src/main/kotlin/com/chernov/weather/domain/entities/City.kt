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
        val id: Long? = null
        ,
        @Column(name = "CITY_ID", unique = true)
        val cityId: Long? = null
        ,
        @Column(name = "NAME")
        val name: String? = null
        ,
        @Embedded
        val weather: Weather = Weather("{}", "")
        ,
        @UpdateTimestamp
        @Column(name = "TIME")
        val updateTime: LocalDateTime? = null
)

