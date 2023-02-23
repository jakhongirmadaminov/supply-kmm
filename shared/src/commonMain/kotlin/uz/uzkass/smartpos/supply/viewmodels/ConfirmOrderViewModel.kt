package uz.uzkass.smartpos.supply.viewmodels


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileOrderResourceApi
import dev.icerock.moko.network.generated.models.MobileOrderDTO
import dev.icerock.moko.network.generated.models.MobileOrderProductDTO
import dev.icerock.moko.network.generated.models.OrderStatus
import dev.icerock.moko.network.generated.models.SaleType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.settings.LocalProductRepository

class ConfirmOrderViewModel constructor(
    private val ordersApi: MobileOrderResourceApi,
    private val localProductRepository: LocalProductRepository
) :
    ViewModel() {

    private val _productDateState = MutableStateFlow(ConfirmOrderState())
    val productDateState: StateFlow<ConfirmOrderState> = _productDateState

    fun confirmOrder() {
        viewModelScope.launch {
            val request = MobileOrderDTO(
                contractId = localProductRepository.contractId?.toLongOrNull(),
                customerId = localProductRepository.customerId,
                deliveryBranchId = localProductRepository.deliveryBranchId?.toLongOrNull(),
                warehouseId = localProductRepository.warehouseId?.toLongOrNull(),
                orderDate = "2023-02-22T11:10",
                products = localProductRepository.getProducts().map { item ->
                    MobileOrderProductDTO(
                        id = item.id,
                        qty = item.qty?.toDouble(),
                        orderProductId = item.id,
                        price = item.price,
                        totalPrice = item.totalPrice
                    )
                },
                saleType = SaleType(
                    code = "PREPAYMENT"
                ),
                status = OrderStatus(
                    code = "NEW"
                )
            )
            ordersApi.createUsingPOST83(
                request
            )
        }
    }
}


data class ConfirmOrderState(
    val loading: Boolean = false,

    )