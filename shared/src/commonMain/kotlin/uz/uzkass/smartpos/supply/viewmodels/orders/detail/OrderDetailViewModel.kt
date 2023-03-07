package uz.uzkass.smartpos.supply.viewmodels.orders.detail

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileCustomerBranchResourceApi
import dev.icerock.moko.network.generated.apis.MobileCustomerResourceApi
import dev.icerock.moko.network.generated.apis.MobileOrderResourceApi
import dev.icerock.moko.network.generated.models.MobileOrderDTO
import dev.icerock.moko.network.generated.models.MobileOrderDetailDTO
import dev.icerock.moko.network.generated.models.MobileOrderProductDTO
import dev.icerock.moko.network.generated.models.OrderStatus
import dev.icerock.moko.network.generated.models.PaymentType
import dev.icerock.moko.network.generated.models.SaleType
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.settings.LocalProductRepository
import uz.uzkass.smartpos.supply.settings.OrderProductModel
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrderStatusCodeEnum
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrderStatusModel

class OrderDetailViewModel
constructor(
    private val orderResourceApi: MobileOrderResourceApi,
    private val customerApi: MobileCustomerResourceApi,
    private val customerBranchApi: MobileCustomerBranchResourceApi,
    private val localProductRepository: LocalProductRepository
) : ViewModel() {


    private val _screenState = MutableStateFlow(OrderDetailState())
    val screenState get() = _screenState.asStateFlow()

    private var currentOrderInfo: MobileOrderDetailDTO? = null

    private val _orderChangeStatus: Channel<OrderChangeStatus> = Channel(Channel.BUFFERED)
    var orderChangeStatus = _orderChangeStatus.receiveAsFlow()
    fun loadOrderDetailById(orderId: Long) {


        viewModelScope.launch {
            _screenState.update {
                it.copy(loading = true)
            }


            resultOf {
                val req1 = async { customerApi.lookUpUsingGET75() }
                val req2 = async { orderResourceApi.getUsingGET96(orderId) }
                val req3 = async { customerBranchApi.lookUpUsingGET74() }


                val responseCustomer = req1.await()
                val responseOrderInfo = req2.await()
                val responseCustomerBranch = req3.await()
                Triple(
                    first = responseCustomer,
                    second = responseOrderInfo,
                    third = responseCustomerBranch
                )
            }.onSuccess { response ->
                val dtoOrderInfo = response.second
                val customerList = response.first.map {
                    DropdownModel(
                        id = it.id.toString(),
                        label = it.name.toString()
                    )
                }

                val customerBranchList = response.third.map {
                    DropdownModel(
                        id = it.id.toString(),
                        label = it.name.toString()
                    )
                }

                currentOrderInfo = dtoOrderInfo


                val productList = dtoOrderInfo.products?.map { item ->
                    OrderProductModel(
                        id = item.id,
                        name = item.name,
                        qty = item.qty?.toInt(),
                        orderProductId = item.orderProductId,
                        price = item.price,
                        totalPrice = item.totalPrice,
                        totalVatPrice = item.totalVatPrice,
                        unitId = item.unit?.id,
                        vatPrice = item.vatPrice,
                        vatRate = item.vatRate?.toLong()
                    )
                } ?: emptyList()


                localProductRepository.cleanAndAddProduct(productList)
                localProductRepository.customerId = dtoOrderInfo.customer?.id
                localProductRepository.companyBranchId = dtoOrderInfo.deliveryBranch?.id.toString()
                localProductRepository.sellTypeId = dtoOrderInfo.saleType?.code
                localProductRepository.paymentTypeId = dtoOrderInfo.paymentType?.code

                _screenState.update {
                    it.copy(
                        loading = false,
                        screenTitle = dtoOrderInfo.orderNumber ?: "",
                        orderStatusModel = OrderStatusModel(
                            name = dtoOrderInfo.status?.name ?: "",
                            code = OrderStatusCodeEnum.valueOf(dtoOrderInfo.status?.code ?: "")
                        ),
                        totalPrice = dtoOrderInfo.totalPrice ?: 0.0,
                        totalQty = dtoOrderInfo.totalQty ?: 0.0,
                        totalVatPrice = dtoOrderInfo.totalVatPrice ?: 0.0,
                        customerList = customerList,
                        currentCustomer = DropdownModel(
                            id = dtoOrderInfo.customer?.id.toString(),
                            label = dtoOrderInfo.customer?.name.toString()
                        ),
                        branchList = customerBranchList,
                        customerBranch = DropdownModel(
                            id = dtoOrderInfo.deliveryBranch?.id.toString(),
                            label = dtoOrderInfo.deliveryBranch?.name.toString()
                        ),
                        productList = localProductRepository.getProducts()
                    )
                }
            }.onFailure {


            }


        }
    }

    fun removeProduct(productModel: OrderProductModel) {
        localProductRepository.removeProduct(productModel)
    }


    fun selectCustomerItem(item: DropdownModel?) {
        _screenState.update {
            it.copy(currentCustomer = item)
        }
    }

    fun selectCustomerBranchItem(item: DropdownModel?) {
        _screenState.update {
            it.copy(customerBranch = item)
        }
    }

    fun onSave() {
        viewModelScope.launch {
            val request = MobileOrderDTO(
                customerId = screenState.value.currentCustomer?.id?.toLongOrNull(),
                deliveryBranchId = screenState.value.customerBranch?.id?.toLongOrNull(),
                products = localProductRepository.getProducts().map { item ->
                    MobileOrderProductDTO(
                        id = item.id,
                        qty = item.qty?.toDouble(),
                        orderProductId = item.id,
                        price = item.price,
                        unitId = item.unitId,
                        totalPrice = item.totalPrice,
                        vatPrice = item.vatPrice
                    )
                },
                saleType = currentOrderInfo?.saleType,
                paymentType = currentOrderInfo?.paymentType,
                status = currentOrderInfo?.status
            )

            resultOf {
                orderResourceApi.updateUsingPUT77(
                    currentOrderInfo?.id ?: 0,
                    request
                )
            }.onSuccess {
                _orderChangeStatus.send(OrderChangeStatus.Success("Success!"))
            }.onFailure {
                _orderChangeStatus.send(OrderChangeStatus.Error("Something wrong!"))
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

    val productList: List<OrderProductModel> = emptyList(),

    val customerList: List<DropdownModel> = emptyList(),

    val currentCustomer: DropdownModel? = null,

    val branchList: List<DropdownModel> = emptyList(),

    val customerBranch: DropdownModel? = null,
)

sealed class OrderChangeStatus {
    data class Success(val message: String) : OrderChangeStatus()
    data class Error(val message: String) : OrderChangeStatus()


}