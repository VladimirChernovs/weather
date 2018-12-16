package com.chernov.weather.config

import com.chernov.weather.web.CityHandler
import com.chernov.weather.web.common.internalServerError
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.reactive.function.server.router
import java.net.URI

/**
 *  REST requests mapping
 */
@Configuration
class Routes(private val cityHandler: CityHandler) {

    @Bean
    fun router() = router {
        accept(TEXT_HTML).nest {
            GET("/") { permanentRedirect(URI("index.html")).build() }
        }
        "/api".nest {
            accept(APPLICATION_JSON).nest {
                GET("/cities", cityHandler::findAll)
                GET("/city", cityHandler::findOneByName)
                GET("/city/{name}", cityHandler::findOneByNamePath)
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
