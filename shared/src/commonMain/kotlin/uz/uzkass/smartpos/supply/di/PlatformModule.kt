package uz.uzkass.smartpos.supply.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.Platform

val platformModule = module {
    singleOf(::Platform)
}