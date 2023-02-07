package uz.uzkass.smartpos.supply.settings

import com.russhwolf.settings.Settings

class PreferenceManager(private val settings: Settings) {

    fun setUserToken(value: String) {
        settings.putString(KEY_USER_TOKEN, value)
    }

    fun getUserToken(): String {
        return settings.getString(KEY_USER_TOKEN, "")
    }

    fun setUserPinCode(value: String) {
        settings.putString(KEY_USER_PIN_CODE, value)
    }

    fun getUserPinCode(): String {
        return settings.getString(KEY_USER_PIN_CODE, "")
    }


    fun getLocale(): String {
        return settings.getString(KEY_LOCAL, LOCALE_DEFAULT)
    }

    fun setLocale(value: String) {
        settings.putString(KEY_LOCAL, value)
    }

    fun clear() {
        settings.clear()
    }


    companion object {
        const val KEY_USER_PIN_CODE = "KEY_USER_PIN_CODE"
        const val KEY_USER_TOKEN = "KEY_USER_TOKEN"
        const val KEY_LOCAL = "KEY_LOCAL"

        const val EN_CODE = "en"
        const val RU_CODE = "ru"
        const val UZ_CODE = "uz"
        const val UZ_CR_CODE = "cyrillic"
        const val LOCALE_DEFAULT = RU_CODE
    }

}