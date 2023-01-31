package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class PasswordResetConfirmViewModel constructor(
    private val api: MobileAccountResourceApi,
) : ViewModel(
) {
    private val _navigate: Channel<Unit> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()
    fun confirmResetPassword(phone: String, activationCode: String) {
        viewModelScope.launch {
//            val request = RequestResetPasswordConfirm(
//                phone = phone,
//                activationCode = activationCode
//            )
//            service.confirmPassword(request)
//                .onStart {
//                    Log.d(TAG, "initResetPassword: Start ")
//                }
//                .catch {
//                    Log.d(TAG, "initResetPassword: ${it} ")
//                }
//                .collect {
//                    Log.d(TAG, "initResetPassword: Success ")
//                    _navigate.send(Unit)
//                }

        }
    }

}