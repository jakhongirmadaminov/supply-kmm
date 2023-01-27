package uz.uzkass.smartpos.supply.settings

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class MultiplatformSettingsWrapper(private val context: Context) {
    actual fun createSettings(): Settings {
        val sharedPreferences =
            context.getSharedPreferences("notflix_preferences", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate = sharedPreferences)
    }
}
