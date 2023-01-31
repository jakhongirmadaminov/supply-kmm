package uz.uzkass.smartpos.supply.core.utils

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

private const val KTOR_TAG = "Http Client"

internal fun HttpClientConfig<*>.logging() = install(Logging) {
    level = LogLevel.ALL
    logger = object : Logger {
        override fun log(message: String) {
            Napier.v(tag = "HTTP Client", message = message)
        }
    }
}