package uz.uzkass.smartpos.supply.settings

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings

actual class MultiplatformSettingsWrapper() {
    actual fun createSettings(): Settings {
        return NSUserDefaultsSettings.Factory().create("notflix_preferences")
    }
}