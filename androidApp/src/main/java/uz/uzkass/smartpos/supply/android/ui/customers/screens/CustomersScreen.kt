package uz.uzkass.smartpos.supply.android.ui.customers.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.core.Constants.BOTTOM_BAR_HEIGHT
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.Spacer12dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer24dp
import uz.uzkass.smartpos.supply.android.coreui.paginationStates
import uz.uzkass.smartpos.supply.android.ui.customers.views.CustomerItem
import uz.uzkass.smartpos.supply.android.ui.main.navigation.MainNavGraph
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.clients.CustomersEvent
import uz.uzkass.smartpos.supply.viewmodels.clients.CustomersState
import uz.uzkass.smartpos.supply.viewmodels.clients.CustomersViewModel
import uz.uzkassa.smartpos.supply.library.MR

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
@Destination
@MainNavGraph
fun CustomersScreen(
    navigator: DestinationsNavigator,
    viewModel: CustomersViewModel = koinViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
//    val customersLazyPaging = viewModel.customersPaging.collectAsLazyPagingItems()

//    LaunchedEffect(Unit) {
//        viewModel.customersEvent.flowWithLifecycle(lifecycle).collect { event ->
//            when (event) {
//                is CustomersEvent.RefreshCustomers -> {
//                    customersLazyPaging.refresh()
//                }
//            }
//        }
//    }
//
//    CustomersView(
//        viewState = screenState,
//        customersLazyPaging = customersLazyPaging,
//        onClickAdd = {},
//        onClickSearch = {},
//        onClickFilter = {},
//        onRefresh = viewModel::onRefreshCustomers
//    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CustomersView(
    viewState: CustomersState,
    customersLazyPaging: LazyPagingItems<CustomerListMobileDTO>,
    onClickAdd: () -> Unit,
    onClickSearch: () -> Unit,
    onClickFilter: () -> Unit,
    onRefresh: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(refreshing = viewState.isRefreshing, onRefresh = onRefresh)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .systemBarsPadding(),
        topBar = {
            CustomersTopBar(
                onClickAdd = onClickAdd,
                onClickSearch = onClickSearch,
                onClickFilter = onClickFilter
            )
        },
        backgroundColor = SupplyTheme.colors.idleBackground,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = BOTTOM_BAR_HEIGHT.dp, start = 8.dp, end = 8.dp),
                content = {
                    AllCustomersView(
                        customersLazyPaging = customersLazyPaging,
                        onClickCustomerItem = { customerItem ->

                        }
                    )
                    PullRefreshIndicator(
                        refreshing = viewState.isRefreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            )
        }
    )
}

@Composable
private fun AllCustomersView(
    customersLazyPaging: LazyPagingItems<CustomerListMobileDTO>,
    onClickCustomerItem: (customerItem: CustomerListMobileDTO) -> Unit
) {
    LazyColumn {
        item { Spacer12dp() }
        items(items = customersLazyPaging) { customerItem ->
            customerItem?.let { item ->
                CustomerItem(customerItem = item, onClickItem = { onClickCustomerItem(item) })
            }
        }
        paginationStates(customersLazyPaging)
    }

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

/*
@Composable
private fun VisitsView(
    visitsLazyPaging: LazyPagingItems<CustomerListMobileDTO>,
    onClickVisitItem: (visitItem: CustomerListMobileDTO) -> Unit,
    onClickAdd: () -> Unit
) {
    LazyColumn {
        item { Spacer12dp() }
        items(visitsLazyPaging) { visitItem ->
            visitItem?.let { item ->
                VisitItem(
                    visitItem = item,
                    onClickItem = { onClickVisitItem(item) },
                    onclickAdd = onClickAdd
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabContentView(
    pagerState: PagerState,
    tabsCount: Int,
    customersLazyPaging: LazyPagingItems<CustomerListMobileDTO>,
    visitsLazyPaging: LazyPagingItems<CustomerListMobileDTO>
) {
    HorizontalPager(
        count = tabsCount,
        state = pagerState,
        verticalAlignment = Alignment.CenterVertically,
        content = { page ->
            when (page) {
                CustomersTabEnum.ALL.index -> {
                    AllCustomersView(
                        customersLazyPaging = customersLazyPaging,
                        onClickCustomerItem = {

                        }
                    )
                }
                CustomersTabEnum.VISIT.index -> {
                    VisitsView(
                        visitsLazyPaging = visitsLazyPaging,
                        onClickVisitItem = {},
                        onClickAdd = {}
                    )
                }
            }
        }
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun AnimatedTab(
    pagerState: PagerState,
    tabItems: List<Int>,
) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = SupplyTheme.colors.background,
        tabs = {
            tabItems.forEachIndexed { index, tabTitle ->
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
}*/