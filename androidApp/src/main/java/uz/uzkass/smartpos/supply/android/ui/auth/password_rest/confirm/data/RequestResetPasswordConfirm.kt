package uz.uzkass.smartpos.supply.android.ui.auth.password_rest.confirm.data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class RequestResetPasswordConfirm(
    @SerialName("activationCode")
    val activationCode: String,
    @SerialName("phone")
    val phone:String
)