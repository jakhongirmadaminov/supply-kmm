package uz.uzkass.smartpos.supply.core.data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class LoginRequest(
    @SerialName("password")
    val password: String,
    @SerialName("rememberMe")
    val rememberMe: Boolean,
    @SerialName("username")
    val username: String
)
