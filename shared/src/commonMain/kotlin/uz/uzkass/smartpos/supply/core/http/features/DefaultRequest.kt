package uz.uzkass.smartpos.supply.core.http.features

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom

private const val BASE_URL = "https://api-devsupply.smartpos.uz/"

internal fun HttpClientConfig<*>.defaultRequest() =
    install(DefaultRequest) {
        url.protocol = URLProtocol.HTTPS
        url.withBaseUrl()
    }

private fun URLBuilder.withBaseUrl(url: String = BASE_URL): URLBuilder {
    val baseUrl = Url(url)
    val urlBuilder = URLBuilder(baseUrl).apply { encodedPath += this@withBaseUrl.encodedPath }
    return takeFrom(urlBuilder)
}