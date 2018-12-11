package com.chernov.weather.services

class CityExistException : RuntimeException("City already exist!")
class CityNotExistException : IllegalArgumentException("Nothing to delete, city not found!")
