package uz.uzkass.smartpos.supply

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect class Platform() {
    val name: String
}

expect fun getPlatform(): Platform


expect fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient

expect fun initLogger()