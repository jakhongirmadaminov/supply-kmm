package uz.uzkass.smartpos.supply.android.ui.main.create_order.selectcustomer

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.R
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.FillAvailableSpace
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.ui.destinations.SelectContractScreenDestination
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.SelectCustomerBranchViewModel
import uz.uzkassa.smartpos.supply.library.MR

@Destination
@Composable
fun SelectCustomerBranchScreen(
    navigator: DestinationsNavigator,
    customerId: Long,
    viewModel: SelectCustomerBranchViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getCustomerBranch(customerId = customerId)
    })

    val screenState = viewModel.screenState.collectAsState()

    SelectCustomerBranchScreenView(
        loading = screenState.value.loading,
        customerBranchList = screenState.value.customerBranchList,
        onClickItem = {
            viewModel.selectCustomerBranch(it)
            navigator.navigate(
                SelectContractScreenDestination(customerId = customerId)
            )
        },
        onBackPressed = navigator::popBackStack
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun SelectCustomerBranchScreenView(
    loading: Boolean,
    customerBranchList: List<CustomerBranchModel>,
    onClickItem: (CustomerBranchModel) -> Unit,
    onBackPressed: () -> Unit
) {

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFFBFBFB)),
        topBar = {
            SelectCustomerBranchScreenAppBar(onBackPressed)
        }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(customerBranchList) { item ->
                    CustomerBranchItemContent(
                        item = item,
                        onClickItem = onClickItem
                    )
                }
            }
        }
    }

}

@Composable
private fun CustomerBranchItemContent(
    item: CustomerBranchModel,
    onClickItem: (CustomerBranchModel) -> Unit
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClickItem(item) },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = SupplyTheme.colors.mediumTitle,
                        text = item.name,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer8dp()
//                    Text(
//                        text = customerItem.brand ?: "",
//                        color = SupplyTheme.colors.h2,
//                        fontSize = 14.sp,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
                }

                FillAvailableSpace()

                Image(
                    painter = painterResource(id = R.drawable.ic_circle_arrow_right),
                    contentDescription = null
                )

            }
        }
    )


}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun SelectCustomerBranchScreenAppBar(onBackPressed: () -> Unit) {
    DefaultAppBar {
        AppBarButton(
            painter = painterResource(id = R.drawable.ic_back),
            onClick = onBackPressed
        )
        Spacer16dp()
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = stringResource(id = MR.strings.customer_branch.resourceId)
        )

    }
}