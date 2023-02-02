package uz.uzkass.smartpos.supply.android.coreui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DividerMin(
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    Divider(
        modifier = modifier.fillMaxWidth(),
        color = color,
        thickness = 1.dp
    )
}

@Composable
fun DividerMiddle(
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Black,
        thickness = 2.dp
    )
}

@Composable
fun DividerMax(
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Black,
        thickness = 3.dp
    )
}
