package uz.uzkass.smartpos.supply.android.ui.customers

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import org.koin.androidx.compose.koinViewModel
import uz.uzkass.smartpos.supply.android.coreui.AppBarButton
import uz.uzkass.smartpos.supply.android.coreui.AppBarTitle
import uz.uzkass.smartpos.supply.android.coreui.DefaultAppBar
import uz.uzkass.smartpos.supply.android.coreui.Spacer16dp
import uz.uzkass.smartpos.supply.android.coreui.Spacer24dp
import uz.uzkass.smartpos.supply.android.ui.theme.SupplyTheme
import uz.uzkass.smartpos.supply.viewmodels.clients.CustomersViewModel
import uz.uzkassa.smartpos.supply.library.MR

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
@Destination
fun CustomersScreen(
//    navigator: DestinationsNavigator,
    viewModel: CustomersViewModel = koinViewModel()
) {
    val pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = { })
    val pagerState = rememberPagerState()
    val customersLazyPaging = viewModel.commonPagingData.collectAsLazyPagingItems()

    CustomersView(
        pullRefreshState = pullRefreshState,
        pagerState = pagerState,
        customersLazyPaging = customersLazyPaging,
        onClickAdd = {},
        onClickSearch = {},
        onClickFilter = {}
    )

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
private fun CustomersView(
    pullRefreshState: PullRefreshState,
    pagerState: PagerState,
    customersLazyPaging: LazyPagingItems<CustomerListMobileDTO>,
    onClickAdd: () -> Unit,
    onClickSearch: () -> Unit,
    onClickFilter: () -> Unit
) {
    val tabsResource by remember { mutableStateOf(listOf(MR.strings.clients_tab_all, MR.strings.clients_tab_visit)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SupplyTheme.colors.background)
            .pullRefresh(pullRefreshState)
    ) {
        CustomersTopBar(
            onClickAdd = onClickAdd,
            onClickSearch = onClickSearch,
            onClickFilter = onClickFilter
        )
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            selectedTabIndex = pagerState.currentPage,
            tabs = {
                tabsResource.forEachIndexed { index, tabTitle ->

                }
            }
        )
        Spacer16dp()
        Column {
            TabContentView(
                customersLazyPaging = customersLazyPaging,
            )
        }
        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnimatedTab(
    pagerState: PagerState,
    index: Int,
    @StringRes tabTitle: Int,
    onClickTab: () -> Unit
) {
    val isSelected by remember { mutableStateOf(index == pagerState.currentPage) }
    val selectedTabTextColor by animateColorAsState(targetValue = if (isSelected) SupplyTheme.colors.selected else SupplyTheme.colors.unSelected)
    val transition = updateTransition(targetState = pagerState, label = "Transition anim")
//    val tabTagColor = transition.animateColor(trans)
}

@Composable
private fun TabContentView(
    customersLazyPaging: LazyPagingItems<CustomerListMobileDTO>
) {

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