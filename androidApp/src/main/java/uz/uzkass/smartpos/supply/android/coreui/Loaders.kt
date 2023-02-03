package uz.uzkass.smartpos.supply.android.coreui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme

@Composable
fun LoadingView(
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = SupplyTheme.colors.primary,
            modifier = Modifier.size(24.dp)
        )
    }
}