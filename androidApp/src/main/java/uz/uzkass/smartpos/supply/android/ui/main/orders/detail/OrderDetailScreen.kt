package uz.uzkass.smartpos.supply.android.ui.main.orders.detail

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.mvvm.asState
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.ui.main.create_order.DisableTextWithLabel
import uz.uzkass.smartpos.supply.viewmodels.orders.detail.OrderDetailState
import uz.uzkass.smartpos.supply.viewmodels.orders.detail.OrderDetailViewModel

@Composable
@Destination
fun OrderDetailScreen(
    orderId: Long,
    navigator: DestinationsNavigator,
    viewModel: OrderDetailViewModel = koinViewModel()
) {


    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.loadOrderDetailById(orderId)
    })

    OrderDetailScreenView(
        screenState = screenState.value
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun OrderDetailScreenView(
    screenState: OrderDetailState
) {
    var isEditable by remember {
        mutableStateOf(false)
    }
    var dataExpand by remember() {
        mutableStateOf(false)
    }
    var productsExpand by remember() {
        mutableStateOf(false)
    }

    Scaffold(topBar = {}) {

        Column(modifier = Modifier.fillMaxSize()) {
            ExpandableLayout(title = "Danni", expend = dataExpand, changeExpend = {
                dataExpand = it
            }) {
                DisableTextWithLabel(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Клиент",
                    text = screenState.currentCustomer?.label ?: ""
                )
                Spacer8dp()
                DisableTextWithLabel(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Филиал",
                    text = screenState.customerBranch?.label ?: ""
                )

            }
            ExpandableLayout(title = "Tovari", expend = productsExpand, changeExpend = {
                productsExpand = it
            }) {

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                    items()
                }
            }
        }
    }
}

@Composable
private fun ExpandableLayout(
    title: String,
    expend: Boolean,
    changeExpend: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                changeExpend(expend.not())
            }) {
            Text(text = "")
            FillAvailableSpace()
            val icon = if (expend) {
                Icons.Default.KeyboardArrowRight
            } else {
                Icons.Default.KeyboardArrowDown
            }
            Image(imageVector = icon, contentDescription = null)
        }

        AnimatedVisibility(visible = expend) {
            content()
        }
    }

}

