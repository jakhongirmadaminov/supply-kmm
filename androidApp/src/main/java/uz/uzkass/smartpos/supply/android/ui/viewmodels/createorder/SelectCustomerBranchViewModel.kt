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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.android.ui.main.create_order.selectcustomer.CustomerBranchModel
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.settings.LocalProductRepository
import uz.uzkass.smartpos.supply.viewmodels.ConfirmOrderState

class SelectCustomerBranchViewModel
constructor(
    private val customerApi: MobileCustomerBranchResourceApi,
    private val localProductRepository: LocalProductRepository
) : ViewModel() {


    private val _screenState = MutableStateFlow(SelectCustomerBranchState())
    val screenState: StateFlow<SelectCustomerBranchState> = _screenState


    fun getCustomerBranch(customerId: Long?) {
        _screenState.update {
            it.copy(
                loading = true
            )
        }
        viewModelScope.launch {
            resultOf {
                customerApi.lookUpUsingGET74(customerId = customerId)
            }.onSuccess { list ->
                _screenState.update {
                    it.copy(
                        loading = false,
                        customerBranchList = list.map { item ->
                            CustomerBranchModel(
                                id = item.id ?: 0,
                                name = item.name ?: ""
                            )
                        }
                    )
                }
            }.onFailure {

            }
        }
    }


    fun selectCustomerBranch(item:CustomerBranchModel){
        localProductRepository.customerBranchId = item.id.toString()
    }

}


data class SelectCustomerBranchState(
    val loading: Boolean = false,
    val customerBranchList: List<CustomerBranchModel> = emptyList()
)
