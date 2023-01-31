package uz.uzkass.smartpos.supply.android.coreui.otp

import android.content.res.Resources
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme


const val PIN_VIEW_TYPE_UNDERLINE = 0
const val PIN_VIEW_TYPE_BORDER = 1

@Composable
fun PinView(
    modifier: Modifier = Modifier,
    pinText: String,
    onPinTextChange: (String) -> Unit,
    digitColor: Color = MaterialTheme.colors.onBackground,
    digitSize: TextUnit = 16.sp,
    digitCount: Int = 4,
    startFocusRequest: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions { }
) {
    val focusRequester = remember { FocusRequester() }

    BasicTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = pinText,
        onValueChange = onPinTextChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = keyboardActions,
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(SupplyTheme.spacing.small8Dp)
            ) {
                val size = remember {
                    val temp =
                        (Resources.getSystem().configuration.screenWidthDp - 32 - ((digitCount) * 8)) / digitCount
                    if (temp > 60) {
                        60
                    } else {
                        temp
                    }
                }
                repeat(digitCount) { index ->
                    val text = if (index < pinText.length) "${pinText[index]}" else null
                    val color = if (index <= pinText.length) {
                        SupplyTheme.colors.lineActiveColor
                    } else {
                        SupplyTheme.colors.lineColor
                    }
                    DigitView(
                        Modifier.size(size.dp),
                        text,
                        textColor = color,
                        digitSize,
                    )
                }
            }
        })
    if (startFocusRequest) {
        LaunchedEffect(key1 = Unit, block = {
            focusRequester.requestFocus()
        })
    }
}


@Composable
private fun DigitView(
    modifier: Modifier,
    pinText: String?,
    textColor: Color,
    digitSize: TextUnit,
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = textColor,
                shape = SupplyTheme.shapes.extraSmall4dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = pinText ?: ".",
            color = textColor,
            style = MaterialTheme.typography.body1,
            fontSize = digitSize,
            textAlign = TextAlign.Center
        )
    }
}