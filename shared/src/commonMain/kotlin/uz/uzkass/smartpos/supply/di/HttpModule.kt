package uz.uzkass.smartpos.supply.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.core.http.authHttpClient
import uz.uzkass.smartpos.supply.core.http.httpClient

const val AUTH_HTTP_CLIENT_NAME = "AUTH_HTTP_CLIENT_NAME"
const val HTTP_CLIENT_NAME = "HTTP_CLIENT_NAME"

val httpModule = module {

    single(named(HTTP_CLIENT_NAME)) { httpClient }

    single(named(AUTH_HTTP_CLIENT_NAME)) { authHttpClient(preferenceManager = get()) }

}