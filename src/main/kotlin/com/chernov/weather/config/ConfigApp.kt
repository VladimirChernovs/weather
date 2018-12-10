package com.chernov.weather.config

import com.chernov.weather.utl.LoggingAspect
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.core.env.Environment


@Configuration
@EnableCaching
@EnableAspectJAutoProxy
class ConfigApp

@Bean
fun loggingAspect(env: Environment) = LoggingAspect(env)

