package uz.uzkass.smartpos.supply.android.ui.auth.password_rest.creat_new


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.PasswordTextView
import uz.uzkass.smartpos.supply.android.ui.auth.password_rest.PasswordResetNavGraph
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkassa.smartpos.supply.library.MR

@PasswordResetNavGraph
@Destination
@Composable
fun CreateNewPasswordScreen() {

    CreateNewPasswordScreenView()

}

@Composable
private fun CreateNewPasswordScreenView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id =MR.strings.create_new_password.resourceId)
        )
        Spacer(modifier = Modifier.height(SupplyTheme.spacing.extraLarge64Dp))

        val valuePassword = remember {
            mutableStateOf(TextFieldValue())
        }

        val valuePassword2 = remember {
            mutableStateOf(TextFieldValue())
        }



        PasswordTextView(
            label = stringResource(id =MR.strings.password.resourceId),
            valueState = valuePassword
        )

        PasswordTextView(
            label = stringResource(id =MR.strings.repeat_password.resourceId),
            valueState = valuePassword2
        )


        FillAvailableSpace()
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id =MR.strings.confirm.resourceId),
                style = SupplyTheme.typography.button
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreateNewPasswordScreenPreview() {

    CreateNewPasswordScreenView()

}