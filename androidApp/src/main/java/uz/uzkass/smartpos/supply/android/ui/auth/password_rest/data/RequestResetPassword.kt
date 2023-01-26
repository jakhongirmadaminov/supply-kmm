package uz.uzkass.smartpos.supply.android.ui.auth.password_rest.data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class RequestResetPassword(
    @SerialName("phone")
    val phone: String
)