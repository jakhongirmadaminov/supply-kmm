package uz.uzkass.smartpos.supply.viewmodels.home

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.utils.coroutines.asCommonFlow
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

private const val PAGE_SIZE = 20

class SelectCustomerViewModel constructor(
    val customerApi: MobileCustomerResourceApi,
) : ViewModel() {

    private val _screenStateFlow = MutableStateFlow(SelectCustomerScreenState())
    val screenStateFlow get() = _screenStateFlow.asStateFlow()

    val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = PAGE_SIZE,
        enablePlaceholders = false,
    )

    val searchCustomerPager = MutableStateFlow<PagingData<CustomerListMobileDTO>?>(null)

    private var query: String = ""

    init {
        getCustomerByQuery()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun getCustomerByQuery(newQuery: String = "") {
        query = newQuery
        viewModelScope.launch {
            Pager(
                clientScope = viewModelScope,
                config = pagingConfig,
                initialKey = 0,
                getItems = { currentKey, size ->
                    val response = customerApi.getListUsingGET89(
                        page = currentKey,
                        size = size,
                        search = newQuery
                    )
                    val items = response.content ?: emptyList()
                    PagingResult(
                        items = items,
                        currentKey = currentKey,
                        prevKey = { if (currentKey == 0) null else currentKey - 1 },
                        nextKey = {
                            if ((response.totalPages
                                    ?: 1) > currentKey.plus(1)
                            ) currentKey.plus(1) else 1
                        }
                    )
                }
            ).pagingData.cachedIn(viewModelScope).collectLatest {
                searchCustomerPager.emit(it)
            }
        }
    }

    fun selectCustomer(newCustomer: DropdownModel) {

    }


}

data class SelectCustomerScreenState(
    val isLoading: Boolean = false,
    val customerList: List<DropdownModel> = emptyList(),


    )


