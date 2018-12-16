package com.chernov.weather.utl

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.net.InetAddress

/**
 *  API uri informer
 */
@Component
class AppStartupRunner(private val env: Environment) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        val protocol = "http"
        var hostAddress = "localhost"
        try {
            hostAddress = InetAddress.getLocalHost().hostAddress
        } catch (e: Exception) {
            log.warn("The host name could not be determined, using `localhost` as fallback")
        }

        log.info("\n----------------------------------------------------------\n\t" +
                "API for application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "External: \t{}://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("server.port"),
                protocol,
                hostAddress,
                env.getProperty("server.port"),
                env.activeProfiles)
    }

}