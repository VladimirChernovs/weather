package com.chernov.weather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 *  Weather project
 */
@SpringBootApplication
class WeatherApp

/**
 *  Start application
 */
fun main(args: Array<String>) {
    runApplication<WeatherApp>(*args)
}