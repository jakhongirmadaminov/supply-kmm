package uz.uzkass.smartpos.supply.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.core.http.httpClient
import uz.uzkass.smartpos.supply.services.demo.DemoService
import uz.uzkass.smartpos.supply.services.demo.DemoServiceImpl
import uz.uzkass.smartpos.supply.viewmodels.DemoViewModel
import uz.uzkass.smartpos.supply.viewmodels.SplashViewModel

val commonModule = module {
    singleOf(::httpClient)

    single { DemoServiceImpl(httpClient = get()) } bind DemoService::class
//    single { DemoUseCaseImpl(demoService = get()) } bind DemoUseCase::class

//    factory { DemoViewModel(httpClient = get()) }
    factory { SplashViewModel() }

}