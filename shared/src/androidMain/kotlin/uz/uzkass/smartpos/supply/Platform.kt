package uz.uzkass.smartpos.supply

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit
import org.koin.core.module.Module
import org.koin.dsl.module
import uz.uzkass.smartpos.supply.settings.MultiplatformSettingsWrapper

actual class Platform actual constructor() {
    actual val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = Platform()

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config(this)

    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(30, TimeUnit.SECONDS)
        }
    }
}

actual fun settingsModule(): Module = module {
    single { MultiplatformSettingsWrapper(context = get()).createSettings() }
}