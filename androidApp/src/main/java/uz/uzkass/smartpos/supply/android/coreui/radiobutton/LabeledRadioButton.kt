package uz.uzkass.smartpos.supply.android.coreui.radiobutton

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uz.uzkass.smartpos.supply.android.ui.main.create_order.firstRadioId


@Composable
fun LabeledRadioButton(
    selected: Boolean,
    modifier: Modifier,
    label: String,
    onClick: () -> Unit
) {
    Row(modifier = modifier.clickable(onClick = onClick)) {
        RadioButton(
            modifier = Modifier,
            selected = selected,
            onClick = onClick
        )
        Text(text = label)
    }
}