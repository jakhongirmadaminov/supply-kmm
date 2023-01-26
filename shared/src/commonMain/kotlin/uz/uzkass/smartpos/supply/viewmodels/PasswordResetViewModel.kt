package uz.uzkass.smartpos.supply.viewmodels


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


const val TAG = "TTT"


class PasswordResetViewModel constructor(
) : ViewModel(
) {
    private val _navigate: Channel<Unit> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()

    fun initResetPassword(phone: String) {
//        viewModelScope.launch {
//            val request = RequestResetPassword(
//                phone = "998${phone}"
//            )
//            service.resetPassword(request)
//                .onStart {
//                    Log.d(TAG, "initResetPassword: Start ")
//                }
//                .catch {
//                    Log.d(TAG, "initResetPassword: ${it} ")
//                }
//                .collect {
//                    _navigate.send(Unit)
//                    Log.d(TAG, "initResetPassword: Success ")
//                }
//
//        }
    }

}