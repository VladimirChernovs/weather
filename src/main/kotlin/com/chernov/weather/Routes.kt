package com.chernov.weather

import com.chernov.weather.cities.CityHandler
import com.chernov.weather.common.internalServerError
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.router
import java.net.URI

class Routes(private val cityHandler: CityHandler) {

    fun router() = router {
        accept(TEXT_HTML).nest {
            GET("/") { permanentRedirect(URI("index.html")).build() }
        }
        "/api".nest {
            accept(APPLICATION_JSON).nest {
                GET("/cities", cityHandler::findAll)
                GET("/city", cityHandler::findOne)
                POST("/city", cityHandler::create)
                DELETE("/city", cityHandler::deleteOne)
            }
        }
        resources("/**", ClassPathResource("static/"))
    }
    .filter { request, next ->
        try {
            next.handle(request)
        } catch (ex: Exception) {
            internalServerError(ex)
        }
    }
}