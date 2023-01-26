package uz.uzkass.smartpos.supply.android.ui.main.check_pincode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class CheckPinCodeViewModel constructor(

//    private val userPreferences: UserPreferences

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
//            if (userPreferences.pincode == newPinCode) {
//                viewModelScope.launch {
//                    _navigate.send(CheckPinCodeNavigator.ToMain)
//                }
//            } else {
//
//            }
        }
    }

}

enum class CheckPinCodeNavigator {
    ToMain,
    ToLogin
}