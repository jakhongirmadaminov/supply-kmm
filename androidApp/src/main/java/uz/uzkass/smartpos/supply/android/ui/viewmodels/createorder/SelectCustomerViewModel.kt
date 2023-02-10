package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CustomerDetailDTO
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SelectCustomerViewModel
constructor(private val customerApi: MobileCustomerResourceApi) : ViewModel() {

    var customerPagingSource =
        MutableStateFlow<PagingData<CustomerListMobileDTO>>(PagingData.empty())
        private set

    init {
//        getCustomerByQuery("")
    }

    private var job: Job? = null
    fun getCustomerByQuery(query: String) {
        Log.d("TTT", "getCustomerByQuery: ")
        job?.cancel()
        Log.d("TTT", "Job: ")
        job = viewModelScope.launch(Dispatchers.IO) {
            Pager(config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    CustomerPageSource(
                        customerApi = customerApi,
                        searchQuery = query
                    )
                }).flow.catch { throwable ->
            }.cachedIn(viewModelScope).collectLatest {
                customerPagingSource.value = it
            }
        }
    }
}

