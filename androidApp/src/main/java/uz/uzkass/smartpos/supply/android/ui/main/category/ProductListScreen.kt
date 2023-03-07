package uz.uzkass.smartpos.supply.android.ui.main.category

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.ProductListViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.models.ProductItemModel

@Composable
@Destination
fun ProductListScreen(
    categoryId: Long,
    navigator: DestinationsNavigator,
    viewModel: ProductListViewModel
) {

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getProductListByCategoryId(categoryId)
    })

//    ProductListScreenView(
//        productList =,
//        onItemClick = {},
//        onClickBack = navigator::popBackStack
//    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ProductListScreenView(
    productList: LazyPagingItems<ProductItemModel>,
    onItemClick: (ProductItemModel) -> Unit,
    onClickBack: () -> Unit
) {
    val scrollState = rememberLazyListState()
    Scaffold(topBar = {
        ProductListScreenAppBar(onClickBack = onClickBack)
    }) {
        LazyColumn(state = scrollState, modifier = Modifier
            .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(productList, key = { item -> item.id.toString() }
                ) { item ->
                    ProductItem(
                        item = item!!,
                        onClickItem = { onItemClick(item) })
                }
            })

    }
}


@Composable
private fun ProductItem(
    item: ProductItemModel,
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
            text = item.label.toString(),
            style = SupplyTheme.typography.subtitle2,
        )
        FillAvailableSpace()
        Text(
            fontWeight = FontWeight.SemiBold,
            text = item.price.toString() ?: "",
            color = SupplyTheme.colors.productLabel
        )
        Spacer3dp()
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)

    }

}

@Composable
private fun ProductListScreenAppBar(onClickBack: () -> Unit) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onClickBack
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = "List товар"
        )

    }
}
