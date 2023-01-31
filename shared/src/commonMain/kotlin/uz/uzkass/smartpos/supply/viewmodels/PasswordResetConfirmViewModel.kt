package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import dev.icerock.moko.network.generated.models.PhoneDTO
import dev.icerock.moko.network.generated.models.ResetPasswordDTO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class PasswordResetConfirmViewModel constructor(
    private val api: MobileAccountResourceApi,
) : ViewModel(
) {
    private val _navigate: Channel<Unit> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()


    fun confirmResetPassword(phone: String, activationCode: String) {
        _loading.value = true
        viewModelScope.launch {
            val request = ResetPasswordDTO(
                activationCode = activationCode,
                phone = phone
            )
            kotlin.runCatching {
                api.resetPasswordCheckUsingPOST6(request)
            }.onSuccess {
                _loading.emit(false)
                _navigate.send(Unit)
            }.onFailure {
                _loading.emit(false)
            }

        }
    }


    fun clickSendSmsAgain(phone: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                val request = PhoneDTO(phone)
                api.resetPasswordUsingPOST3(request)
            }.onSuccess {

            }.onFailure {

            }

        }
    }

}