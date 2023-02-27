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

    private val _screenState = MutableStateFlow(ConfirmOrderState())
    val screenState: StateFlow<ConfirmOrderState> = _screenState



    fun confirmOrder() {
        viewModelScope.launch {
            val request = MobileOrderDTO(
                customerId = localProductRepository.customerId,
                contractId = localProductRepository.contractId?.toLongOrNull(),

                branchId = localProductRepository.companyBranchId?.toLongOrNull(),
                warehouseId = localProductRepository.companyWarehouseId?.toLongOrNull(),

                deliveryBranchId = localProductRepository.customerBranchId?.toLongOrNull(),
                orderDate = "2023-02-22T11:10",
                products = localProductRepository.getProducts().map { item ->
                    MobileOrderProductDTO(
                        id = item.id,
                        qty = item.qty?.toDouble(),
                        orderProductId = item.id,
                        price = item.price,
                        unitId = item.unitId,
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
            ordersApi.createUsingPOST89(
                request
            )
        }
    }
}


data class ConfirmOrderState(
    val loading: Boolean = false,

    val productCount: String = "",

    val summa: String = "",

    val vatAmount: String = "",

    val companyName: String = ""


)