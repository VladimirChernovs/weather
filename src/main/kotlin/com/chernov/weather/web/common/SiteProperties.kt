package com.chernov.weather.web.common


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Weather's site settings
 */
@Component
@ConfigurationProperties(prefix = "site")
data class SiteProperties (
    var appid: String? = null,
    var host: String? = null,
    var path: String? = null,
    var timeout: Int = 0
)

