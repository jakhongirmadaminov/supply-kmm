package uz.uzkass.smartpos.supply.viewmodels.clients

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.PageCustomerListMobileDTO
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.uzkass.smartpos.supply.utils.coroutines.asCommonFlow

private const val PAGE_SIZE = 20

class CustomersViewModel(
    private val api: MobileCustomerResourceApi
) : ViewModel() {

    init {
        Napier.d("TTT vm: ${this.hashCode()}")
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val pager = Pager(
        clientScope = viewModelScope,
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            enablePlaceholders = false,
        ),
        initialKey = 0,
        getItems = { currentKey, size ->
            val response = getPagingList(page = currentKey, size = size, search = _screenState.value.searchQuery)
            val items = response.content ?: emptyList()
            PagingResult(
                items = items,
                currentKey = currentKey,
                prevKey = { if (currentKey == 0) null else currentKey - 1 },
                nextKey = { if ((response.totalPages ?: 1) > currentKey.plus(1)) currentKey.plus(1) else 1 }
            )
        }
    )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    var customersPaging = pager.pagingData.cachedIn(viewModelScope).asCommonFlow()

    private val _customersEvent = Channel<CustomersEvent>(Channel.BUFFERED)
    val customersEvent = _customersEvent.receiveAsFlow()

    private val _screenState = MutableStateFlow(CustomersState())
    val screenState = _screenState.asStateFlow()

    private suspend fun getPagingList(page: Int, size: Int, search: String? = null): PageCustomerListMobileDTO =
        api.getListUsingGET89(page = page, size = size, search = search)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun onRefreshCustomers() {
        _screenState.update { it.copy(refreshing = true) }
        viewModelScope.launch {
            customersPaging = pager.pagingData.cachedIn(viewModelScope).asCommonFlow()
            delay(500)
            withContext(Main) {
                _screenState.update { it.copy(refreshing = false) }
            }
        }
    }

    fun onClickSearch() {
        Napier.d("TTT vm on click search")
        _screenState.update { it.copy(searching = true, searchButtonVisible = false, filterButtonVisible = false) }
    }

    fun onSearchCustomer(search: String?) {
        Napier.d("TTT search: $search")
        _screenState.update { it.copy(searchQuery = search) }
    }

    fun onCloseSearch() {
        Napier.d("TTT vm on close search")
        _screenState.update {
            it.copy(
                searching = false,
                searchButtonVisible = true,
                filterButtonVisible = true,
                searchQuery = null
            )
        }
    }

}

data class CustomersState(
    val addButtonVisible: Boolean = true,
    val searchButtonVisible: Boolean = true,
    val filterButtonVisible: Boolean = true,
    val refreshing: Boolean = false,
    val searching: Boolean = false,
    val searchQuery: String? = null
)

sealed class CustomersEvent {
    object RefreshCustomers : CustomersEvent()
}