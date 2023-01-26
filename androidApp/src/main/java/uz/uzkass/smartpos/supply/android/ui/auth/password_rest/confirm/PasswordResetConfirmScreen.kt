package uz.uzkass.smartpos.supply.android.ui.auth.password_rest.confirm


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.uzkassa.smartpos.supply.library.MR
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.otp.PinView

import uz.uzkass.smartpos.supply.android.ui.auth.password_rest.PasswordResetNavGraph
import uz.uzkass.smartpos.supply.android.ui.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.PasswordResetConfirmViewModel

private const val digitCount = 6

@Composable()
@Destination
@PasswordResetNavGraph
fun PasswordResetConfirmScreen(
    navigator: DestinationsNavigator,
    phoneNumber: String
) {

    val viewModel: PasswordResetConfirmViewModel?=null
    viewModel!!
    LaunchedEffect(key1 = Unit, block = {
        viewModel.navigate.collectLatest {
//            navigator.navigate(CreateNewPasswordScreenDestination)
        }
    })
    PasswordResetConfirmScreenView(
        onClickNext = { smsCode ->
            viewModel.confirmResetPassword(phone = phoneNumber, smsCode)
        }

    )

}

@Composable
private fun PasswordResetConfirmScreenView(onClickNext: (smsCode: String) -> Unit) {
    var pinValue by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(LocalSpacing.current.medium16Dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = MR.strings.reset_password.resourceId)
        )
        Spacer(modifier = Modifier.height(SupplyTheme.spacing.extraLarge64Dp))

        Text(text = stringResource(id = MR.strings.input_sms_code.resourceId))
        Spacer(modifier = Modifier.height(SupplyTheme.spacing.small8Dp))
        Text(
            text = stringResource(id = MR.strings.input_sms_code_info.resourceId),

            )
        PinView(
            pinText = pinValue,
            onPinTextChange = {
                if (it.length <= digitCount) {
                    pinValue = it
                }

            },
            digitCount = digitCount
        )

        FillAvailableSpace()
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                onClickNext(pinValue)
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
fun PasswordResetConfirmScreenPreview() {
    PasswordResetConfirmScreenView({

    })
}