package uz.uzkass.smartpos.supply.android.coreui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.ui.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkassa.smartpos.supply.library.MR


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