package uz.uzkass.smartpos.supply.android.ui.main.create_order

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.SearchTextField
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.ui.destinations.AddProductScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.ProductItemModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectProductViewModel

@Composable
@Destination
fun ProductSelectScreen(
    navigator: DestinationsNavigator,
    viewModel: SelectProductViewModel = koinViewModel(),
) {

    val productListState = viewModel.productPage.collectAsLazyPagingItems()

    ProductSelectScreenView(productList = productListState,
        onQueryChange = {
            viewModel.getProductByQuery(it)
        },
        onItemClick = {
            navigator.navigate(AddProductScreenDestination(it.id!!))
        })
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ProductSelectScreenView(
    productList: LazyPagingItems<ProductItemModel>,
    onQueryChange: (String) -> Unit,
    onItemClick: (ProductItemModel) -> Unit
) {
    val scrollState = rememberLazyListState()


    Scaffold(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding(),
        topBar = {
            
        }) {


    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onQueryChange = onQueryChange
            )
            Spacer16dp()
            LazyColumn(state = scrollState, modifier = Modifier
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(productList, key = { item -> item.id.toString() }
                    ) { item ->
                        ProductItem(item = item!!, onClickItem = { onItemClick(item) })
                    }
                })
        }


    }
}

@Composable
fun ProductSelectAppBar() {
    
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
            text = item.price ?: "",
            color = SupplyTheme.colors.productLabel
        )
        Spacer3dp()
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)

    }

}