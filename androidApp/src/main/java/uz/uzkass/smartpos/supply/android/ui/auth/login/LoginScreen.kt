package uz.uzkass.smartpos.supply.android.ui.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.core.PhoneWithPrefixTransformation
import uz.uzkass.smartpos.supply.android.coreui.*
import uz.uzkass.smartpos.supply.android.ui.NavGraphs
import uz.uzkass.smartpos.supply.android.ui.destinations.SetPinCodeScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.LoginNavigator
import uz.uzkass.smartpos.supply.viewmodels.LoginViewModel
import uz.uzkassa.smartpos.supply.library.MR


@Composable
@Destination
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.navigate.collectLatest {
            when (it) {
                LoginNavigator.ToCreatePinCode -> {
                    navigator.navigate(SetPinCodeScreenDestination)
                }
                LoginNavigator.ToRestorePassword -> {
                    navigator.navigate(NavGraphs.passwordReset)
                }
            }
        }
    })

    val loading = viewModel.loading.collectAsState()

    LoginScreenView(
        loading = loading.value,
        onClickLogin = viewModel::loginUser,
        onClickRestorePassword = {
            navigator.navigate(NavGraphs.passwordReset)
        }
    )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun LoginScreenView(
    loading: Boolean,
    onClickLogin: (login: String, password: String) -> Unit,
    onClickRestorePassword: () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = stringResource(id = MR.strings.authorization.resourceId),
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = SupplyTheme.colors.largeTitle
            )
            Text(
                text = stringResource(id = MR.strings.authorization_info.resourceId),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = SupplyTheme.colors.mediumTitle
            )
            val valuePhone = remember {
                mutableStateOf(TextFieldValue())
            }
            val valuePassword = remember {
                mutableStateOf(TextFieldValue())
            }
            Spacer16dp()
            LabelTextField(
                label = stringResource(id = MR.strings.phone_number.resourceId),
                valueState = valuePhone,
                visualTransformation = PhoneWithPrefixTransformation("+998"),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
            )
            Spacer16dp()
            PasswordTextView(
                label = stringResource(id = MR.strings.password.resourceId),
                valueState = valuePassword,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboard?.hide()
                        onClickLogin(
                            valuePhone.value.text,
                            valuePassword.value.text
                        )
                    }
                )
            )
            FillAvailableSpace()
            CorneredTextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !loading,
                buttonBackgroundColor = SupplyTheme.colors.primary,
                text = stringResource(id = MR.strings.login.resourceId),
                onClick = {
                    onClickLogin(
                        valuePhone.value.text,
                        valuePassword.value.text
                    )
                })
            SupplyTextButton(
                onClick = onClickRestorePassword,
                text = stringResource(id = MR.strings.forgot_password.resourceId)
            )
        }

        if (loading) {
            CircularProgressIndicator()
        }

    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreenView(
        onClickLogin = { l, p ->

        },
        onClickRestorePassword = {

        },
        loading = false
    )
}