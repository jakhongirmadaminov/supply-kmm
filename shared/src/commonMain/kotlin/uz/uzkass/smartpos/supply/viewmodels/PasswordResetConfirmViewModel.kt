package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class PasswordResetConfirmViewModel constructor(

) : ViewModel(
) {
    private val _navigate: Channel<Unit> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()
    fun confirmResetPassword(phone: String, activationCode: String) {
//        viewModelScope.launch {
//            val request = RequestResetPasswordConfirm(
//                phone = "998${phone}",
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
//
//        }
    }

}