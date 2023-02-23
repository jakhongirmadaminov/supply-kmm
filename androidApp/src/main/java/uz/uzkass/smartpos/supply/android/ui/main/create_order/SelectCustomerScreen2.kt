package uz.uzkass.smartpos.supply.android.ui.main.create_order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.SearchTextField
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.ui.customers.views.CustomerItem
import uz.uzkass.smartpos.supply.viewmodels.SelectCustomerViewModel2

@Destination
@Composable
fun SelectCustomerScreen2(
    navigator: DestinationsNavigator,
    viewModel: SelectCustomerViewModel2 = koinViewModel()
) {

    val pagingStateChange = viewModel.pagedData.collectAsState()
    SelectCustomerView(
        data = pagingStateChange.value,
        onQueryChange = { query ->
            viewModel.onQuery(query)
        },
        onItemClick = { item ->

        },
        loadNext = {
            viewModel.loadNext()
        })

}

@Composable
private fun SelectCustomerView(
    data: List<CustomerListMobileDTO>?,
    onQueryChange: (String) -> Unit,
    onItemClick: (CustomerListMobileDTO) -> Unit,
    loadNext: () -> Unit
) {
    val listState = rememberLazyListState()




    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            SearchTextField(
                modifier = Modifier.fillMaxWidth(), onQueryChange = onQueryChange
            )
            Spacer16dp()
            LazyColumn(state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    data?.let {
                        items(data.size) { index ->
                            CustomerItem(customerItem = data[index],
                                onClickItem = { onItemClick(data[index]) })
                        }
                    }
                })
        }

    }
    // observer when reached end of list
    val endOfListReached = remember {
        derivedStateOf { listState.isScrolledToEnd() }
    }

    if (endOfListReached.value) {
        loadNext()
    }

}

fun LazyListState.isScrolledToEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
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