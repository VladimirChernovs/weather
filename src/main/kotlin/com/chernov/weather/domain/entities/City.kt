package com.chernov.weather.domain.entities

import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

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
    var id: Long = 0,
    @Column(name = "CITY_ID")
    var cityId: Long = 0,
    @Column(name = "NAME")
    var name: String = "",
    @Embedded
    var weather: Weather = Weather("{}", ""),
    @UpdateTimestamp
    @Column(name = "TIME")
    var updateTime: LocalDateTime = LocalDateTime.MIN
)