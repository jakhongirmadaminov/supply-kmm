package uz.uzkass.smartpos.supply.android.coreui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import uz.uzkass.smartpos.supply.android.theme.SupplyTheme


@Composable
fun LabelText(
    modifier: Modifier = Modifier,
    label: String,
    style: TextStyle = SupplyTheme.typography.subtitle2,
) {
    Text(
        modifier = modifier,
        text = label,
        style = style,
        maxLines = 1
    )
}
