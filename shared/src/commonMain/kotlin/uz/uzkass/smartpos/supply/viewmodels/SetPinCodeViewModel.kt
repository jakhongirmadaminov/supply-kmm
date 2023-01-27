package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import uz.uzkass.smartpos.supply.settings.PreferenceManager


class SetPinCodeViewModel constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    fun setPinCode(code: String) {
        preferenceManager.setUserPinCode(code)
    }

}