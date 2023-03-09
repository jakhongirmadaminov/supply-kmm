package uz.uzkass.smartpos.supply.android.ui.main.orders

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer24dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer76dp
import uz.uzkass.smartpos.supply.android.ui.destinations.OrderDetailScreenDestination
import uz.uzkass.smartpos.supply.android.ui.main.create_order.isScrolledToEnd
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.orders.OrdersViewModel
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrderStatusModel
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrderStatusCodeEnum
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrdersModel
import uz.uzkassa.smartpos.supply.library.MR

@Destination
@Composable
fun OrdersScreen(
    navigator: DestinationsNavigator,
    viewModel: OrdersViewModel = koinViewModel()
) {

    val ordersList = viewModel.ordersList.collectAsState()
    LaunchedEffect(key1 = Unit, block = {
//        viewModel.loadAllData()
    })

    OrdersScreenView(
        ordersList = ordersList.value,
        onItemClick = {
            navigator.navigate(OrderDetailScreenDestination(orderId = it.id ?: 0))
        },
        loadNext = viewModel::loadNextPage
    )


}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrdersScreenView(
    ordersList: List<OrdersModel>,
    onItemClick: (OrdersModel) -> Unit,
    loadNext: () -> Unit
) {

    val listState = rememberLazyListState()

    Scaffold(topBar = {
        OrdersTopBar(
            onClickSearch = {

            },
            onClickFilter = {

            }
        )
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ordersList) { item ->
                    OrderItemContent(item = item, onClickItem = onItemClick)
                }
                item {
                    Spacer76dp()
                }
            }
            val endOfListReached = remember {
                derivedStateOf { listState.isScrolledToEnd() }
            }

            if (endOfListReached.value) {
                loadNext()
            }
        }

    }


}

@Composable
private fun OrderItemContent(
    item: OrdersModel,
    onClickItem: (OrdersModel) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClickItem(item) }
        .padding(16.dp)
    ) {
        OrderItemText(
            leftText = item.orderNumber,
            rightText = item.totalPrice.toString()
        )
        OrderItemStatus(status = item.status)
        OrderItemText(
            leftText = item.customer,
            rightText = item.orderDate
        )

    }
}

@Composable
private fun OrderItemText(
    leftText: String,
    rightText: String,
    leftStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = SupplyTheme.colors.productLabel
    ),

    rightStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = SupplyTheme.colors.productLabel
    ),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = leftText, style = leftStyle)
        FillAvailableSpace()
        Text(text = rightText, style = rightStyle)
    }

}

@Composable
private fun OrderItemStatus(status: OrderStatusModel) {

    val statusColor = when (status.code) {
        OrderStatusCodeEnum.NEW -> {
            SupplyTheme.colors.orderStatusNew
        }
        OrderStatusCodeEnum.COMPLETED -> {
            SupplyTheme.colors.orderStatusComplited
        }
        OrderStatusCodeEnum.APPROVED -> {
            SupplyTheme.colors.orderStatusApproved
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(4.dp),
            shape = CircleShape,
            color = statusColor
        ) {}

        Text(
            text = status.name,
            color = statusColor,
            fontSize = 12.sp,
        )

    }
}


@Composable
private fun OrdersTopBar(
    onClickSearch: () -> Unit,
    onClickFilter: () -> Unit,
) {
    DefaultAppBar {
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = stringResource(id = MR.strings.clients.resourceId)
        )

        Spacer24dp()
        AppBarButton(
            imageVector = Icons.Default.Search,
            onClick = onClickSearch
        )
        Spacer24dp()
        AppBarButton(
            imageVector = Icons.Default.Edit,
            onClick = onClickFilter
        )
    }
}

