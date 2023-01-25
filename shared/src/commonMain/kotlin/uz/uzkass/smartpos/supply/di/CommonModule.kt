package uz.uzkass.smartpos.supply.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.core.http.httpClient
import uz.uzkass.smartpos.supply.services.demo.DemoService
import uz.uzkass.smartpos.supply.services.demo.DemoServiceImpl
import uz.uzkass.smartpos.supply.usecases.demo.DemoUseCase
import uz.uzkass.smartpos.supply.usecases.demo.DemoUseCaseImpl
import uz.uzkass.smartpos.supply.viewmodels.DemoViewModel

val commonModule = module {
    singleOf(::httpClient)

    single { DemoServiceImpl(httpClient = get()) } bind DemoService::class
    single { DemoUseCaseImpl(demoService = get()) } bind DemoUseCase::class

    factory { DemoViewModel(demoUseCase = get()) }
}