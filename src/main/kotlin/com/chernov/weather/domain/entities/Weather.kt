package com.chernov.weather.domain.entities

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Lob

/**
 * weather responses from the site
 */
@Embeddable
data class Weather(
        @Lob
        @Column(name = "JSON")
        val json: String
        ,
        @Lob
        @Column(name = "XML")
        val xml: String
)
