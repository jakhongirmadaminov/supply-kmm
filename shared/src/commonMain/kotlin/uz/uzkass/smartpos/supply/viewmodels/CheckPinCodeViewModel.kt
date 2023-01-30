package uz.uzkass.smartpos.supply.viewmodels


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.settings.PreferenceManager


class CheckPinCodeViewModel constructor(
    private val preferenceManager: PreferenceManager
) :
    ViewModel() {

    private val _navigate: Channel<CheckPinCodeNavigator> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()


    private var checkCount = 0

    fun checkPinCode(newPinCode: String) {
        if (checkCount >= 3) {
//            userPreferences.clearAll()
            viewModelScope.launch {
                _navigate.send(CheckPinCodeNavigator.ToLogin)
            }
        } else {
            if (preferenceManager.getUserPinCode() == newPinCode) {
                viewModelScope.launch {
                    _navigate.send(CheckPinCodeNavigator.ToMain)
                }
            } else {

            }
        }
    }

}

enum class CheckPinCodeNavigator {
    ToMain,
    ToLogin
}