package uz.uzkass.smartpos.supply.core.http.features

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.header
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.Url
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import uz.uzkass.smartpos.supply.settings.PreferenceManager

private const val BASE_URL = "https://api-devsupply.smartpos.uz/"
private const val HEADER_SELECTED_LANGUAGE: String = "language"
private const val HEADER_AUTHORIZATION: String = "Authorization"

internal fun HttpClientConfig<*>.defaultRequest() =
    install(DefaultRequest) {
        url.protocol = URLProtocol.HTTPS
        url.withBaseUrl()
    }

internal fun HttpClientConfig<*>.defaultRequest(preferenceManager: PreferenceManager) =
    install(DefaultRequest) {
        url.protocol = URLProtocol.HTTPS
        url.withBaseUrl()
        header(HEADER_AUTHORIZATION, "Bearer ${preferenceManager.getUserToken()}")
    }

private fun URLBuilder.withBaseUrl(url: String = BASE_URL): URLBuilder {
    val baseUrl = Url(url)
    val urlBuilder = URLBuilder(baseUrl).apply { encodedPath += this@withBaseUrl.encodedPath }
    return takeFrom(urlBuilder)
}