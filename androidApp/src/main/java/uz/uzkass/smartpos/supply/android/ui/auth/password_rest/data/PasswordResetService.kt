package uz.uzkass.smartpos.supply.android.ui.auth.password_rest.data

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import uz.uzkass.smartpos.supply.android.ui.auth.password_rest.confirm.data.RequestResetPasswordConfirm
import uz.uzkass.smartpos.supply.core.utils.postJson

interface PasswordResetService {

    fun resetPassword(request: RequestResetPassword): Flow<Unit>
    fun confirmPassword(request: RequestResetPasswordConfirm): Flow<Unit>

}

class PasswordResetServiceImpl(private val httpClient: HttpClient) : PasswordResetService {
    override fun resetPassword(request: RequestResetPassword) = flow {
        emit(
            httpClient.postJson<Unit>(urlAddress = PASSWORD_RESET_URL) {
                setBody(request)
            }
        )
    }

    override fun confirmPassword(request: RequestResetPasswordConfirm) = flow {
        emit(
            httpClient.postJson<Unit>(urlAddress = PASSWORD_RESET_CONFIRM_URL) {
                setBody(request)
            }
        )
    }

    companion object {
        const val PASSWORD_RESET_URL = "api/mobile/v1/account/reset-password/init"
        const val PASSWORD_RESET_CONFIRM_URL = "api/mobile/v1/account/reset-password/check"
    }
}