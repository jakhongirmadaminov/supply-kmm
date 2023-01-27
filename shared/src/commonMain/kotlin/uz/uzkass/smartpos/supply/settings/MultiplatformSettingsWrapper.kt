package uz.uzkass.smartpos.supply.settings

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings

expect class MultiplatformSettingsWrapper {
    fun createSettings(): Settings
}