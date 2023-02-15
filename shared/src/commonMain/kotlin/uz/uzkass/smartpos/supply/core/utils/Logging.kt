package uz.uzkass.smartpos.supply.core.utils

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

internal fun HttpClientConfig<*>.logging() = install(Logging) {
    level = LogLevel.ALL
    logger = object : Logger {
        override fun log(message: String) {
           println("HTTP Client message: $message)")
        }
    }
}