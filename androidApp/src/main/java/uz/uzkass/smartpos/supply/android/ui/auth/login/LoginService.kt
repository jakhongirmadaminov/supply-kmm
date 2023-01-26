package uz.uzkass.smartpos.supply.android.ui.auth.login

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.uzkass.smartpos.supply.android.ui.auth.login.data.LoginRequest
import uz.uzkass.smartpos.supply.android.ui.auth.login.data.LoginResponse
import uz.uzkass.smartpos.supply.core.utils.postJson

interface LoginService {

    fun clientLogin(request: LoginRequest): Flow<LoginResponse>

}

class LoginServiceImpl(private val httpClient: HttpClient) : LoginService {
    override fun clientLogin(request: LoginRequest) = flow<LoginResponse> {

        emit(httpClient.postJson(LOGIN_URL) {
            setBody(request)
        })

    }

    companion object{
        const val LOGIN_URL = "api/mobile/v1/account/login"
    }
}


