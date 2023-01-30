package uz.uzkass.smartpos.supply.android.ui.main.category.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp

@Composable
fun AppBar(
    modifier: Modifier,
    title: String,

    onBackClick: (() -> Unit)? = null,
    iconsId: List<Int> = emptyList(),
    onIconClick: ((Int) -> Unit) = {},
) {
    Row(modifier = modifier) {
        onBackClick?.let {
            Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
            Spacer8dp()
        }

        Text(text = title)

        FillAvailableSpace()

        iconsId.forEach {
            IconButton(onClick = {
                onIconClick(it)
            }) {
                Icon(painter = painterResource(id = it), contentDescription = null)
            }
        }
    }
}