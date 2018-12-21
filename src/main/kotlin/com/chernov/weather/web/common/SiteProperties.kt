package com.chernov.weather.web.common

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Weather's site settings
 */
@Component
@ConfigurationProperties(prefix = "site")
class SiteProperties {
    lateinit var appid: String
    lateinit var host: String
    lateinit var path: String
    var timeout: Int = 0
}