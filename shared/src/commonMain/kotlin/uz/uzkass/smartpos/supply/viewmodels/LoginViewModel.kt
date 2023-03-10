package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import dev.icerock.moko.network.generated.models.CabinetLoginDTO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.settings.PreferenceManager
import uz.uzkass.smartpos.supply.utils.ErrorTranslator

class LoginViewModel constructor(
    private val api: MobileAccountResourceApi,
    private val preferenceManager: PreferenceManager,
) : ViewModel() {

    private val _navigate: Channel<LoginNavigator> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()

    private val _showError = MutableStateFlow(false)
    val showError get() = _showError.asStateFlow()


    fun loginUser(login: String, password: String) {

        _loading.value = true

        viewModelScope.launch {
            val request = CabinetLoginDTO(
                phone = "998${login}",
                password = password
            )

            resultOf {
                api.loginUsingPOST5(
                    request
                )
            }.onFailure {
                _loading.emit(false)
                ErrorTranslator.translateServerError(it)
//                _navigate.send(LoginNavigator.ToCreatePinCode)
            }.onSuccess {
                _loading.emit(false)
                preferenceManager.setUserToken(it.accessToken.toString())
                _navigate.send(LoginNavigator.ToCreatePinCode)
            }


//                if (!response.accessToken.isNullOrEmpty()) {

//                } else {
//
//                }
        }
    }
}

enum class LoginNavigator {
    ToCreatePinCode,
    ToRestorePassword,

}

