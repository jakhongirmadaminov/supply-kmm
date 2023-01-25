package uz.uzkass.smartpos.supply.services.demo

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import uz.uzkass.smartpos.supply.core.data.LoginRequest
import uz.uzkass.smartpos.supply.core.utils.postJson

class DemoServiceImpl(
    private val httpClient: HttpClient
) : DemoService {

    override suspend fun login(loginRequest: LoginRequest): String {
        return httpClient.postJson(urlAddress = ACCOUNT_LOGIN) {
            setBody(loginRequest)
        }
    }

    private companion object {
        const val ACCOUNT_LOGIN = "api/admin/v1/account/login"
    }

}