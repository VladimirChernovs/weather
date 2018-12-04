package com.city.weather.services;

public class CityExistException extends RuntimeException {
    public CityExistException() {
        super("City already exist!");
    }
}
