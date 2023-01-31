package uz.uzkass.smartpos.supply.viewmodels


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import dev.icerock.moko.network.generated.models.PhoneDTO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


const val TAG = "TTT"


class PasswordResetViewModel constructor(
    private val api: MobileAccountResourceApi
) : ViewModel(
) {
    private val _navigate: Channel<String> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()

    fun initResetPassword(phone: String) {
        _loading.value = true
        viewModelScope.launch {
            val temp = "998${phone}"
            val request = PhoneDTO(temp)
            kotlin.runCatching {
                api.resetPasswordUsingPOST3(request)
            }.onSuccess {
                _navigate.send(temp)
                _loading.emit(false)
            }.onFailure {
                _loading.emit(false)
            }
        }
    }
}