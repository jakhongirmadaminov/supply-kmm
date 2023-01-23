package uz.uzkass.smartpos.supply.android.coreui.otp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.theme.SupplyTheme


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
) {
    BasicTextField(
        modifier = modifier,
        value = pinText,
        onValueChange = onPinTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(SupplyTheme.spacing.small8Dp)
            ) {
                repeat(digitCount) { index ->
                    val text = if (index < pinText.length) "${pinText[index]}" else null
                    val color = if (index <= pinText.length) {
                        SupplyTheme.colors.lineActiveColor
                    } else {
                        SupplyTheme.colors.lineColor
                    }
                    DigitView(
                        index,
                        text,
                        textColor = color,
                        digitSize,
                    )
                }
            }
        })
}


@Composable
private fun DigitView(
    index: Int,
    pinText: String?,
    textColor: Color,
    digitSize: TextUnit,
) {
    Box(
        modifier = Modifier
            .width(70.dp)
            .height(50.dp)
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