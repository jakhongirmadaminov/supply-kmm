package uz.uzkass.smartpos.supply.viewmodels.clients

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
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

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val customersPaging = Pager(
        clientScope = viewModelScope,
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            enablePlaceholders = false,
        ),
        initialKey = 0,
        getItems = { currentKey, size ->
            val response = api.getListUsingGET89(page = currentKey, size = size)
            val items = response.content ?: emptyList()
            PagingResult(
                items = items,
                currentKey = currentKey,
                prevKey = { if (currentKey == 0) null else currentKey - 1 },
                nextKey = { if ((response.totalPages ?: 1) > currentKey.plus(1)) currentKey.plus(1) else 1 }
            )
        }
    ).pagingData.cachedIn(viewModelScope).asCommonFlow()

    private val _customersEvent = Channel<CustomersEvent>(Channel.BUFFERED)
    val customersEvent = _customersEvent.receiveAsFlow()

    private val _screenState = MutableStateFlow(CustomersState())
    val screenState = _screenState.asStateFlow()

    fun onRefreshCustomers() {
        _screenState.update { it.copy(isRefreshing = true) }
        viewModelScope.launch {
            _customersEvent.send(CustomersEvent.RefreshCustomers)
            delay(500)
            withContext(Main) {
                _screenState.update { it.copy(isRefreshing = false) }
            }
        }
    }

}

data class CustomersState(
    val isRefreshing: Boolean = false
)

sealed class CustomersEvent {
    object RefreshCustomers : CustomersEvent()
}