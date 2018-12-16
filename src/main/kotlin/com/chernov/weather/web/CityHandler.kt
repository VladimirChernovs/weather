package com.chernov.weather.web

import com.chernov.weather.domain.dto.CityDTO
import com.chernov.weather.services.CityService
import com.chernov.weather.services.WeatherService
import com.chernov.weather.web.common.validate
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

/**
 *  Handle requests from router
 */
@Component
class CityHandler(private val weatherService: WeatherService,
                  private val cityService: CityService) {

    /**
     *  Get all cities. [req] - server request
     */
    fun findAll(req: ServerRequest): Mono<ServerResponse> = validate.request(req) {
        ok().body(cityService.findAll())
    }

    /**
     *  Get city by name. [req] - server request
     */
    fun findOneByName(req: ServerRequest): Mono<ServerResponse> = validate.request(req) {
        val cityName = getTheNameParameter(req)
        val cityBody = cityService.findOneByName(cityName)
        val mediaType = getMediaType(req)
        cityBody.flatMap {
            ServerResponse.ok()
                    .contentType(mediaType)
                    .header("db-update-time", it.updateTime.toString())
                    .body(cityBody.map { city ->
                        when (mediaType) {
                            MediaType.APPLICATION_XML -> city.weather.xml
                            else -> city.weather.json
                        }
                    })
        }.switchIfEmpty(weatherService.inCity(cityName, mediaType.subtype))
    }

    /**
     *  Create city by name. [req] - server request
     */
    fun create(req: ServerRequest) = validate.request(req).withBody(CityDTO::class.java) {
        ok().body(cityService.addOne(it))
    }

    /**
     *  Delete city by name. [req] - server request
     */
    fun deleteOne(req: ServerRequest): Mono<ServerResponse> = validate.request(req) {
        ok().body(cityService.deleteOne(getTheNameParameter(req)))
    }

    /**
     *  Get media type from [req] - server request
     */
    private fun getMediaType(req: ServerRequest): MediaType {
        val mediaType = req.headers().contentType()
        if (mediaType.isPresent) {
            val type = mediaType.get()
            when (type) {
                MediaType.APPLICATION_XML -> return type
            }
        }
        val mediaMode = req.queryParam("mode")
        if (mediaMode.isPresent) when (mediaMode.get()) {
            "xml" -> return MediaType.APPLICATION_XML
        }
        return MediaType.APPLICATION_JSON
    }

    private fun getTheNameParameter(req: ServerRequest) = req.queryParam("name").get()

}
