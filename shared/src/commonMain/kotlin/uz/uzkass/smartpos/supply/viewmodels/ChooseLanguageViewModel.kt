package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.settings.PreferenceManager
import uz.uzkass.smartpos.supply.settings.PreferenceManager.Companion.RU_CODE
import uz.uzkass.smartpos.supply.settings.PreferenceManager.Companion.UZ_CODE


class ChooseLanguageViewModel constructor(
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    private val _navigate: Channel<Unit> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()
    fun chooseRussian() {
        preferenceManager.setLocale(RU_CODE)
        viewModelScope.launch {
            _navigate.send(Unit)
        }
    }

    fun chooseUzbek() {
        preferenceManager.setLocale(UZ_CODE)
        viewModelScope.launch {
            _navigate.send(Unit)
        }
    }

}