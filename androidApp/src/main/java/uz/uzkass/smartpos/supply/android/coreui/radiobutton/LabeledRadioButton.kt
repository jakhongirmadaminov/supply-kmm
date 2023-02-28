package uz.uzkass.smartpos.supply.android.coreui.radiobutton

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.ui.main.create_order.firstRadioId
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme


@Composable
fun LabeledRadioButton(
    modifier: Modifier,
    selected: Boolean,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier,
            selected = selected,
            onClick = onClick
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = SupplyTheme.colors.subtitle1,
            fontWeight = FontWeight.Medium
        )
    }
}