package uz.uzkass.smartpos.supply.android.ui.auth.pincode

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.otp.PinView
import uz.uzkass.smartpos.supply.android.ui.NavGraphs
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.SetPinCodeViewModel
import uz.uzkassa.smartpos.supply.library.MR

@Destination
@Composable()
fun SetPinCodeScreen(
    navigator: DestinationsNavigator,
    viewModel: SetPinCodeViewModel = koinViewModel()

) {
    SetPinCodeScreenView() { code ->
        viewModel.setPinCode(code)
        navigator.navigate(NavGraphs.main) {
            popUpTo(NavGraphs.root.route)
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SetPinCodeScreenView(onClickSetPinCode: (String) -> Unit) {
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
            text = stringResource(id = MR.strings.create_pin_code.resourceId)
        )
        Spacer(modifier = Modifier.height(SupplyTheme.spacing.extraLarge64Dp))
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = MR.strings.create_pin_code_info.resourceId),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = SupplyTheme.colors.imageTitle,
        )
        Spacer(modifier = Modifier.height(SupplyTheme.spacing.small8Dp))
        PinView(
            pinText = pinValue,
            onPinTextChange = {
                if (it.length<=4){
                    pinValue = it
                }
                if (it.length == 4){
                    onClickSetPinCode(pinValue)
                }

            },
            keyboardActions = KeyboardActions(onDone = {
                if (pinValue.length == 4) {
                    onClickSetPinCode(pinValue)
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
                onClickSetPinCode(pinValue)
            }) {
            Text(
                text = stringResource(id = MR.strings.confirm.resourceId),
                style = SupplyTheme.typography.button
            )
        }

    }
}


@Composable
@Preview
fun SetPinCodeScreenPreview() {
    SetPinCodeScreenView() {

    }
}