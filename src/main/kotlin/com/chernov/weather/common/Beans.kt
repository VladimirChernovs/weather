package com.chernov.weather.common

import com.chernov.weather.Routes
import com.chernov.weather.cities.CityHandler
import com.chernov.weather.cities.CityService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.server.adapter.WebHttpHandlerBuilder

fun beans() = beans {
    bean<CityHandler>()
    bean<CityService>()
    bean<Routes>()
    bean(WebHttpHandlerBuilder.WEB_HANDLER_BEAN_NAME) {
        RouterFunctions.toWebHandler(ref<Routes>().router())
    }
}

class Foo
