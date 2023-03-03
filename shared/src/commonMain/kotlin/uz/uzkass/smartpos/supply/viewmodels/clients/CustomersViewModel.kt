package uz.uzkass.smartpos.supply.viewmodels.clients

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.uzkass.smartpos.supply.core.utils.resultOf

private const val PAGE_SIZE = 20

class CustomersViewModel(
    private val api: MobileCustomerResourceApi
) : ViewModel() {



    private var _customerList = MutableStateFlow<List<CustomerListMobileDTO>>(listOf())

    val customerList: StateFlow<List<CustomerListMobileDTO>>
        get() = _customerList.asStateFlow()

    var customerPagination: Pagination<CustomerListMobileDTO> = createCustomerPagination()
    init {
        loadAllData()
    }

    private fun createCustomerPagination(): Pagination<CustomerListMobileDTO> {
        return Pagination(
            parentScope = viewModelScope,
            dataSource = LambdaPagedListDataSource { currentPage ->
                var somt: List<CustomerListMobileDTO> = listOf()
                resultOf {
                    api.getListUsingGET101(
                        page = pageIndex,
                    ).content!!
                }.onSuccess { list ->
                    somt = list
                    pageIndex++
                }.onFailure {
                    println("PAGINATION ERROR ${it.message}")
                }

                currentPage?.let {
                    val merged = currentPage.plus(somt)
                    _customerList.emit(merged)
                    merged
                } ?: run {
                    _customerList.emit(somt)
                    somt
                }
            },
            comparator = { a: CustomerListMobileDTO, b: CustomerListMobileDTO ->
                if (a.id == b.id) 0 else 1
            },
            nextPageListener = { result: Result<List<CustomerListMobileDTO>> ->
                if (result.isSuccess) {
                    println("Next page successful loaded")
                } else {
                    println("Next page loading failed")
                }
            },
            refreshListener = { result: Result<List<CustomerListMobileDTO>> ->
                if (result.isSuccess) {
                    println("Refresh successful")
                } else {
                    println("Refresh failed")
                }
            },
            initValue = listOf()
        )

    }

    private var pageIndex = 0

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

    fun loadNextPage() {
        customerPagination.loadNextPage()
    }

    fun loadAllData() {
        customerPagination.loadFirstPage()
    }


}

data class CustomersState(
    val isRefreshing: Boolean = false
)

sealed class CustomersEvent {
    object RefreshCustomers : CustomersEvent()
}