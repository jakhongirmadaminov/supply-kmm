package uz.uzkass.smartpos.supply.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow


interface ThemeProvider {
    var theme: Theme
    fun observeTheme(): Flow<Theme>

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM
    }

    fun isNightMode(): Boolean

    fun setNightMode(forceNight: Boolean)
}

@Composable
fun ThemeProvider.shouldUseDarkMode(indexOfTheme: Int): Boolean {
    val initialValue =
        when (indexOfTheme) {
            0 -> ThemeProvider.Theme.DARK
            1 -> ThemeProvider.Theme.LIGHT
            2 -> ThemeProvider.Theme.SYSTEM
            else -> ThemeProvider.Theme.SYSTEM
        }
    val themePreference =
        observeTheme().collectAsState(initial = initialValue)
    val mode = when (themePreference.value) {
        ThemeProvider.Theme.LIGHT -> false
        ThemeProvider.Theme.DARK -> true
        else -> isSystemInDarkTheme()
    }
    setNightMode(mode)
    return mode
}
