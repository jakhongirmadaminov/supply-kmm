package uz.uzkass.smartpos.supply.android.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import uz.uzkass.smartpos.supply.viewmodels.profil.ProfileScreenState
import uz.uzkass.smartpos.supply.viewmodels.profil.ProfileViewModel

@Destination
@Composable
fun ChangeProfileInfoScreen(
    viewModel: ProfileViewModel = viewModel()
) {


    val screenState = viewModel.screenState.collectAsStateWithLifecycle()


}

@Composable
fun ChangeProfileInfoScreenView(
) {




}


data class ChangeProfileInfoStateHolder(
    val firstName:MutableState<String> = mutableStateOf(""),
    val lastName:MutableState<String> = mutableStateOf(""),
)


