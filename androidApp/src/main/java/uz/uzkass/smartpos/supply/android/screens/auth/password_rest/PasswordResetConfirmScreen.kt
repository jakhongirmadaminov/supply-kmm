package uz.uzkass.smartpos.supply.android.screens.auth.password_rest

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.otp.PinView
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.theme.SupplyTheme
import uz.uzkassa.smartpos.supply.library.MR

@Composable()
fun PasswordResetConfirmScreen() {
    PasswordResetConfirmScreenView()

}

@Composable
private fun PasswordResetConfirmScreenView() {
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
            style = SupplyTheme.typography.h1
        )
        PinView(
            pinText = pinValue,
            onPinTextChange = {
                pinValue = it
            }
        )

        FillAvailableSpace()
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { /*TODO*/ }) {
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
    PasswordResetConfirmScreenView()
}