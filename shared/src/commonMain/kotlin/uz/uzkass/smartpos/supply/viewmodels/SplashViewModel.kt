package uz.uzkass.smartpos.supply.viewmodels


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _navigate: Channel<SplashNavigator> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(500)
//            if (userPreferences.accessToken == null || userPreferences.pincode == null) {
//                _navigate.send(
//                    SplashNavigator.ToChooseLanguage
//                )
//            } else {
//                _navigate.send(
//                    SplashNavigator.ToMain
//                )
//            }
        }
    }
}

enum class SplashNavigator {
    ToMain,
    ToChooseLanguage
}