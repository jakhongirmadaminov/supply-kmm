package uz.uzkass.smartpos.supply.core.utils

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun HttpClientConfig<*>.contentNegotiation() = install(ContentNegotiation) {
    json(
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    )
}