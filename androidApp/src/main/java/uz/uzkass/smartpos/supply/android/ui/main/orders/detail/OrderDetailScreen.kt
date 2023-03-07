package uz.uzkass.smartpos.supply.android.ui.main.orders.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.mvvm.asState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField3
import uz.uzkass.smartpos.supply.android.ui.main.create_order.DisableTextWithLabel
import uz.uzkass.smartpos.supply.android.ui.main.create_order.SelectedItemContent
import uz.uzkass.smartpos.supply.android.ui.theme.LocalColors
import uz.uzkass.smartpos.supply.settings.OrderProductModel
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel
import uz.uzkass.smartpos.supply.viewmodels.orders.detail.OrderChangeStatus
import uz.uzkass.smartpos.supply.viewmodels.orders.detail.OrderDetailState
import uz.uzkass.smartpos.supply.viewmodels.orders.detail.OrderDetailViewModel

@Composable
@Destination
fun OrderDetailScreen(
    orderId: Long,
    navigator: DestinationsNavigator,
    viewModel: OrderDetailViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val screenState = viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.loadOrderDetailById(orderId)
    })
    LaunchedEffect(key1 = Unit, block = {
        viewModel.orderChangeStatus.collectLatest {
            when (it) {
                is OrderChangeStatus.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is OrderChangeStatus.Success -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    navigator.popBackStack()
                }
            }
        }

    })

    OrderDetailScreenView(
        screenState = screenState.value,
        onCustomerSelect = viewModel::selectCustomerItem,
        onBackPressed = navigator::popBackStack,
        onBranchSelect = viewModel::selectCustomerBranchItem,
        onSave = viewModel::onSave,
        onRemoveProduct = viewModel::removeProduct
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun OrderDetailScreenView(
    screenState: OrderDetailState,
    onCustomerSelect: (DropdownModel?) -> Unit,
    onBranchSelect: (DropdownModel?) -> Unit,
    onBackPressed: () -> Unit,
    onSave: () -> Unit,
    onRemoveProduct: (OrderProductModel) -> Unit
) {
    val listProduct = screenState.productList.toMutableStateList()

    var isEditable by remember {
        mutableStateOf(false)
    }
    var dataExpand by remember() {
        mutableStateOf(false)
    }
    var productsExpand by remember() {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        OrderDetailAppBar(
            showEditIcon = true,
            orderNumber = screenState.screenTitle,
            vatAmount = screenState.totalVatPrice.toString(),
            onBackPressed = onBackPressed,
            onEditClick = {
                isEditable = true
            },
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ExpandableLayout(
                    title = "Danni",
                    expend = dataExpand,
                    changeExpend = {
                        dataExpand = it
                    }) {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        if (!screenState.customerList.isNullOrEmpty()) {
                            ExposedDropdownField3(
                                modifier = Modifier.fillMaxWidth(),
                                label = "Customer",
                                items = screenState.customerList,
                                currentItem = screenState.currentCustomer,
                                autoSelectFirst = false,
                                readOnly = !isEditable,
                                onItemSelected = onCustomerSelect
                            )
                        }
                        Spacer16dp()
                        if (!screenState.branchList.isNullOrEmpty()) {
                            ExposedDropdownField3(
                                modifier = Modifier.fillMaxWidth(),
                                label = "Филиал",
                                items = screenState.branchList,
                                currentItem = screenState.customerBranch,
                                autoSelectFirst = false,
                                readOnly = !isEditable,
                                onItemSelected = onBranchSelect
                            )
                        }

                    }
                }

                Spacer16dp()
                ExpandableLayout(title = "Tovari",
                    expend = productsExpand, changeExpend = {
                        productsExpand = it
                    }) {

                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(listProduct) { item ->
                            SelectedItemContent(
                                item = item,
                                onClickItem = {},
                                onRemoveItem = {
                                    onRemoveProduct(it)
                                    listProduct.remove(it)
                                },
                                isRemovable = isEditable
                            )
                        }
                    }
                }
            }

            SupplyFilledTextButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = "Сохранить",
                enabled = isEditable,
                onClick = onSave
            )
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickable {
                    changeExpend(expend.not())
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title)
            FillAvailableSpace()
            val icon = if (expend) {
                Icons.Default.KeyboardArrowRight
            } else {
                Icons.Default.KeyboardArrowDown
            }
            Image(imageVector = icon, contentDescription = null)
        }

        Spacer16dp()
        AnimatedVisibility(visible = expend) {
            content()
        }
    }
}

@Composable
fun OrderDetailAppBar(
    showEditIcon: Boolean = true,
    orderNumber: String,
    vatAmount: String,
    onBackPressed: () -> Unit,
    onEditClick: () -> Unit
) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onBackPressed
        )
        Spacer16dp()
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = orderNumber, fontSize = 14.sp, color = LocalColors.current.h2)
            Spacer3dp()
            Text(text = "НДС: $vatAmount сум", fontSize = 12.sp, color = LocalColors.current.label)
        }
        FillAvailableSpace()

        AppBarButton(
            imageVector = Icons.Default.Edit,
            onClick = onEditClick
        )
    }
}
