package uz.uzkass.smartpos.supply.android.coreui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme

@Composable
fun SupplyFilledTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    textColor: Color = SupplyTheme.colors.filledButtonText,
    buttonBackgroundColor: Color = SupplyTheme.colors.primary,
    disabledButtonBackground: Color = SupplyTheme.colors.primary.copy(0.5f)
) {
    DefaultButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        enabled = enabled,
        textColor = textColor,
        backgroundColor = buttonBackgroundColor,
        disabledBackgroundColor = disabledButtonBackground
    )
}

@Composable
fun SupplyTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    textColor: Color = SupplyTheme.colors.textButtonText
) {
    DefaultTextButton(
        modifier = modifier,
        text = text,
        textColor = textColor,
        onClick = onClick,
        enabled = enabled
    )
}

@Composable
private fun DefaultButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    textColor: Color = SupplyTheme.colors.filledButtonText,
    backgroundColor: Color = SupplyTheme.colors.background,
    disabledBackgroundColor: Color = SupplyTheme.colors.background,
    shape: Shape = SupplyTheme.shapes.small8Dp,
    fontSize: Int = 16,
    maxLines: Int = 1,
    textStyle: TextStyle = SupplyTheme.typography.button,
    fontWeight: FontWeight = FontWeight.W600,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    multipleEventsCutter { multipleEventsCutterManager ->
        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = { multipleEventsCutterManager.processEvent(onClick) },
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                disabledBackgroundColor = disabledBackgroundColor
            ),
            shape = shape,
        ) {
            DefaultText(
                text = text,
                textColor = textColor,
                fontSize = fontSize,
                maxLines = maxLines,
                style = textStyle,
                overflow = overflow,
                fontWeight = fontWeight
            )
        }
    }
}

@Composable
private fun DefaultTextButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
    textColor: Color = SupplyTheme.colors.textButtonText,
    shape: Shape = SupplyTheme.shapes.small8Dp,
    fontSize: Int = 16,
    maxLines: Int = 1,
    textStyle: TextStyle = SupplyTheme.typography.button,
    fontWeight: FontWeight = FontWeight.W600,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    multipleEventsCutter { multipleEventsCutterManager ->
        TextButton(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = { multipleEventsCutterManager.processEvent(onClick) },
            enabled = enabled,
            shape = shape,
        ) {
            DefaultText(
                text = text,
                textColor = if (enabled) textColor else textColor.copy(alpha = 0.5f),
                fontSize = fontSize,
                maxLines = maxLines,
                style = textStyle,
                overflow = overflow,
                fontWeight = fontWeight
            )
        }
    }
}

@Composable
private fun DefaultText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = SupplyTheme.colors.filledButtonText,
    fontSize: Int = 16,
    maxLines: Int = 1,
    style: TextStyle = SupplyTheme.typography.button,
    fontWeight: FontWeight = FontWeight.W600,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        fontSize = fontSize.sp,
        maxLines = maxLines,
        style = style,
        overflow = overflow,
        fontWeight = fontWeight
    )
}

@Composable
fun TimerTextButton(
    modifier: Modifier = Modifier,
    onClickAgain: () -> Unit,
    text: String,
) {
    var ticks by rememberSaveable {
        mutableStateOf(59)
    }

    LaunchedEffect(key1 = ticks) {
        if (ticks > 0) {
            delay(1_000)
            ticks--
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimerText(ticks)
        Spacer3dp()
        TextButton(
            onClick = {
                ticks = 59
                onClickAgain()
            },
            enabled = ticks == 0
        ) {
            Text(
                text = text,
                color = SupplyTheme.colors.textButtonText,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun TimerText(
    ticks: Int
) {
    val text = if (ticks < 10) {
        "00:0$ticks"
    } else {
        "00:$ticks"
    }
    Text(text = text)
}

class TimerState {


}