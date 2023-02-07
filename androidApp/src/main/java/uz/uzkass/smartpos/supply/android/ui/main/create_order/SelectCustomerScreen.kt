package uz.uzkass.smartpos.supply.android.ui.main.create_order

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.Spacer12dp
import uz.uzkass.smartpos.supply.viewmodels.home.SelectCustomerViewModel
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

@Destination
@Composable

fun SelectCustomerScreen(
    navigator: DestinationsNavigator,
    viewModel: SelectCustomerViewModel = koinViewModel()
) {

//
//    SelectCustomerView(
//
//    )
}

@Composable
private fun SelectCustomerView(customersLazyPaging: LazyPagingItems<CustomerListMobileDTO>) {

    LazyColumn(content = {
        item { Spacer12dp() }
        items(items = customersLazyPaging) { customerItem ->
            customerItem?.let { item ->
                uz.uzkass.smartpos.supply.android.ui.customers.views.CustomerItem(
                    customerItem = item,
                    onClickItem = { })
            }
        }
    })
}

//@Composable
//fun CustomerItem(item: DropdownModel) {
//    Text(text = item.label, color = Color.Black)
//}