package com.chernov.weather.services

/**
 *  City already exist Exception
 */
class CityExistException : RuntimeException("City already exist!")

/**
 *  City not exist Exception
 */
class CityNotExistException : IllegalArgumentException("Nothing to delete, city not found!")
