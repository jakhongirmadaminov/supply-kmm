package uz.uzkass.smartpos.supply.di

import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.errors.mappers.throwableMapper
import dev.icerock.moko.errors.presenters.*
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import dev.icerock.moko.network.generated.apis.MobileDashboardResourceApi
import dev.icerock.moko.network.generated.apis.MobileDashboardResourceApiImpl
import io.github.aakira.napier.Napier
import io.ktor.client.*
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.core.http.getAuthHttpClient
import uz.uzkass.smartpos.supply.viewmodels.CheckPinCodeViewModel
import uz.uzkass.smartpos.supply.core.http.httpClient
import uz.uzkass.smartpos.supply.services.demo.DemoService
import uz.uzkass.smartpos.supply.services.demo.DemoServiceImpl
import uz.uzkass.smartpos.supply.settings.PreferenceManager
import uz.uzkass.smartpos.supply.viewmodels.*

const val AUTH_HTTP_CLIENT_NAME = "AUTH_HTTP_CLIENT_NAME"
const val HTTP_CLIENT_NAME = "HTTP_CLIENT_NAME"
val commonModule = module {
//    singleOf(::httpClient)
//    single { AppViewModel() }

    single(named(HTTP_CLIENT_NAME)) {
        httpClient
    }
    single(named(AUTH_HTTP_CLIENT_NAME)) {
        getAuthHttpClient(
            get()
        )
    }

    single {
        PreferenceManager(settings = get())
    }



    single { DemoServiceImpl(httpClient = get()) } bind DemoService::class

    factory { SplashViewModel(preferenceManager = get()) }

    factory { ChooseLanguageViewModel(preferenceManager = get()) }

    factory {
        LoginViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(named(HTTP_CLIENT_NAME)),
                json = Json {
//                    ignoreUnknownKeys = true
                }
            ),
            preferenceManager = get(),
        )
    }

    factory { PasswordResetViewModel() }

    factory { SetPinCodeViewModel(preferenceManager = get()) }

    factory { CheckPinCodeViewModel(preferenceManager = get()) }

    factory {
        HomeViewModel(
            api = MobileDashboardResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = Json {
//                    ignoreUnknownKeys = true
                }
            )
        )
    }

}
