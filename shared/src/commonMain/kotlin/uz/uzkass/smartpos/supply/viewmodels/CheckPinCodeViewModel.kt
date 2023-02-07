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

    private val _pinErrorState: Channel<Boolean> = Channel(Channel.BUFFERED)
    var pinErrorState = _pinErrorState.receiveAsFlow()


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


    fun checkPinCodeForCreateNew(newPinCode: String) {
        viewModelScope.launch {
            if (preferenceManager.getUserPinCode() == newPinCode) {
                _navigate.send(CheckPinCodeNavigator.ToCreateNewPinCode)
            } else {
                _pinErrorState.send(true)
            }
        }
    }


    fun createPinCode(newPinCode: String) {
        preferenceManager.setUserPinCode(newPinCode)
        viewModelScope.launch {
            _navigate.send(CheckPinCodeNavigator.ToMain)
        }
    }


}

enum class CheckPinCodeNavigator {
    ToMain,
    ToLogin,
    ToCreateNewPinCode
}