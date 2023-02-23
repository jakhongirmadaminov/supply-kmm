package uz.uzkass.smartpos.supply.android.ui.main.create_order

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import dev.icerock.moko.network.generated.models.ProductDetailMobileDTO
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.core.PhoneWithPrefixTransformation
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.LabelTextField
import uz.uzkass.smartpos.supply.android.coreui.SearchTextField
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer24dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.SupplyFilledTextButton
import uz.uzkass.smartpos.supply.android.coreui.SupplyTextButton
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField2
import uz.uzkass.smartpos.supply.android.ui.NavGraphs
import uz.uzkass.smartpos.supply.android.ui.customers.views.CustomerItem
import uz.uzkass.smartpos.supply.android.ui.destinations.ProductSelectScreenDestination
import uz.uzkass.smartpos.supply.android.ui.destinations.SelectedProductsScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.ProductItemModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectProductViewModel
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel
import uz.uzkassa.smartpos.supply.library.MR

@Composable
@Destination
fun AddProductScreen(
    productId: Long,
    navigator: DestinationsNavigator,
    viewModel: SelectProductViewModel = koinViewModel(),
) {

    val screenState = viewModel.productDateState.collectAsStateWithLifecycle()


    LaunchedEffect(key1 = Unit, block = {
        viewModel.loadProductData(productId)

    })


    AddProductScreenView(
        productItemModel = screenState.value.productData,
        unitList = screenState.value.unitList,
        onUnitSelected = {

        },
        onClickBack = {
            navigator.navigateUp()
        },

        onProductAdd = {
            viewModel.addProduct(it)
            navigator.navigate(SelectedProductsScreenDestination) {
                popUpTo(ProductSelectScreenDestination.route)
            }
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun AddProductScreenView(
    productItemModel: ProductDetailMobileDTO?,
    unitList: List<DropdownModel>,
    onClickBack: () -> Unit,
    onProductAdd: (Int) -> Unit,
    onUnitSelected: (DropdownModel) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            AddProductAppBar(
                onClickBack = onClickBack
            )
        },
        backgroundColor = SupplyTheme.colors.idleBackground,
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                val labelWidth = LocalConfiguration.current.screenWidthDp - 120
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier.width(labelWidth.dp),
                        text = productItemModel?.name.toString(),
                        style = SupplyTheme.typography.subtitle2,
                    )
                    FillAvailableSpace()
                    Text(
                        fontWeight = FontWeight.SemiBold,
                        text = productItemModel?.price.toString() ?: "",
                        color = SupplyTheme.colors.productLabel
                    )
                    Spacer3dp()
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )

                }

                ExposedDropdownField2(
                    items = unitList,
                    label = stringResource(id = MR.strings.type_sell.resourceId),
                    onItemSelected = onUnitSelected
                )


                var valueState by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = valueState,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        onProductAdd(valueState.toIntOrNull() ?: 0)
                    }),
                    onValueChange = {
                        if (it == "") {
                            valueState = it
                            return@OutlinedTextField
                        }
                        val count = it.toIntOrNull()
                        if (count != null) {
                            valueState = it
                        }
                    }
                )

                FillAvailableSpace()

                SupplyFilledTextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    buttonBackgroundColor = SupplyTheme.colors.primary,
                    text = "+",
                    enabled = valueState != "",
                    onClick = {
                        onProductAdd(
                            valueState.toIntOrNull() ?: 0
                        )
                    })
            }
        }
    )
}

@Composable
private fun AddProductAppBar(onClickBack: () -> Unit) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onClickBack
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = "Добавить товар"
        )

    }
}











