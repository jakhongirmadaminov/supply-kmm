package uz.uzkass.smartpos.supply.viewmodels.orders.detail

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileOrderResourceApi
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf

class OrderDetailViewModel
constructor(private val orderResourceApi: MobileOrderResourceApi) : ViewModel() {


    fun loadOrderDetailById(orderId: Long) {
        viewModelScope.launch {
            resultOf {
                orderResourceApi.getUsingGET96(orderId)
            }.onSuccess {


            }.onFailure {


            }


        }
    }

}