package uz.uzkass.smartpos.supply.core.http

import uz.uzkass.smartpos.supply.core.http.features.defaultRequest
import uz.uzkass.smartpos.supply.core.utils.contentNegotiation
import uz.uzkass.smartpos.supply.core.utils.logging
import uz.uzkass.smartpos.supply.httpClient
import uz.uzkass.smartpos.supply.initLogger

val httpClient = httpClient {
    logging()
    contentNegotiation()
    defaultRequest()
}.also { initLogger() }