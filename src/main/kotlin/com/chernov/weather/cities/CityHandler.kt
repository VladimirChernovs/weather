package com.chernov.weather.cities

import com.chernov.weather.common.validate
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

class CityHandler(val cityService: CityService) {

    fun findAll(req: ServerRequest): Mono<ServerResponse> = validate
        .request(req) {
            ok().body(cityService.findAll())
        }

    fun findOne(req: ServerRequest): Mono<ServerResponse> = validate
        .request(req) {
            ok().body(cityService.findOne(getTheNameParameter(req)))
        }

    fun create(req: ServerRequest) = validate
        .request(req)
        .withBody(CityDTO::class.java) { city ->
            ok().body(cityService.create(City(city.name)))
        }

    fun deleteOne(req: ServerRequest): Mono<ServerResponse> = validate
            .request(req) {
                ok().body(cityService.deleteOne(getTheNameParameter(req)))
            }

    private fun getTheNameParameter(req: ServerRequest) = req.queryParam("name").get()

}