package uz.uzkass.smartpos.supply.android.ui.main.create_order

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.SearchTextField
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.ui.destinations.AddProductScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectProductViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.models.ProductItemModel

@Composable
@Destination
fun ProductSelectScreen(
    navigator: DestinationsNavigator,
    viewModel: SelectProductViewModel = koinViewModel(),
) {

    val productListState = viewModel.screenState.collectAsStateWithLifecycle()

    ProductSelectScreenView(
        productList = productListState.value.productList,
        onQueryChange = {
            viewModel.getProductByQuery(it)
        },
        onItemClick = {
            navigator.navigate(AddProductScreenDestination(productItem = it))
        },
        onBackPressed = navigator::popBackStack
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ProductSelectScreenView(
    productList: List<ProductItemModel>,
    onQueryChange: (String) -> Unit,
    onItemClick: (ProductItemModel) -> Unit,
    onBackPressed: () -> Unit
) {
    val scrollState = rememberLazyListState()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding(),
        topBar = {
            ProductSelectAppBar(onBackPressed = onBackPressed)
        }) {

        Column(modifier = Modifier.fillMaxSize()) {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onQueryChange = onQueryChange
            )
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
private fun ProductSelectAppBar(onBackPressed: () -> Unit) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onBackPressed
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = "Select товар"
        )

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