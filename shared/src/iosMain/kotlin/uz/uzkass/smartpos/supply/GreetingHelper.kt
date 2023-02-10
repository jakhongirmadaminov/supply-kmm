package uz.uzkass.smartpos.supply

import org.koin.core.context.startKoin
import uz.uzkass.smartpos.supply.di.appModule

fun initKoin(){
    startKoin {
        modules(appModule())
    }
}