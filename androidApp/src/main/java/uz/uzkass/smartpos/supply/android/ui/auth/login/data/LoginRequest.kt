package uz.uzkass.smartpos.supply.android.ui.auth.login.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("password")
    val password: String? = null,
    @SerialName("rememberMe")
    val rememberMe: String? = null,
    @SerialName("phone")
    val phone: String? = null,
)
