package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.errors.handler.ExceptionHandler
import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.errors.registerAllNetworkMappers
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import dev.icerock.moko.network.generated.models.CabinetLoginDTO
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.services.demo.safeApiCall
import uz.uzkass.smartpos.supply.settings.PreferenceManager


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

            kotlin.runCatching {
                api.loginUsingPOST5(
                    request
                )
            }.onFailure {
                Napier.e("TTT", it)
                _loading.emit(false)
                _navigate.send(LoginNavigator.ToCreatePinCode)
            }.onSuccess {
                Napier.i(it.accessToken.toString())
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

