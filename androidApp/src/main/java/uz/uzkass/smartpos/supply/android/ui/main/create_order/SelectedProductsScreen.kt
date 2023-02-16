package uz.uzkass.smartpos.supply.android.ui.main.create_order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.ProductItemModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectProductViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectedProductsViewModel
import uz.uzkass.smartpos.supply.settings.OrderProductModel

@Composable
@Destination
fun SelectedProductsScreen(
    viewModel: SelectedProductsViewModel = koinViewModel(),
    navigator: DestinationsNavigator,
) {

    val screenState = viewModel.productDateState.collectAsStateWithLifecycle()

    SelectedProductsView(
        selectedProducts = screenState.value.productList,
        addProductPress = {

        },
        removeProductPress = {

        },
        onClickNext = {

        }
    )

}

@Composable
private fun SelectedProductsView(
    selectedProducts: List<OrderProductModel>,
    addProductPress: () -> Unit,
    removeProductPress: (OrderProductModel) -> Unit,
    onClickNext: () -> Unit,
) {

    LazyColumn {
        items(selectedProducts) { item ->
            SelectedItemContent(item = item) {

            }
        }
    }

}


@Composable
private fun SelectedItemContent(
    item: OrderProductModel,
    onClickItem: () -> Unit
) {

    val labelWidth = LocalConfiguration.current.screenWidthDp - 120
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClickItem
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.width(labelWidth.dp),
            text = item.name.toString(),
            style = SupplyTheme.typography.subtitle2,
        )
        FillAvailableSpace()
        Text(
            fontWeight = FontWeight.SemiBold,
            text = "510" ?: "",
            color = SupplyTheme.colors.productLabel
        )
        Spacer3dp()
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)

    }


}




