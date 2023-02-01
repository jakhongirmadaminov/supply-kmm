package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import dev.icerock.moko.network.generated.models.ResetPasswordFinishDTO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf

class CreateNewPasswordViewModel constructor(

    private val api: MobileAccountResourceApi

) : ViewModel() {
    private val _navigate: Channel<LoginNavigator> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog get() = _showDialog.asStateFlow()


    private val _showPasswordError = MutableStateFlow(false)
    val showPasswordError get() = _showPasswordError.asStateFlow()


    fun changedPassword(phone: String, password: String, confirmPassword: String) {
        if (password == confirmPassword) {
            viewModelScope.launch {
                resultOf {
                    val request = ResetPasswordFinishDTO(phone = phone, confirmPassword, password)
                    api.resetPasswordCheckUsingPOST7(request)
                }.onSuccess {

                }.onFailure {

                }.let {

                }
            }
        } else {


        }
    }


    fun onDialogDismissClick() {

    }


}