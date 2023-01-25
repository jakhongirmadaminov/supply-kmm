package uz.uzkass.smartpos.supply.android.screens.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.core.PhoneWithPrefixTransformation
import uz.uzkass.smartpos.supply.android.coreui.*
import uz.uzkass.smartpos.supply.android.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.DemoViewModel

import uz.uzkassa.smartpos.supply.library.MR

@Composable
fun LoginScreen() {
//    val viewModel: LoginViewModel = hiltViewModel()
  LoginScreenView(
    onClickLogin = { l, p -> },
    onClickRestorePassword = {

    }
  )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun LoginScreenView(
  onClickLogin: (login: String, password: String) -> Unit,
  onClickRestorePassword: () -> Unit,
  vm: DemoViewModel = koinViewModel()
) {

  val keyboard = LocalSoftwareKeyboardController.current
  Surface(
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
      Button(
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp),
        onClick = {
          onClickLogin(
            valuePhone.value.text,
            valuePassword.value.text
          )
        }
      ) {
        Text(
          text = stringResource(id = MR.strings.login.resourceId),
          style = SupplyTheme.typography.button
        )
      }

      SupplyTextButton(
        onClick = onClickRestorePassword,
        text = stringResource(id = MR.strings.forgot_password.resourceId)
      )
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

    }
  )
}