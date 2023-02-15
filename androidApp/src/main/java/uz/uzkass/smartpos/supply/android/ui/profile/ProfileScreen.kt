package uz.uzkass.smartpos.supply.android.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.ui.NavGraphs
import uz.uzkass.smartpos.supply.android.ui.destinations.CheckOldPinCodeScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.profil.ProfileScreenState
import uz.uzkass.smartpos.supply.viewmodels.profil.ProfileViewModel

@Composable
@Destination
fun ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = viewModel()
) {

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    ProfileScreenView(
        onPinCodeChangeClick = {
            navigator.navigate(CheckOldPinCodeScreenDestination)
        },
        onPasswordChangeClick = {
            navigator.navigate(NavGraphs.passwordReset)
        },
        screenState = screenState.value
    )
}

@Composable
private fun ProfileScreenView(
    screenState: ProfileScreenState,
    onPinCodeChangeClick: () -> Unit,
    onPasswordChangeClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {

            }


            LabeledProfileText(
                label = "",
                value = ""
            )

            LabeledProfileText(
                label = "",
                value = ""
            )

            LabeledProfileText(
                label = "",
                value = ""
            )

            LabeledProfileText(
                label = "",
                value = ""
            )


            FillAvailableSpace()

            SupplyFilledTextButton(
                text = "Изменить PIN-код",
                onClick = onPinCodeChangeClick
            )

            SupplyFilledTextButton(
                text = "Изменить пароль",
                onClick = onPasswordChangeClick
            )

        }

    }

}

@Composable
private fun LabeledProfileText(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = SupplyTheme.typography.subtitle1,
            color = SupplyTheme.colors.unSelected
        )
        Spacer8dp()
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = SupplyTheme.colors.subtitle1
        )
    }
}

