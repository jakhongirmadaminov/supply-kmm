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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.theme.SupplyTheme


@Composable
fun CorneredTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    textColor: Color = SupplyTheme.colors.primary,
    enabled: Boolean = true,
    buttonBackgroundColor: Color = SupplyTheme.colors.background
) {
    multipleEventsCutter { manager ->
        Button(
            onClick = { manager.processEvent { onClick() } },
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonBackgroundColor,
                disabledBackgroundColor = SupplyTheme.colors.onBackground
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
                .height(48.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = textColor,
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