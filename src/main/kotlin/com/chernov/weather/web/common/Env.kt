package com.chernov.weather.web.common

import io.github.cdimascio.dotenv.dotenv
import io.github.cdimascio.swagger.Validate

/**
 *  Request validator
 */
val validate = Validate.configure("static/api.yaml") { status, messages ->
    Error(status.value(), messages)
}

val dotenv = dotenv()