package uz.uzkass.smartpos.supply.android.ui.auth.password_rest.creat_new


import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.PasswordTextField
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.ui.NavGraphs
import uz.uzkass.smartpos.supply.android.ui.auth.password_rest.PasswordResetNavGraph
import uz.uzkass.smartpos.supply.android.ui.auth.password_rest.dialog.PasswordSuccessFullyChanged
import uz.uzkass.smartpos.supply.android.ui.destinations.LoginScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.CreateNewPasswordViewModel
import uz.uzkassa.smartpos.supply.library.MR

@PasswordResetNavGraph
@Destination
@Composable
fun CreateNewPasswordScreen(
    phoneNumber: String,
    navigator: DestinationsNavigator,
    viewModel: CreateNewPasswordViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.navigate.collectLatest {

        }
    })
    val showDialog = viewModel.showDialog.collectAsState()
    val loading = viewModel.loading.collectAsState()

    CreateNewPasswordScreenView(
        loading = loading.value,
        showDialog = showDialog.value,
        onPasswordChangeClick = { p, c ->
            viewModel.changedPassword(
                phone = phoneNumber,
                password = p,
                confirmPassword = c
            )
        },
        dialogDismissClick = {
            navigator.navigate(LoginScreenDestination) {
                popUpTo(NavGraphs.root.route)
            }
        }
    )

}

@Composable
private fun CreateNewPasswordScreenView(
    loading: Boolean = false,
    showDialog: Boolean,
    onPasswordChangeClick: (String, String) -> Unit,
    dialogDismissClick: () -> Unit,
) {
    var isPasswordError by remember {
        mutableStateOf(false)
    }

    var valuePassword by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = SupplyTheme.colors.mediumTitle,
                text = stringResource(id = MR.strings.create_new_password.resourceId)
            )
            Spacer(modifier = Modifier.height(SupplyTheme.spacing.extraLarge64Dp))

            PasswordTextField(
                label = stringResource(id = MR.strings.password.resourceId),
                valueState = valuePassword,
                onValueChange = {
                    valuePassword = it
                }
            )
            Spacer16dp()
            PasswordTextField(
                label = stringResource(id = MR.strings.repeat_password.resourceId),
                valueState = confirmPassword,
                isError = isPasswordError,
                onValueChange = {
                    confirmPassword = it
                    isPasswordError = false
                }
            )

            FillAvailableSpace()
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    if (valuePassword == confirmPassword) {
                        onPasswordChangeClick(
                            valuePassword,
                            confirmPassword
                        )
                    } else {
                        isPasswordError = true
                    }
                }) {
                Text(
                    text = stringResource(id = MR.strings.confirm.resourceId),
                    style = SupplyTheme.typography.button
                )
            }
        }


        if (showDialog) {
            Dialog(
                onDismissRequest = {

                },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            ) {
                PasswordSuccessFullyChanged(
                    onClickOk = dialogDismissClick
                )
            }
        }
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


}

//@Preview(showSystemUi = true)
//@Composable
//fun CreateNewPasswordScreenPreview() {
//
//    CreateNewPasswordScreenView()
//
//}