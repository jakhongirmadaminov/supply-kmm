package uz.uzkass.smartpos.supply.viewmodels.clients

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
import uz.uzkass.smartpos.supply.utils.coroutines.CommonFlow
import uz.uzkass.smartpos.supply.utils.coroutines.asCommonFlow

class CustomersViewModel(
    private val api: MobileCustomerResourceApi
) : ViewModel() {

    private val pageSize = 20
    private val pagingConfig = PagingConfig(
        pageSize = pageSize,
        initialLoadSize = pageSize,
        enablePlaceholders = false,
    )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val pager = Pager(
        clientScope = viewModelScope,
        config = pagingConfig,
        initialKey = 1,
        getItems = { currentKey, size ->
            val items = api.getListUsingGET89(page = currentKey, size = size).content ?: emptyList()
            PagingResult(
                items = items,
                currentKey = currentKey,
                prevKey = { currentKey - 1 },
                nextKey = { currentKey + 1 }
            )
        }
    )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val commonPagingData: CommonFlow<PagingData<CustomerListMobileDTO>>
        get() = pager.pagingData.cachedIn(viewModelScope).asCommonFlow()


}