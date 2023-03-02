package uz.uzkass.smartpos.supply.android.ui.main.orders.detail

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun OrderDetailScreen(
    orderId: Long,
    navigator: DestinationsNavigator,

    ) {

    OrderDetailScreenView()

}

@Composable
private fun OrderDetailScreenView() {

}