package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.data.LoginRequest
import uz.uzkass.smartpos.supply.usecases.demo.DemoUseCase

class DemoViewModel(
    private val demoUseCase: DemoUseCase
) : ViewModel() {

    fun login() {
        viewModelScope.launch {
            demoUseCase
                .login(LoginRequest(password = "Aa1234567", rememberMe = false, username = "998946740298"))
                .catch { cause: Throwable ->
                    Napier.d("MainViewModel cause: $cause")
                }.collect { accessToken ->
                    Napier.d("MainViewModel accessToken: $accessToken")
                }

        }
    }

}