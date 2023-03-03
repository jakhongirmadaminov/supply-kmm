package uz.uzkass.smartpos.supply.viewmodels.orders.detail

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileOrderResourceApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrderStatusCodeEnum
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrderStatusModel

class OrderDetailViewModel
constructor(private val orderResourceApi: MobileOrderResourceApi) : ViewModel() {


    private val _screenState = MutableStateFlow(OrderDetailState())
    val screenState get() = _screenState.asStateFlow()


    fun loadOrderDetailById(orderId: Long) {
        viewModelScope.launch {
            _screenState.update {
                it.copy(loading = true)
            }
            resultOf {
                orderResourceApi.getUsingGET96(orderId)
            }.onSuccess { dto ->

                _screenState.update {
                    it.copy(
                        loading = false,
                        screenTitle = dto.orderNumber ?: "",
                        orderStatusModel = OrderStatusModel(
                            name = dto.status?.name ?: "",
                            code = OrderStatusCodeEnum.valueOf(dto.status?.code ?: "")
                        ),
                        totalPrice = dto.totalPrice ?: 0.0,
                        totalQty = dto.totalQty ?: 0.0,
                        totalVatPrice = dto.totalVatPrice ?: 0.0

                    )
                }
            }.onFailure {


            }


        }
    }

}

data class OrderDetailState(
    val loading: Boolean = false,
    val screenTitle: String = "",
    val orderStatusModel: OrderStatusModel? = null,

    val totalPrice: Double = 0.0,

    val totalPriceWithoutVat: Double = 0.0,

    val totalQty: Double = 0.0,

    val totalVatPrice: Double = 0.0,

    val currentCustomer: DropdownModel? = null,

    val customerBranch: DropdownModel? = null,



)