package uz.uzkass.smartpos.supply.core.http

import io.ktor.client.*
import uz.uzkass.smartpos.supply.core.http.features.defaultRequest
import uz.uzkass.smartpos.supply.core.utils.contentNegotiation
import uz.uzkass.smartpos.supply.core.utils.logging
import uz.uzkass.smartpos.supply.httpClient
import uz.uzkass.smartpos.supply.initLogger
import uz.uzkass.smartpos.supply.settings.PreferenceManager

val httpClient = httpClient {
    logging()
    contentNegotiation()
    defaultRequest()
}.also { initLogger() }


fun getAuthHttpClient(preferenceManager: PreferenceManager): HttpClient {
    return httpClient {
        logging()
        contentNegotiation()
        defaultRequest(preferenceManager)
    }.also { initLogger() }
}