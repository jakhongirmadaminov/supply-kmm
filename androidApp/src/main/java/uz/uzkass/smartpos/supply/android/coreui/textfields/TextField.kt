package uz.uzkass.smartpos.supply.android.coreui.textfields

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.core.Constants
import uz.uzkass.smartpos.supply.android.coreui.LabelText
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.android.ui.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkassa.smartpos.supply.library.MR

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LabelTextField(
    label: String,
    valueState: MutableState<TextFieldValue>,
    placeholder: String? = null,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,

    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier.fillMaxWidth()) {
        LabelText(label = label)
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmall4dp))
        TextField(
            value = valueState.value,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = SupplyTheme.colors.textFieldBorder,
                    shape = LocalShapes.current.small8Dp
                ),
            onValueChange = { newText ->
                if (newText.text.length <= Constants.UZB_PHONE_NUMBER_LENGTH) {
                    valueState.value = newText
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Color.Black
            ),
            shape = RoundedCornerShape(4.dp),
            singleLine = true,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                trailingIcon?.let {
                    IconButton(onClick = {
                        keyboardController?.hide()
                    }) {
                        Image(
                            painter = trailingIcon,
                            contentDescription = null,
                        )
                    }
                }
            },

            visualTransformation = visualTransformation
        )
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String,
    valueState: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        LabelText(label = label)
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmall4dp))
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isError) SupplyTheme.colors.error else SupplyTheme.colors.textFieldBorder,
                    shape = LocalShapes.current.small8Dp
                ),
            value = valueState,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = stringResource(id = MR.strings.input_password.resourceId),
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp,
                    color = SupplyTheme.colors.textFieldBorder
                )
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = keyboardOptions,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Color.Black
            ),
            shape = RoundedCornerShape(4.dp),
            singleLine = true,
            keyboardActions = keyboardActions,
            trailingIcon = {
                IconButton(
                    onClick = {
                        isPasswordVisible = !isPasswordVisible
                    },
                    content = {
                        Icon(
                            painter = if (isPasswordVisible) painterResource(id = R.drawable.ic_visible_eye) else painterResource(
                                id = R.drawable.ic_visible_eye
                            ),
                            contentDescription = null,
                            tint = SupplyTheme.colors.textFieldBorder
                        )
                    }
                )
            }
        )
    }

}

