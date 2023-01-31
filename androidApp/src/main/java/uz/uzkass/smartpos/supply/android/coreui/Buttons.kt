package uz.uzkass.smartpos.supply.android.coreui

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import uz.uzkass.smartpos.supply.android.ui.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkassa.smartpos.supply.library.MR
import kotlin.time.seconds


@Composable
fun CorneredTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    buttonBackgroundColor: Color = SupplyTheme.colors.background
) {
    multipleEventsCutter { manager ->
        Button(
            modifier = modifier,
            onClick = { manager.processEvent { onClick() } },
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonBackgroundColor,
                disabledBackgroundColor = buttonBackgroundColor.copy(0.4f)
            ),
            shape = SupplyTheme.shapes.small8Dp,
        ) {
            Text(
                text = text,
                style = SupplyTheme.typography.button
            )
        }
    }
}


@Composable
fun SupplyTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    TextButton(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(top = LocalSpacing.current.small8Dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            color = SupplyTheme.colors.textButtonText,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
    }
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

