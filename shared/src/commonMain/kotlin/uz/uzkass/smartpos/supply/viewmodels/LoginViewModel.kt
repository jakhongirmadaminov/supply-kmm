package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class LoginViewModel
constructor(

) : ViewModel() {

    private val _navigate: Channel<Unit> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()

    fun loginUser(login: String, password: String) {

//        viewModelScope.launch {
//            val request = LoginRequest(
//                phone = "998${login}",
//                password = password
//            )
//            service.clientLogin(request = request)
//                .onStart {
//                    Log.d("TTT", "loginUser:Start ")
//                }
//                .catch {
//                    Log.d("TTT", "loginUser:${it} ")
//                }
//                .collect {
////                    userPreferences.accessToken = it.accessToken
//                    _navigate.send(Unit)
//                    Log.d("TTT", "loginUser:${it} ")
//                }
//
//        }

    }
}


