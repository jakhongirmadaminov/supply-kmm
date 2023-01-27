package uz.uzkass.smartpos.supply.viewmodels


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.settings.PreferenceManager

class SplashViewModel(preferenceManager: PreferenceManager) : ViewModel() {
    private val _navigate: Channel<SplashNavigator> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(500)
            if (preferenceManager.getUserToken().isEmpty() || preferenceManager.getUserPinCode().isEmpty()) {
                _navigate.send(
                    SplashNavigator.ToChooseLanguage
                )
            } else {
                _navigate.send(
                    SplashNavigator.ToCheckPinCode
                )
            }
        }
    }
}

enum class SplashNavigator {
    ToCheckPinCode,
    ToChooseLanguage
}