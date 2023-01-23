package uz.uzkass.smartpos.supply.android.screens.auth.pincode

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import uz.uzkass.smartpos.supply.android.coreui.otp.PinView
import uz.uzkass.smartpos.supply.android.theme.SupplyTheme

import uz.uzkassa.smartpos.supply.library.MR

@Composable()
fun SetPinCodeScreen() {
    SetPinCodeScreenView()

}

@Composable
private fun SetPinCodeScreenView() {
    var pinValue by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SupplyTheme.spacing.medium16Dp),
            text = stringResource(id = MR.strings.create_pin_code.resourceId)
        )
        Spacer(modifier = Modifier.height(SupplyTheme.spacing.extraLarge64Dp))
        Text(text = stringResource(id = MR.strings.create_pin_code_info.resourceId))
        Spacer(modifier = Modifier.height(SupplyTheme.spacing.small8Dp))
        PinView(
            pinText = pinValue,
            onPinTextChange = {
                pinValue = it
            }
        )

    }
}


@Composable
@Preview
fun SetPinCodeScreenPreview() {
    SetPinCodeScreenView()
}