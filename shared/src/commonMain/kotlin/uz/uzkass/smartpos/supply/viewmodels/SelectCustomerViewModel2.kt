package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf

class SelectCustomerViewModel2 constructor(private val customerApi: MobileCustomerResourceApi) :
    ViewModel() {

//    var customerPagingSource =
//        MutableStateFlow<Pagination<CustomerListMobileDTO>>(Pagination())
//        private set

    var searchQuery = ""
    private var _pagedData = MutableStateFlow<List<CustomerListMobileDTO>>(listOf())
    val pagedData: StateFlow<List<CustomerListMobileDTO>>
        get() = _pagedData.asStateFlow()

    var pagination: Pagination<CustomerListMobileDTO> = createPagination()

    init {
        pagination.loadFirstPage()
    }

    var pageIndex = 0
    private fun createPagination() = Pagination(
        parentScope = viewModelScope,
        dataSource = LambdaPagedListDataSource { currentPage ->
            var somt: List<CustomerListMobileDTO> = listOf()
            resultOf {
                customerApi.getListUsingGET89(
                    page = pageIndex,
                    search = searchQuery
                ).content!!
            }.onSuccess {
                somt = it
                pageIndex++
            }.onFailure {
                println("PAGINATION ERROR ${it.message}")
            }

            currentPage?.let {
                val merged = currentPage.plus(somt)
                _pagedData.emit(merged)
                merged
            } ?: run {
                _pagedData.emit(somt)
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

    fun onQuery(it: String) {
        searchQuery = it
        pageIndex = 0
        viewModelScope.launch {
            _pagedData.emit(listOf())
            pagination.loadFirstPageSuspend()
        }
    }

    fun loadNext() {
        pagination.loadNextPage()
    }

}

