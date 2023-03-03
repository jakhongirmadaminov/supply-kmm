package uz.uzkass.smartpos.supply.android.ui.customers.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.core.Constants.BOTTOM_BAR_HEIGHT
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.Spacer12dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer24dp
import uz.uzkass.smartpos.supply.android.coreui.paginationStates
import uz.uzkass.smartpos.supply.android.ui.customers.data.CustomersTabEnum
//import uz.uzkass.smartpos.supply.android.ui.customers.data.CustomersTabEnum
import uz.uzkass.smartpos.supply.android.ui.customers.views.CustomerItem
import uz.uzkass.smartpos.supply.android.ui.customers.views.VisitItem
import uz.uzkass.smartpos.supply.android.ui.main.create_order.isScrolledToEnd
//import uz.uzkass.smartpos.supply.android.ui.customers.views.VisitItem
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
    val customerList = viewModel.customerList.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.customersEvent.flowWithLifecycle(lifecycle).collect { event ->
            when (event) {
                is CustomersEvent.RefreshCustomers -> {
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit, block = {
//        viewModel.loadAllData()
    })
    CustomersView(
        viewState = screenState,
        customersLazyPaging = customerList.value,
        visitsLazyPaging = customerList.value,
        onClickAdd = {},
        onClickSearch = {},
        onClickFilter = {},
        onRefresh = viewModel::onRefreshCustomers,
        loadNextCustomerList = viewModel::loadNextPage,
        loadNextVisitsList = viewModel::loadNextPage
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
private fun CustomersView(
    viewState: CustomersState,
    customersLazyPaging: List<CustomerListMobileDTO>,
    visitsLazyPaging: List<CustomerListMobileDTO>,
    onClickAdd: () -> Unit,
    onClickSearch: () -> Unit,
    onClickFilter: () -> Unit,
    onRefresh: () -> Unit,
    loadNextCustomerList: () -> Unit,
    loadNextVisitsList: () -> Unit,
) {
    val tabItems by remember {
        mutableStateOf(
            listOf(
                MR.strings.clients_tab_all.resourceId,
                MR.strings.clients_tab_visit.resourceId
            )
        )
    }
    val pullRefreshState =
        rememberPullRefreshState(refreshing = viewState.isRefreshing, onRefresh = onRefresh)
    val pagerState = rememberPagerState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
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
                    tabItems = tabItems
                )
            }
        },
        backgroundColor = SupplyTheme.colors.idleBackground,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = BOTTOM_BAR_HEIGHT.dp, start = 8.dp, end = 8.dp),
                content = {
                    TabContentView(
                        pagerState = pagerState,
                        tabsCount = tabItems.size,
                        customersLazyPaging = customersLazyPaging,
                        visitsLazyPaging = visitsLazyPaging,
                        loadNextCustomerList = loadNextCustomerList
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

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabContentView(
    pagerState: PagerState,
    tabsCount: Int,
    customersLazyPaging: List<CustomerListMobileDTO>,
    visitsLazyPaging: List<CustomerListMobileDTO>,
    loadNextCustomerList: () -> Unit
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

                        },
                        loadNext = loadNextCustomerList
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

@Composable
private fun AllCustomersView(
    customersLazyPaging: List<CustomerListMobileDTO>,
    onClickCustomerItem: (customerItem: CustomerListMobileDTO) -> Unit,
    loadNext: () -> Unit
) {

    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        item { Spacer12dp() }
        items(items = customersLazyPaging) { customerItem ->
            customerItem?.let { item ->
                CustomerItem(customerItem = item, onClickItem = { onClickCustomerItem(item) })
            }
        }
//        paginationStates(customersLazyPaging)
    }

    val endOfListReached = remember {
        derivedStateOf { listState.isScrolledToEnd() }
    }

    if (endOfListReached.value) {
        loadNext()
    }

}

@Composable
private fun VisitsView(
    visitsLazyPaging: List<CustomerListMobileDTO>,
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