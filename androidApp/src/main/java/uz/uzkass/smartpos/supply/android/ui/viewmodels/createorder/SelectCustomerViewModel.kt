package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.settings.LocalProductRepository

class SelectCustomerViewModel
constructor(
    private val customerApi: MobileCustomerResourceApi,
    private val localProductRepository: LocalProductRepository
) : ViewModel() {

    private var query: String = ""
    private lateinit var pagingSource: CustomerPageSource
    var customerPagingSource = Pager(config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            CustomerPageSource(
                customerApi = customerApi,
                searchQuery = query
            ).also {
                pagingSource = it
            }
        })
        .flow

    init {
        localProductRepository.clean()
    }

    fun getCustomerByQuery(newQuery: String) {
        Log.d("TTT", "getCustomerByQuery:${newQuery} ")
        query = newQuery
        pagingSource.invalidate()
    }
}

