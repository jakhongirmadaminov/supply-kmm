package uz.uzkass.smartpos.supply.android.ui.customers

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.Spacer12dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer24dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer8dp
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.clients.CustomersViewModel
import uz.uzkassa.smartpos.supply.library.MR

enum class CustomersTabEnum(val index: Int) {
    ALL(0), VISIT(1)
}

@Composable
@Destination
fun CustomersScreen(
//    navigator: DestinationsNavigator,
    viewModel: CustomersViewModel = koinViewModel()
) {
    val customersLazyPaging = viewModel.commonPagingData.collectAsLazyPagingItems()

    CustomersView(
        customersLazyPaging = customersLazyPaging,
        onClickAdd = {},
        onClickSearch = {},
        onClickFilter = {}
    )

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
private fun CustomersView(
    customersLazyPaging: LazyPagingItems<CustomerListMobileDTO>,
    onClickAdd: () -> Unit,
    onClickSearch: () -> Unit,
    onClickFilter: () -> Unit
) {
    val tabsResource by remember {
        mutableStateOf(listOf(MR.strings.clients_tab_all.resourceId, MR.strings.clients_tab_visit.resourceId))
    }
    val pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = {})
    val pagerState = rememberPagerState()


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            Column {
                CustomersTopBar(
                    onClickAdd = onClickAdd,
                    onClickSearch = onClickSearch,
                    onClickFilter = onClickFilter
                )
                AnimatedTab(
                    pagerState = pagerState,
                    tabsResource = tabsResource
                )
            }
        },
        backgroundColor = SupplyTheme.colors.background,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                content = {
                    TabContentView(
                        pagerState = pagerState,
                        tabItems = tabsResource,
                        customersLazyPaging = customersLazyPaging,
                    )
                    PullRefreshIndicator(
                        refreshing = false,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabContentView(
    pagerState: PagerState,
    tabItems: List<Int>,
    customersLazyPaging: LazyPagingItems<CustomerListMobileDTO>
) {
    HorizontalPager(
        count = tabItems.size,
        state = pagerState,
        verticalAlignment = Alignment.CenterVertically,
        content = { page ->
            when (page) {
                CustomersTabEnum.ALL.index -> {
                    AllCustomersView(customersLazyPaging)
                }
                CustomersTabEnum.VISIT.index -> {
                    Text(modifier = Modifier.fillMaxSize(), text = "VISIT")
                }
            }
        }
    )
}

@Composable
private fun AllCustomersView(customersLazyPaging: LazyPagingItems<CustomerListMobileDTO>) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
        content = {
            items(items = customersLazyPaging) { customerItem ->
                customerItem?.let { item ->
                    CustomerItem(customerItem = item, onClickItem = {})
                }
            }
        }
    )
}

@Composable
private fun CustomerItem(
    customerItem: CustomerListMobileDTO,
    onClickItem: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(64.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                color = Color.Black,
                text = customerItem.name ?: "",
                fontSize = 14.sp
            )
            Spacer8dp()
            Text(
                text = customerItem.tin ?: "",
                color = Color.Black,
                fontSize = 14.sp
            )
        }
        Spacer12dp()
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                color = Color.Black,
                text = customerItem.brand ?: "",
                fontSize = 14.sp
            )
            Spacer8dp()
            Text(
                text = "${customerItem.phone}",
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun AnimatedTab(
    pagerState: PagerState,
    tabsResource: List<Int>,
) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        selectedTabIndex = pagerState.currentPage,
        tabs = {
            tabsResource.forEachIndexed { index, tabTitle ->
                Tab(
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    selected = index == pagerState.currentPage,
                    content = {
                        Text(text = stringResource(id = tabTitle))
                    }
                )
            }
        }
    )
}

@Composable
private fun CustomersTopBar(
    onClickAdd: () -> Unit,
    onClickSearch: () -> Unit,
    onClickFilter: () -> Unit,
) {
    DefaultAppBar {
        AppBarTitle(
            modifier = Modifier.weight(1f),
            title = stringResource(id = MR.strings.clients.resourceId)
        )
        AppBarButton(
            imageVector = Icons.Default.Add,
            onClick = onClickAdd
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