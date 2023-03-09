package uz.uzkass.smartpos.supply.viewmodels.profil

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.viewmodels.profil.model.ProfileData

class ProfileViewModel constructor(
    private val profileApi: MobileAccountResourceApi
) : ViewModel() {
    private val _screenState = MutableStateFlow(ProfileScreenState())
    val screenState: StateFlow<ProfileScreenState> = _screenState

    init {
        getProfileData()
    }


    private fun getProfileData() {
        _screenState.update { it.copy(loading = true) }
        viewModelScope.launch {
            resultOf {
                profileApi.getCurrentUserUsingGET5()
            }.onSuccess { obj ->
                val response: ProfileData = Json.decodeFromJsonElement(obj)

                _screenState.update {
                    it.copy(
                        loading = false,
                        firstName = response.firstName ?: "",
                        lastName = response.lastName ?: "",
                        avatarUrl = response.photo ?: "",
                        company = response.company.name ?: "",
                        activityType = response.activityType.name ?: "",
                        companyVat = response?.company?.tin ?: ""
                    )
                }

            }.onFailure {

            }.let {

            }
        }


    }


}

data class ProfileScreenState(
    val loading: Boolean = false,
    val login: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val company: String = "",
    val activityType: String = "",
    val companyVat: String = "",
)

