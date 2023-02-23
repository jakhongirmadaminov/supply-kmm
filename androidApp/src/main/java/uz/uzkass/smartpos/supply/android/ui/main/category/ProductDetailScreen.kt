package uz.uzkass.smartpos.supply.android.ui.main.category

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.ui.main.create_order.DisableTextWithLabel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.ProductDetailState
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.ProductViewModel

@Composable
@Destination
fun ProductDetailScreen(
    productId: Long,
    navigator: DestinationsNavigator,
    viewModel: ProductViewModel = koinViewModel()
) {

    val productState = viewModel.productState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getProductInfoById(productId)
    })
    ProductDetailScreenView(
        productDetail = productState.value,
        onClickBack = navigator::popBackStack,
        onClickFavorite = {

        }
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProductDetailScreenView(
    loading: Boolean = false,
    productDetail: ProductDetailState,
    onClickBack: () -> Unit,
    onClickFavorite: () -> Unit
) {

    Scaffold(topBar = {

        ProductDetailAppBar(onClickBack = onClickBack, onClickFavorite = onClickFavorite)

    }) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(text = productDetail.name ?: "")

            DisableTextWithLabel(label = "IKPU", text = productDetail.vatBarcode ?: "")
            DisableTextWithLabel(label = "Штрих код", text = productDetail.barcode ?: "")
            DisableTextWithLabel(label = "type pa", text = productDetail.packageName ?: "")

            Row(modifier = Modifier.fillMaxWidth()) {
                DisableTextWithLabel(
                    modifier = Modifier.weight(1f),
                    label = "IKPU",
                    text = productDetail.vatBarcode ?: ""
                )
                DisableTextWithLabel(
                    modifier = Modifier.weight(1f),
                    label = "IKPU",
                    text = productDetail.vatBarcode ?: ""
                )
            }
        }
    }
}

@Composable
private fun ProductDetailAppBar(onClickBack: () -> Unit, onClickFavorite: () -> Unit) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onClickBack
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = "Info товар"
        )
        FillAvailableSpace()
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_plus_add),
            onClick = onClickFavorite
        )
    }
}
