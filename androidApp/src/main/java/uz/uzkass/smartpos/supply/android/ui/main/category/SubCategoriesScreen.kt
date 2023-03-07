package uz.uzkass.smartpos.supply.android.ui.main.category

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.network.generated.models.CategoryTreeDTO
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer3dp
import uz.uzkass.smartpos.supply.android.coreui.menu.ExposedDropdownField2
import uz.uzkass.smartpos.supply.android.ui.destinations.ProductDetailScreenDestination
import uz.uzkass.smartpos.supply.android.ui.destinations.SubCategoriesScreenDestination
import uz.uzkass.smartpos.supply.android.ui.main.create_order.isScrolledToEnd
import uz.uzkass.smartpos.supply.android.ui.main.navigation.MainNavGraph
import uz.uzkass.smartpos.supply.android.ui.theme.LocalShapes
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.CategoriesViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.SubCategoriesViewModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.category.model.SubCategoryModel
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.models.ProductItemModel
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel
import uz.uzkassa.smartpos.supply.library.MR

@Composable
@Destination
fun SubCategoriesScreen(
    branchId: Long,
    parentId: Long,
    navigator: DestinationsNavigator,
    viewModel: SubCategoriesViewModel = koinViewModel()
) {
    val screenState = viewModel.screenState.collectAsState()

    val categoryList = viewModel.pagedData.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getSubCategoryList(branchId, parentId)
    })

    SubCategoriesView(
        loading = screenState.value.loading,
        categoryList = categoryList.value,
        onCLickItem = {
            if (it.category == true) {
                navigator.navigate(
                    SubCategoriesScreenDestination(
                        branchId = branchId,
                        parentId = it.id!!
                    )
                )
            } else {
                navigator.navigate(
                    ProductDetailScreenDestination(
                        productId = it.id!!
                    )
                )
            }
        },
        loadNextPage = viewModel::loadNextPage,
        onClickBack = navigator::popBackStack
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun SubCategoriesView(
    loading: Boolean,
    categoryList: List<SubCategoryModel>,
    onCLickItem: (SubCategoryModel) -> Unit,
    loadNextPage: () -> Unit,
    onClickBack: () -> Unit
) {
    val listState = rememberLazyListState()
    Scaffold(modifier = Modifier
        .systemBarsPadding()
        .fillMaxSize(),
        topBar = {
            SubCategoryAppBar(onClickBack = onClickBack)
        }) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.White),

            ) {

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categoryList) { item ->
                    val text = if ((item.name?.length ?: 0) > 25) {
                        item.name!!.substring(0, 24)
                    } else {
                        item.name ?: ""
                    }
                    if (item.category == true) {
                        CategoryItem(
                            name = text,
                            count = 0
                        ) {
                            onCLickItem(item)
                        }
                    } else {
                        ProductItem(
                            label = item.name.toString(),
                            price = item.id.toString()
                        ) {
                            onCLickItem(item)
                        }
                    }
                }
            }

            val endOfListReached = remember {
                derivedStateOf { listState.isScrolledToEnd() }
            }

            if (endOfListReached.value) {
                loadNextPage()
            }

        }
    }

}


@Composable
private fun ProductItem(
    label: String,
    price: String,
    onClickItem: () -> Unit
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
            text = label,
            style = SupplyTheme.typography.subtitle2,
        )
        FillAvailableSpace()
        Text(
            fontWeight = FontWeight.SemiBold,
            text = price,
            color = SupplyTheme.colors.productLabel
        )
        Spacer3dp()
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)

    }

}

@Composable
private fun SubCategoryAppBar(onClickBack: () -> Unit) {
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
