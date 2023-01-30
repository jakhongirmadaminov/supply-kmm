package uz.uzkass.smartpos.supply.android.app

import android.app.Application
import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.network.errors.registerAllNetworkMappers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import uz.uzkass.smartpos.supply.android.di.androidModule
import uz.uzkass.smartpos.supply.di.appModule

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            ExceptionMappersStorage.registerAllNetworkMappers()
            androidContext(this@AndroidApplication)
            androidLogger()
            modules(appModule() + androidModule)

        }
    }
}