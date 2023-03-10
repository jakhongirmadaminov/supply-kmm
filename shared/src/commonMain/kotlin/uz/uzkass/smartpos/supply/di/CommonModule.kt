package uz.uzkass.smartpos.supply.di

import org.koin.dsl.module
import uz.uzkass.smartpos.supply.settings.LocalProductRepository
import uz.uzkass.smartpos.supply.settings.PreferenceManager
import uz.uzkass.smartpos.supply.utils.ErrorTranslator

val commonModule = module {

    single { PreferenceManager(settings = get()) }
    single { LocalProductRepository() }
//    single { ErrorTranslator() }
}
