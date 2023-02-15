package uz.uzkass.smartpos.supply.core.http

import uz.uzkass.smartpos.supply.core.http.features.defaultRequest
import uz.uzkass.smartpos.supply.core.utils.contentNegotiation
import uz.uzkass.smartpos.supply.core.utils.logging
import uz.uzkass.smartpos.supply.httpClient
import uz.uzkass.smartpos.supply.settings.PreferenceManager

internal val httpClient = httpClient {
    expectSuccess = true
    logging()
    contentNegotiation()
    defaultRequest()
}

internal fun authHttpClient(preferenceManager: PreferenceManager) = httpClient {
    logging()
    contentNegotiation()
    defaultRequest(preferenceManager)
}