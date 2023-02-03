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
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownField(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    items: List<DropdownModel> = emptyList(),
    currentItem: DropdownModel? = null,
    readOnly: Boolean = true,
    onDismissRequest: () -> Unit,
    onItemSelected: (DropdownModel) -> Unit,
    onQueryChange: (String) -> Unit = {}
) {

    var mExpanded by remember { mutableStateOf(false) }
    var selectedItemLabel by remember { mutableStateOf(currentItem?.label ?: "") }

    ExposedDropdownMenuBox(
        expanded = mExpanded,
        onExpandedChange = {
            mExpanded = mExpanded.not()
        },
        modifier = modifier,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = readOnly,
            value = selectedItemLabel,
            onValueChange = onQueryChange
        )
        ExposedDropdownMenu(
            expanded = mExpanded,
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { mExpanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        selectedItemLabel = item.label
                        mExpanded = mExpanded.not()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Text(text = "${item.label}")
                }
            }
        }
    }
}
