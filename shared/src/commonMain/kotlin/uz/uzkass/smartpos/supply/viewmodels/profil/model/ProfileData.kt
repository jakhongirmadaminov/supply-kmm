package uz.uzkass.smartpos.supply.viewmodels.profil.model

@kotlinx.serialization.Serializable
data class ProfileData(
    val id: Long? = null,
    val login: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val photo: String? = null,
    val company: Company,
    val activityType: ActivityType

)
@kotlinx.serialization.Serializable
data class ActivityType(
    val id: Long? = null,
    val name: String? = null
)
@kotlinx.serialization.Serializable
data class Company(
    val id: Long? = null,
    val name: String? = null,
    val tin: String? = null
)