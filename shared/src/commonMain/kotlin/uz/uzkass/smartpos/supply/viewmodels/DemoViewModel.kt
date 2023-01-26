package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.data.LoginRequest
import dev.icerock.moko.network.generated.apis.*
import io.ktor.client.HttpClient
import uz.uzkass.smartpos.supply.core.http.httpClient
import kotlinx.serialization.json.Json
import uz.uzkass.smartpos.supply.services.demo.DemoService

class DemoViewModel(
//    private val demoUseCase: DemoUseCase,

) : ViewModel() {


    private val productResourceApi = MobileProductResourceApi(
        basePath = "https://api-devsupply.smartpos.uz/api/mobile/v1/", // Base API URL
        httpClient = httpClient, // Reference to Ktor HTTP client object
        json = Json // Reference to kotlinx.serialization.json parser object
    )

    fun apiRequest() {
        viewModelScope.launch {
            try {
                val product = productResourceApi.getUsingGET43(834)
                println(product.name)
            } catch (error: Exception) {
                println(error.message)
            }
        }
    }


//    fun getCurrentAccount() {
//        viewModelScope.launch {
//            demoUseCase
//                .getCurrentAccount()
//                .catch {
//                    Napier.d("MainViewModel: $it")
//                }
//                .collect { data ->
//                    Napier.d(message = "MainViewModel: $data")
//                }
//        }
//    }

//    fun login() {
//        viewModelScope.launch {
//            demoUseCase
//                .login(LoginRequest(password = "Aa1234567", rememberMe = false, username = "998946740298"))
//                .catch { cause: Throwable ->
//                    Napier.d("MainViewModel cause: $cause")
//                }.collect { accessToken ->
//                    Napier.d("MainViewModel accessToken: $accessToken")
//                }
//
//        }
//    }

}