package uz.uzkass.smartpos.supply.android.coreui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Spacer3dp() {
    Spacer(modifier = Modifier.size(3.dp))
}

@Composable
fun Spacer4dp() {
    Spacer(modifier = Modifier.size(4.dp))
}

@Composable
fun Spacer5dp() {
    Spacer(modifier = Modifier.size(5.dp))
}

@Composable
fun Spacer8dp() {
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
fun Spacer10dp() {
    Spacer(modifier = Modifier.size(10.dp))
}

@Composable
fun Spacer12dp() {
    Spacer(modifier = Modifier.size(12.dp))
}

@Composable
fun Spacer16dp() {
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun Spacer20dp() {
    Spacer(modifier = Modifier.size(20.dp))
}

@Composable
fun Spacer24dp() {
    Spacer(modifier = Modifier.size(24.dp))
}

@Composable
fun Spacer30dp() {
    Spacer(modifier = Modifier.size(30.dp))
}

@Composable
fun Spacer48dp() {
    Spacer(modifier = Modifier.size(48.dp))
}

@Composable
fun Spacer50dp() {
    Spacer(modifier = Modifier.size(50.dp))
}
@Composable
fun Spacer56dp() {
    Spacer(modifier = Modifier.size(56.dp))
}

@Composable
fun Spacer64dp() {
    Spacer(modifier = Modifier.size(64.dp))
}

@Composable
fun Spacer76dp() {
    Spacer(modifier = Modifier.size(76.dp))
}

@Composable
fun Spacer96dp() {
    Spacer(modifier = Modifier.size(96.dp))
}

@Composable
fun ColumnScope.FillAvailableSpace() {
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
fun RowScope.FillAvailableSpace() {
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
fun ColumnScope.SpacerWeight(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun RowScope.SpacerWeight(weight: Float = 1f) {
    Spacer(modifier = Modifier.weight(weight))
}