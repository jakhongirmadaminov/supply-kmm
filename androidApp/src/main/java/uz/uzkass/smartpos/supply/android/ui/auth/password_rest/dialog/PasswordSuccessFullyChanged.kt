package uz.uzkass.smartpos.supply.android.ui.auth.password_rest.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.Spacer12dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme

@Composable
fun PasswordSuccessFullyChanged(onClickOk: () -> Unit) {

    Column(
        modifier = Modifier
            .background(SupplyTheme.colors.background, shape = SupplyTheme.shapes.medium12Dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_success), contentDescription = null)
        Spacer12dp()
        Text(
            text = "Пароль изменён",
            color = SupplyTheme.colors.h1
        )
        Spacer8dp()
        Text(
            text = "Введите обновленные данные для авторизации",
            textAlign = TextAlign.Center,
            color = SupplyTheme.colors.h1
        )
        Spacer12dp()

        SupplyFilledTextButton(
            text = "Ok",
            onClick = {
                onClickOk()
            },
            buttonBackgroundColor = SupplyTheme.colors.primary

        )

    }

}

@Preview
@Composable
fun PasswordSuccessFullyChangedPreview() {
    PasswordSuccessFullyChanged {

    }
}

