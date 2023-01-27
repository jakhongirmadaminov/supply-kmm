package uz.uzkass.smartpos.supply.di

import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.viewmodels.CheckPinCodeViewModel
import uz.uzkass.smartpos.supply.core.http.httpClient
import uz.uzkass.smartpos.supply.services.demo.DemoService
import uz.uzkass.smartpos.supply.services.demo.DemoServiceImpl
import uz.uzkass.smartpos.supply.settings.PreferenceManager
import uz.uzkass.smartpos.supply.viewmodels.*

val commonModule = module {
    singleOf(::httpClient)
    single {
        PreferenceManager(settings = get())
    }

    single { DemoServiceImpl(httpClient = get()) } bind DemoService::class
//    single { DemoUseCaseImpl(demoService = get()) } bind DemoUseCase::class

//    factory { DemoViewModel(httpClient = get()) }
    factory { SplashViewModel(preferenceManager = get()) }

    factory { ChooseLanguageViewModel(preferenceManager = get()) }

    factory {
        LoginViewModel(
            api = MobileAccountResourceApi(
                httpClient = get(),
                json = Json
            ),
            preferenceManager = get()
        )
    }

    factory { PasswordResetViewModel() }
    factory { SetPinCodeViewModel(preferenceManager = get()) }

    factory { CheckPinCodeViewModel(preferenceManager = get()) }

}

