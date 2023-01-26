package uz.uzkass.smartpos.supply.android.coreui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme

@Composable
fun ColumnButtonImageText(
    modifier: Modifier,
    painter: Painter,
    title: String,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Column(
            modifier = modifier
                .clickable(role = Role.Button, onClick = onClick)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = null,
            )
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                color = SupplyTheme.colors.imageTitle,
                fontSize = 16.sp
            )
        }
    }
}