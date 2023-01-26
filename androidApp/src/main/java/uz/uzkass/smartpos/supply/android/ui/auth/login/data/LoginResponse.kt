package uz.uzkass.smartpos.supply.android.ui.auth.login.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("access_token")
    val accessToken: String? = null,
)
