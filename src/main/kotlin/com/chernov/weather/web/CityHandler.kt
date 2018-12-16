package com.chernov.weather.web

import com.chernov.weather.domain.dto.CityDTO
import com.chernov.weather.domain.entities.City
import com.chernov.weather.services.CityService
import com.chernov.weather.services.WeatherService
import com.chernov.weather.web.common.validate
import org.springframework.core.env.Environment
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.Charset

/**
 *  Handle requests from router
 */
@Component
class CityHandler(private val env: Environment,
                  private val weatherService: WeatherService,
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
        getCityBodyByName(cityName, req)
    }

    /**
     *  Get city by name path. [req] - server request
     */
    fun findOneByNamePath(req: ServerRequest): Mono<ServerResponse> = validate.request(req) {
        val cityName = req.pathVariable("name")
        getCityBodyByName(cityName, req)
    }

    /**
     *  Get city by global id (from the site). [req] - server request
     */
    fun findOneByGlobId(req: ServerRequest): Mono<ServerResponse> = validate.request(req) {
        val gid = req.pathVariable("gid")
        val cityBody = cityService.findOneByGlobId(gid.toLong())
        getCityEtc(req, cityBody)
                .switchIfEmpty(weatherService.inCityByGlobId(gid, getMediaType(req).subtype))

    }

    private fun getCityBodyByName(cityName: String, req: ServerRequest): Mono<ServerResponse> {
        val cityBody = cityService.findOneByName(cityName)
        return getCityEtc(req, cityBody)
                .switchIfEmpty(weatherService.inCityByName(cityName, getMediaType(req).subtype))
    }

    private fun getCityEtc(req: ServerRequest, cityBody: Mono<City>): Mono<ServerResponse> = cityBody.flatMap {
        ok()
                .contentType(getMediaType(req))
                .header("db-update-time", it.updateTime.toString())
                .body(cityBody.map { city ->
                    when (getMediaType(req)) {
                        MediaType.APPLICATION_XML -> city.weather.xml
                        else -> city.weather.json
                    }
                })
    }

    /**
     *  Create city by name. [req] - server request
     */
    fun create(req: ServerRequest) = validate.request(req).withBody(CityDTO::class.java) {
        created(URI.create("http://localhost:${env.getProperty("server.port")}" +
                "/city/${
                (URLEncoder.encode(it.name, Charset.defaultCharset())).replace("+", "%20")
                }"))
                .body(cityService.addOne(it))
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
