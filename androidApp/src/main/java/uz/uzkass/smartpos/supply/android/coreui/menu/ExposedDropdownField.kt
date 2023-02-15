package uz.uzkass.smartpos.supply.android.coreui.menu

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.android.coreui.LabelText
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.android.ui.theme.LocalSpacing
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownField(
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
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
    var mExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        valueDebounce
            .debounce(500)
            .distinctUntilChanged()
            .collectLatest {
                onQueryChange(it)
            }
    })


    var selectedItemLabel by remember {
        val text = currentItem?.label ?: ""
        mutableStateOf(
            TextFieldValue(text = text, TextRange(text.length))
        )
    }

    ExposedDropdownMenuBox(
        expanded = mExpanded,
        onExpandedChange = {
            mExpanded = it
        },
        modifier = modifier,
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            LabelText(label = label)
            Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmall4dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = SupplyTheme.colors.textFieldBorder,
                        shape = LocalShapes.current.small8Dp
                    )
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
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                ),
                placeholder = {
                    Text(text = placeholder)
                }
            )
        }

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
                        val text = if (item.label.length > 20) {
                            item.label.substring(0, 19)
                        } else {
                            item.label
                        }
                        selectedItemLabel =
                            TextFieldValue(
                                text = text,
                                TextRange(text.length)
                            )
                        onItemSelected(item)
                        mExpanded = false
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

@Composable
fun ExposedDropdownField2(
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    isError: Boolean = false,
    items: List<DropdownModel>,
    readOnly: Boolean = true,
    onItemSelected: (DropdownModel) -> Unit,
    onQueryChange: (String) -> Unit = {}
) {
    var mExpanded by remember { mutableStateOf(false) }
    var firstTime by remember { mutableStateOf(true) }

    val valueDebounce = remember {
        MutableStateFlow("")
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit, block = {
        valueDebounce
            .debounce(500)
            .distinctUntilChanged()
            .collectLatest {
                if (!firstTime) {
                    onQueryChange(it)
                }
            }
    })


    var selectedItemLabel by remember {
        val item = items.first()
        onItemSelected(item)
        mutableStateOf(
            TextFieldValue(text =  item.label, TextRange( item.label.length))
        )
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()
    LaunchedEffect(isPressed) {
        if (isPressed) {
            mExpanded = true
        }
    }
    Column(modifier = modifier) {
        LabelText(label = label)
        Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmall4dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = readOnly,
            value = selectedItemLabel,
            onValueChange = {
                selectedItemLabel = it
                firstTime = false
                coroutineScope.launch {
                    valueDebounce.emit(it.text)
                }
            },
            maxLines = 1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = SupplyTheme.colors.primary,
//                disabledBorderColor = SupplyTheme.colors.textFieldBorder,
                unfocusedBorderColor = SupplyTheme.colors.textFieldBorder,
                textColor = Color.Black
            ),
            placeholder = {
                Text(text = placeholder)
            },
            interactionSource = interactionSource,
            isError = isError,
            trailingIcon = {
                IconToggleButton(
                    checked = mExpanded,
                    onCheckedChange = {
                        mExpanded = it
                    }
                ) {
                    if (mExpanded) Icon(
                        imageVector = Icons.Outlined.KeyboardArrowUp,
                        contentDescription = null
                    ) else Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            },
        )

        DropdownMenu(
            expanded = mExpanded,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .requiredSizeIn(maxHeight = 400.dp),
            onDismissRequest = {
                mExpanded = false
            },
            properties = PopupProperties(focusable = false)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        val text = if (item.label.length > 25) {
                            item.label.substring(0, 24)
                        } else {
                            item.label
                        }
                        selectedItemLabel =
                            TextFieldValue(
                                text = text,
                                TextRange(text.length)
                            )
                        onItemSelected(item)
                        mExpanded = false
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
