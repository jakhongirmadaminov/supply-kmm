package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch



class ChooseLanguageViewModel  constructor(
//    private val appPreferences: AppPreferences
) : ViewModel() {
    private val _navigate: Channel<Unit> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()
    fun chooseRussian() {
//        appPreferences.setLocale(RU_CODE)
        viewModelScope.launch {
            _navigate.send(Unit)
        }
    }

    fun chooseUzbek() {
//        appPreferences.setLocale(UZ_CODE)
        viewModelScope.launch {
            _navigate.send(Unit)
        }
    }

}