package uz.uzkass.smartpos.supply.viewmodels


import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileOrderResourceApi
import dev.icerock.moko.network.generated.models.MobileOrderDTO
import dev.icerock.moko.network.generated.models.MobileOrderProductDTO
import dev.icerock.moko.network.generated.models.OrderStatus
import dev.icerock.moko.network.generated.models.SaleType
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import public.apis.PublicOrderResourceApi
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.settings.LocalProductRepository
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

class ConfirmOrderViewModel constructor(
    private val ordersApi: MobileOrderResourceApi,
    private val localProductRepository: LocalProductRepository,
    private val publicOrderResourceApi: PublicOrderResourceApi
) :
    ViewModel() {

    private val _screenState = MutableStateFlow(ConfirmOrderState())
    val screenState: StateFlow<ConfirmOrderState> = _screenState

    init {
//        val productList = localProductRepository.productList

        getAllData()

    }

    fun getAllData() {
        viewModelScope.launch {
            resultOf {
                val async1 = async { publicOrderResourceApi.getPaymentTypesUsingGET3() }
                val async2 = async { publicOrderResourceApi.getSaleTypesUsingGET3() }
                val response1 = async1.await()
                val response2 = async2.await()
                response1 to response2
            }.onSuccess { result ->

                val paymentType = result.first.map {
                    DropdownModel(
                        id = it.code.toString(),
                        label = it.name.toString()
                    )
                }

                val saleType = result.second.map {
                    DropdownModel(
                        id = it.code.toString(),
                        label = it.name.toString()
                    )
                }

                var productCount = 0
                var summa = 0.0
                var vatSumma = 0.0

                localProductRepository.productList.forEach { item ->
                    productCount += item.qty ?: 0
                    summa += item.totalPrice ?: 0.0
                    vatSumma += item.vatPrice ?: 0.0
                }

                _screenState.update {
                    it.copy(
                        loading = true,
                        saleType = saleType,
                        paymentType = paymentType,
                        productCount = productCount.toString(),
                        summa = summa.toString(),
                        vatAmount = vatSumma.toString()
                    )
                }

            }.onFailure {

            }
        }
    }

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
                        totalPrice = item.totalPrice,
                        vatPrice = item.vatPrice
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

    val saleType: List<DropdownModel> = emptyList(),

    val paymentType: List<DropdownModel> = emptyList(),

    val productCount: String = "235",

    val summa: String = "10212",

    val vatAmount: String = "200",

    val companyName: String = ""


)