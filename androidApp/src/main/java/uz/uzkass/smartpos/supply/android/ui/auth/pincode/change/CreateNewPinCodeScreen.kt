package uz.uzkass.smartpos.supply.android.ui.auth.pincode.change

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.otp.PinView
import uz.uzkass.smartpos.supply.android.ui.NavGraphs
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.CheckPinCodeViewModel
import uz.uzkassa.smartpos.supply.library.MR

@Destination
@Composable
fun CreateNewPinCodeScreen(
    navigator: DestinationsNavigator,
    viewModel: CheckPinCodeViewModel = koinViewModel()

) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.navigate.collectLatest {
            navigator.navigate(NavGraphs.main){
                popUpTo(NavGraphs.root.route)
            }
        }
    })
    CreateNewPinCodeScreenView(
        clickConfirmPinCode = viewModel::createPinCode
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CreateNewPinCodeScreenView(
    clickConfirmPinCode: (String) -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    var pinValue by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(SupplyTheme.spacing.medium16Dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = SupplyTheme.colors.mediumTitle,
            text = stringResource(id = MR.strings.change_pin_code.resourceId)
        )
        Spacer(modifier = Modifier.height(SupplyTheme.spacing.extraLarge64Dp))
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = MR.strings.input_new_pin_code.resourceId),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = SupplyTheme.colors.imageTitle,
        )
        Spacer(modifier = Modifier.height(SupplyTheme.spacing.small8Dp))
        PinView(
            pinText = pinValue,
            onPinTextChange = {
                if (it.length <= 4) {
                    pinValue = it
                }
                if (it.length == 4) {
                    clickConfirmPinCode(pinValue)
                }

            },
            keyboardActions = KeyboardActions(onDone = {
                if (pinValue.length == 4) {
                    clickConfirmPinCode(pinValue)
                    keyboard?.hide()
                }
            })
        )

        FillAvailableSpace()
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = pinValue.length == 4,
            onClick = {
                clickConfirmPinCode(pinValue)
            }) {
            Text(
                text = stringResource(id = MR.strings.confirm.resourceId),
                style = SupplyTheme.typography.button
            )
        }

    }
}