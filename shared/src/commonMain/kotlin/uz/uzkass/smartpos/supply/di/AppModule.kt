package uz.uzkass.smartpos.supply.di

import uz.uzkass.smartpos.supply.settingsModule

fun appModule() = listOf(
    commonModule,
    platformModule,
    httpModule,
    viewModelModule,
    settingsModule()
)