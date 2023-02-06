package uz.uzkass.smartpos.supply.android.coreui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownField(
    modifier: Modifier = Modifier,
//    expanded: Boolean,
    items: List<DropdownModel>,
    currentItem: DropdownModel? = null,
    readOnly: Boolean = true,
    onItemSelected: (DropdownModel) -> Unit,
    onQueryChange: (String) -> Unit = {}
) {
    val valueDebounce = remember {
        MutableStateFlow("")
    }
    val coroutineScope = rememberCoroutineScope()
    val source = remember {
        MutableInteractionSource()
    }
    LaunchedEffect(key1 = Unit, block = {
        valueDebounce
            .debounce(500)
            .distinctUntilChanged()
            .collectLatest(
                onQueryChange
            )
    })


    var mExpanded by remember { mutableStateOf(false) }
    var selectedItemLabel by remember {
        val text = currentItem?.label ?: ""
        mutableStateOf(
            TextFieldValue(text = text, TextRange(text.length))
        )
    }

    ExposedDropdownMenuBox(
        expanded = mExpanded,
        onExpandedChange = {
            mExpanded = true
        },
        modifier = modifier,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) {
                        mExpanded = true
                    }
                },
            readOnly = readOnly,
            value = selectedItemLabel,
            onValueChange = {
                selectedItemLabel = it
                coroutineScope.launch {
                    valueDebounce.emit(it.text)
                }
                mExpanded = true
            },
//            interactionSource = source
        )
        ExposedDropdownMenu(
            expanded = mExpanded,
            modifier = Modifier
                .fillMaxWidth(),
            onDismissRequest = {
                mExpanded = false
            }) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedItemLabel =
                            TextFieldValue(
                                text = item.label,
                                TextRange(item.label.length)
                            )
                        onItemSelected(item)
                        mExpanded = mExpanded.not()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Text(text = "${item.label}")
                }
            }
        }

//        val pressedState = source.interactions.collectAsState(
//            initial = PressInteraction.Cancel(PressInteraction.Press(Offset.Zero))
//        )
//
//        if (pressedState.value is PressInteraction.Release) {
//            mExpanded = true
//            source.tryEmit(PressInteraction.Cancel(PressInteraction.Press(Offset.Zero)))
//        }
    }
}
