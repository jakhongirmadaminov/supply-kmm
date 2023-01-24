package uz.uzkass.smartpos.supply.android.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import uz.uzkass.smartpos.supply.android.di.androidModule
import uz.uzkass.smartpos.supply.di.commonModule
import uz.uzkass.smartpos.supply.di.platformModule

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidApplication)
            androidLogger()
            modules(androidModule + commonModule + platformModule)
        }
    }
}