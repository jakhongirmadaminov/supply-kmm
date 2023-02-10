package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.di.viewModelModule

class SelectCustomerViewModel2 constructor(private val customerApi: MobileCustomerResourceApi) :
    ViewModel() {

//    var customerPagingSource =
//        MutableStateFlow<Pagination<CustomerListMobileDTO>>(Pagination())
//        private set

    var searchQuery = ""

    var pagination: Pagination<CustomerListMobileDTO> = createPagination(searchQuery)

    init {
        pagination.loadFirstPage()

        viewModelScope.launch {
            delay(5000)
            pagination.loadNextPage()
        }
    }

    private fun createPagination(searchQuery: String) = Pagination(
        parentScope = viewModelScope,
        dataSource = LambdaPagedListDataSource { currentPage ->
            currentPage?.plus(
                customerApi.getListUsingGET89(
                    page = currentPage.size / 10,
                    search = searchQuery
                ).content!!
            ) ?: listOf()
        },
        comparator = { a: CustomerListMobileDTO, b: CustomerListMobileDTO ->
            if (a == b) 0 else 1
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
        pagination = createPagination(it)
    }

    fun loadNext() {
        pagination.loadNextPage()
    }

//    private var job: Job? = null
//    fun getCustomerByQuery(query: String) {
//        Log.d("TTT", "getCustomerByQuery: ")
//        job?.cancel()
//        Log.d("TTT", "Job: ")
//        job = viewModelScope.launch(Dispatchers.IO) {
//            Pager(config = PagingConfig(pageSize = 20),
//                pagingSourceFactory = {
//                    CustomerPageSource(
//                        customerApi = customerApi,
//                        searchQuery = query
//                    )
//                }).flow.catch { throwable ->
//            }.cachedIn(viewModelScope).collectLatest {
//                customerPagingSource.value = it
//            }
//        }
//    }
}

