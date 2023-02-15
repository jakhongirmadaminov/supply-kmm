package uz.uzkass.smartpos.supply.android.ui.main.create_order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.SearchTextField
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.ui.customers.views.CustomerItem
import uz.uzkass.smartpos.supply.android.ui.destinations.SelectContractScreenDestination
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectCustomerViewModel

@Destination
@Composable
fun SelectCustomerScreen(
    navigator: DestinationsNavigator,
    viewModel: SelectCustomerViewModel = koinViewModel()
) {

    val screenState = viewModel.customerPagingSource.collectAsLazyPagingItems()

    SelectCustomerView(
        customerList = screenState,
        onQueryChange = {
            viewModel.getCustomerByQuery(it)
        },
        onItemClick = { item ->
            navigator.navigate(SelectContractScreenDestination(customerId = item.id))
        }
    )


}

@Composable
private fun SelectCustomerView(
    customerList: LazyPagingItems<CustomerListMobileDTO>,
    onQueryChange: (String) -> Unit,
    onItemClick: (CustomerListMobileDTO) -> Unit
) {
    val scrollState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {


        SearchTextField(
            modifier = Modifier.fillMaxWidth(),
            onQueryChange = onQueryChange
        )
        Spacer16dp()
        LazyColumn(state = scrollState, modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(customerList, key = { item -> item.id.toString() }) { item ->
                    CustomerItem(customerItem = item!!, onClickItem = { onItemClick(item) })
                }
            })
    }
}

//@Composable
//fun CustomerItem(item: CompanyBaseDTO, onClick: (CompanyBaseDTO) -> Unit) {
//    Column(modifier = Modifier.clickable(onClick = { onClick(item) })) {
//        Text(
//
//            text = "${item.name}", color = Color.Black
//        )
//        Text(
//            text = "${item.tin}", color = Color.Black
//        )
//    }
//
//}