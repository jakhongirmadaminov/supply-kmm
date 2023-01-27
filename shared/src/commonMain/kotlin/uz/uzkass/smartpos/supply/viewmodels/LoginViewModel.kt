package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.errors.registerAllNetworkMappers
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import dev.icerock.moko.network.generated.models.CabinetLoginDTO
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.services.demo.safeApiCall
import uz.uzkass.smartpos.supply.settings.PreferenceManager


class LoginViewModel
constructor(
    private val api: MobileAccountResourceApi,
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _navigate: Channel<LoginNavigator> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()

    fun loginUser(login: String, password: String) {

        viewModelScope.launch {
            val request = CabinetLoginDTO(
                phone = "998${login}",
                password = password
            )

            val temp = safeApiCall {
                api.loginUsingPOST2(
                    request
                )
            }
            ExceptionMappersStorage.registerAllNetworkMappers()
            Napier.e {
                temp.toString()
            }

//
//                if (!response.accessToken.isNullOrEmpty()) {
//                    preferenceManager.setUserToken(response.accessToken)
//                    _navigate.send(LoginNavigator.ToCreatePinCode)
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

