package uz.uzkass.smartpos.supply.android.ui.main.category.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace

@Composable
fun CategoriesItem(
    name: String,
    count: Long,
    onClickItem: (String) -> Unit
) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = name)
        FillAvailableSpace()
        Text(text = count.toString())
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
    }

}