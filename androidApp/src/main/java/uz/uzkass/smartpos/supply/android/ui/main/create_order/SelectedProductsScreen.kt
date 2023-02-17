package uz.uzkass.smartpos.supply.android.ui.main.create_order

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.ProductItemModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectProductViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectedProductsViewModel
import uz.uzkass.smartpos.supply.settings.OrderProductModel

@Composable
@Destination
fun SelectedProductsScreen(
    viewModel: SelectedProductsViewModel = koinViewModel(),
    navigator: DestinationsNavigator,
) {

    val screenState = viewModel.productDateState.collectAsStateWithLifecycle()

    val list = screenState.value.productList.toMutableStateList()

    SelectedProductsView(
        selectedProducts = list,
        addProductPress = {

        },
        removeProduct = {
            list.remove(it)
            viewModel.removeProductItem(it)

        },
        onClickNext = {

        },
        onClickBack = navigator::popBackStack
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun SelectedProductsView(
    selectedProducts: SnapshotStateList<OrderProductModel>,
    onClickBack: () -> Unit,
    addProductPress: () -> Unit,
    removeProduct: (OrderProductModel) -> Unit,
    onClickNext: () -> Unit,
) {

    Scaffold(topBar = {
        SelectedProductsAppBar(
            onClickBack = onClickBack
        )
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                itemsIndexed(items = selectedProducts) { index, item ->
                    SelectedItemContent(
                        item = item,
                        onClickItem = {},
                        onRemoveItem = { removeProduct(it) }
                    )
                }
            }

            FillAvailableSpace()
            SupplyFilledTextButton(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                buttonBackgroundColor = SupplyTheme.colors.primary,
                text = "Next",
                enabled = true,
                onClick = {

                })
        }

    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SelectedItemContent(
    item: OrderProductModel,
    onClickItem: () -> Unit,
    onRemoveItem: (OrderProductModel) -> Unit,

    ) {

    val dismissState = rememberDismissState()
    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onRemoveItem(item)
    }
    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 1.dp),
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
        },
        background = {
            val color by animateColorAsState(
                Color.Red
            )

            val alignment = Alignment.CenterEnd

            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = Color.White,
                    contentDescription = "Localized description",
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = {
            Card(
                elevation = animateDpAsState(
                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                ).value
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
                        text = item.name.toString(),
                        style = SupplyTheme.typography.subtitle2,
                    )
                    FillAvailableSpace()
                    Text(
                        fontWeight = FontWeight.SemiBold,
                        text = "510" ?: "",
                        color = SupplyTheme.colors.productLabel
                    )
                    Spacer3dp()
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)

                }
            }
        }
    )
}


@Composable
private fun SelectedProductsAppBar(onClickBack: () -> Unit) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onClickBack
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = "Selected товар"
        )
        FillAvailableSpace()
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_plus_add),
            onClick = onClickBack
        )

    }
}





