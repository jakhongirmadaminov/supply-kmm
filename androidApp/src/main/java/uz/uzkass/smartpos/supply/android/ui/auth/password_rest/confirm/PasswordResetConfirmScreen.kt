package uz.uzkass.smartpos.supply.android.ui.auth.password_rest.confirm


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkassa.smartpos.supply.library.MR
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.*

import uz.uzkass.smartpos.supply.android.coreui.otp.PinView

import uz.uzkass.smartpos.supply.android.ui.auth.password_rest.PasswordResetNavGraph
import uz.uzkass.smartpos.supply.android.ui.destinations.CreateNewPasswordScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.PasswordResetConfirmViewModel

private const val digitCount = 6

@Composable()
@Destination
@PasswordResetNavGraph
fun PasswordResetConfirmScreen(
    navigator: DestinationsNavigator,
    phoneNumber: String,
    viewModel: PasswordResetConfirmViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = Unit, block = {
        viewModel.navigate.collectLatest {
            navigator.navigate(CreateNewPasswordScreenDestination)
        }
    })

    PasswordResetConfirmScreenView(
        loading = viewModel.loading.value,
        onClickAgain = {
            viewModel.clickSendSmsAgain(phoneNumber)
        },
        onClickNext = { smsCode ->
            viewModel.confirmResetPassword(phone = phoneNumber, smsCode)
        },
    )

}

@Composable
private fun PasswordResetConfirmScreenView(
    loading: Boolean,
    onClickAgain: () -> Unit,
    onClickNext: (smsCode: String) -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        var pinValue by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalSpacing.current.medium16Dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = SupplyTheme.colors.mediumTitle,
                text = stringResource(id = MR.strings.reset_password.resourceId)
            )
            Spacer(modifier = Modifier.height(SupplyTheme.spacing.extraLarge64Dp))

            Text(
                text = stringResource(id = MR.strings.input_sms_code.resourceId),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = SupplyTheme.colors.imageTitle,
            )
            Spacer(modifier = Modifier.height(SupplyTheme.spacing.small8Dp))
            Text(
                text = stringResource(id = MR.strings.input_sms_code_info.resourceId),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = SupplyTheme.colors.mediumTitle
            )

            Spacer(modifier = Modifier.height(SupplyTheme.spacing.medium16Dp))

            PinView(
                Modifier.fillMaxWidth(),
                pinText = pinValue,
                onPinTextChange = {
                    if (it.length <= digitCount) {
                        pinValue = it
                    }

                },
                digitCount = digitCount
            )
            Spacer3dp()

            TimerTextButton(
                modifier = Modifier.align(Alignment.Start),
                onClickAgain = onClickAgain,
                text = "Click Again"
            )

            FillAvailableSpace()

            SupplyFilledTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    onClickNext(pinValue)
                },
                enabled = !loading,
                text = stringResource(id = MR.strings.confirm.resourceId),
                buttonBackgroundColor = SupplyTheme.colors.primary
            )
        }

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


//@Composable
//@Preview
//fun PasswordResetConfirmScreenPreview() {
//    PasswordResetConfirmScreenView({
//
//    })
//}


