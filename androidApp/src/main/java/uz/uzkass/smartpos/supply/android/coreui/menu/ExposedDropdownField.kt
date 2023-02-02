package uz.uzkass.smartpos.supply.android.coreui.menu

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownField(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    items: List<ExposedDropdown> = emptyList(),
    currentItem: ExposedDropdown? = null,
    readOnly: Boolean = true,
    onDismissRequest: () -> Unit,
    onItemSelected: (ExposedDropdown) -> Unit,

    ) {

    var mExpanded by remember { mutableStateOf(false) }
    var selectedItemLabel by remember { mutableStateOf(currentItem?.label ?: "") }

    ExposedDropdownMenuBox(
        expanded = mExpanded,
        onExpandedChange = {
            mExpanded = mExpanded.not()
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = readOnly,
            value = selectedItemLabel,
            onValueChange = {}
        )
        ExposedDropdownMenu(
            expanded = mExpanded,
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { mExpanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    selectedItemLabel = item.label
                    mExpanded = mExpanded.not()
                })
                {
                    Text(text = "${item.label}")
                }
            }
        }
    }
}

data class ExposedDropdown(
    val id: String,
    val label: String,
    val data: Any? = null
)