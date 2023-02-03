package uz.uzkass.smartpos.supply.viewmodels.clients

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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
    )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val commonPagingData = pager.pagingData.cachedIn(viewModelScope).asCommonFlow()

}