package uz.uzkass.smartpos.supply.android.ui.main.category

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.ProductDetailState
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.ProductViewModel

@Composable
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

        ProductDetailAppBar(onClickBack = onClickBack)

    }) {



    }

}

@Composable
private fun ProductDetailAppBar(onClickBack: () -> Unit) {
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

    }
}
