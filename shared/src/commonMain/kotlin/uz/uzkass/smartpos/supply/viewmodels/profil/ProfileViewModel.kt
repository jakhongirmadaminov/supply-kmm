package uz.uzkass.smartpos.supply.viewmodels.profil

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileAccountResourceApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf

class ProfileViewModel constructor(
    private val profileApi: MobileAccountResourceApi
) : ViewModel() {
    private val _screenState = MutableStateFlow(ProfileScreenState())
    val screenState: StateFlow<ProfileScreenState> = _screenState

    init {
        getProfileData()

    }


    private fun getProfileData() {

        viewModelScope.launch {
            resultOf {
                profileApi.getCurrentUserUsingGET5()
            }.onSuccess {

            }.onFailure {

            }.let {

            }
        }


    }


}

data class ProfileScreenState(
    val loading: Boolean = false,
    val login: String = "998935533188",
    val firstName: String = "Иван",
    val lastName: String = "Иванов",
    val avatarUrl: String? = null,
    val company: String? = "OOO Name",
    val activityType: String? = null,
)