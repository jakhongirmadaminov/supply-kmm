package uz.uzkass.smartpos.supply.android.coreui.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkassa.smartpos.supply.library.MR

@OptIn(FlowPreview::class)
@Composable
fun SearchTextFieldWithIcon(
    modifier: Modifier = Modifier,
    valueChange: (String) -> Unit,
    onClickClose: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val valueFlow = remember { MutableStateFlow<String?>(null) }
    val textState = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        valueFlow
            .debounce(500)
            .distinctUntilChanged()
            .collect { value ->
                value?.let { it -> valueChange(it) }
            }
    }

    Row(
        modifier = modifier
            .background(color = SupplyTheme.colors.background)
            .fillMaxWidth()
            .border(width = 1.dp, color = SupplyTheme.colors.lineColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer8dp()
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = SupplyTheme.colors.lineColor
        )
        TextField(
            modifier = Modifier.weight(1f),
            value = textState.value,
            placeholder = {
                Text(
                    text = stringResource(id = MR.strings.search.resourceId),
                    color = SupplyTheme.colors.lineColor
                )
            },
            onValueChange = {
                textState.value = it
                coroutineScope.launch {
                    valueFlow.emit(it)
                }
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent
            )
        )
        Spacer8dp()
        IconButton(
            onClick = { if (textState.value.isBlank()) onClickClose() else textState.value = "" },
            content = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = SupplyTheme.colors.lineColor
                )
            }
        )
    }
}