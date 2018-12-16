package com.chernov.weather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 *  Weather project
 */
@SpringBootApplication
class WeatherApplication

/**
 *  Start application
 */
fun main(args: Array<String>) {
    runApplication<WeatherApplication>(*args)
}


