package uz.uzkass.smartpos.supply.di

import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import dev.icerock.moko.network.generated.apis.MobileDashboardResourceApi
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.viewmodels.*

val viewModelModule = module {

    factory { SplashViewModel(preferenceManager = get()) }

    factory { ChooseLanguageViewModel(preferenceManager = get()) }

    factory {
        LoginViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(named(HTTP_CLIENT_NAME)),
                json = Json
            ),
            preferenceManager = get(),
        )
    }

    factory { SetPinCodeViewModel(preferenceManager = get()) }

    factory { CheckPinCodeViewModel(preferenceManager = get()) }

    factory {
        HomeViewModel(
            api = MobileDashboardResourceApi(
                httpClient = get(named(AUTH_HTTP_CLIENT_NAME)),
                json = Json
            )
        )
    }

    factory {
        PasswordResetViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(named(HTTP_CLIENT_NAME)),
                json = Json
            )
        )
    }

    factory {
        PasswordResetConfirmViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(named(HTTP_CLIENT_NAME)),
                json = Json
            )
        )
    }
factory {
    CreateNewPasswordViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(named(HTTP_CLIENT_NAME)),
                json = Json
            )
        )
    }

}