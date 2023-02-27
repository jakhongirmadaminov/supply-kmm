package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.icerock.moko.network.generated.apis.MobileCustomerBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.models.CustomerListMobileDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.settings.LocalProductRepository

class SelectCustomerViewModel
constructor(
    private val customerApi: MobileCustomerResourceApi,
    private val customerBranchApi: MobileCustomerBranchResourceApi,
    private val localProductRepository: LocalProductRepository
) : ViewModel() {

    private val _navigate: Channel<SelectCustomerNavigator> = Channel(Channel.BUFFERED)
    var navigate = _navigate.receiveAsFlow()


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

    fun selectCustomer(item: CustomerListMobileDTO) {
        viewModelScope.launch {
            resultOf {
                customerBranchApi.lookUpUsingGET74(customerId = item.id)
            }.onSuccess {
                if (it.size > 1) {
                    _navigate.send(
                        SelectCustomerNavigator.ToSelectCustomerBranch(
                            customerId = item.id ?: 0
                        )
                    )
                } else {
                    _navigate.send(
                        SelectCustomerNavigator.ToSelectContract(
                            customerId = item.id ?: 0
                        )
                    )
                }
            }.onFailure {

            }
        }

    }
}


sealed class SelectCustomerNavigator {
    data class ToSelectCustomerBranch(val customerId: Long) : SelectCustomerNavigator()
    data class ToSelectContract(val customerId: Long) : SelectCustomerNavigator()
}