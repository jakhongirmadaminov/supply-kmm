package uz.uzkass.smartpos.supply.viewmodels.orders

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileOrderResourceApi
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrderStatusCodeEnum
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrderStatusModel
import uz.uzkass.smartpos.supply.viewmodels.orders.models.OrdersModel

class OrdersViewModel constructor(
    private val ordersApi: MobileOrderResourceApi

) : ViewModel() {


    private var _ordersList = MutableStateFlow<List<OrdersModel>>(listOf())

    val ordersList: StateFlow<List<OrdersModel>>
        get() = _ordersList.asStateFlow()

    var pagination: Pagination<OrdersModel> = createPagination()

    private var pageIndex = 0
    var searchQuery: String? = null
    var branchId: Long? = null
    var warehouseId: Long? = null

    init {

    }

    private fun createPagination() = Pagination(
        parentScope = viewModelScope,
        dataSource = LambdaPagedListDataSource { currentPage ->
            var somt: List<OrdersModel> = listOf()
            resultOf {
                ordersApi.getListUsingGET102(
                    page = pageIndex,
                    search = searchQuery,
                    branchId = branchId,
                    warehouseId = warehouseId
                ).content!!
            }.onSuccess { list ->
                somt = list.map { item ->
                    OrdersModel(
                        id = item.id,
                        orderNumber = item.orderNumber ?: "",
                        orderDate = item.orderDate ?: "",
                        totalPrice = item.totalPrice ?: 0.0,
                        status = OrderStatusModel(
                            name = item.status?.name ?: "",
                            code = OrderStatusCodeEnum.valueOf(item.status?.code ?: "")
                        ),
                        customer = item.customer?.name ?: ""
                    )
                }
                pageIndex++
            }.onFailure {
                println("PAGINATION ERROR ${it.message}")
            }

            currentPage?.let {
                val merged = currentPage.plus(somt)
                _ordersList.emit(merged)
                merged
            } ?: run {
                _ordersList.emit(somt)
                somt
            }
        },
        comparator = { a: OrdersModel, b: OrdersModel ->
            if (a.id == b.id) 0 else 1
        },
        nextPageListener = { result: Result<List<OrdersModel>> ->
            if (result.isSuccess) {
                println("Next page successful loaded")
            } else {
                println("Next page loading failed")
            }
        },
        refreshListener = { result: Result<List<OrdersModel>> ->
            if (result.isSuccess) {
                println("Refresh successful")
            } else {
                println("Refresh failed")
            }
        },
        initValue = listOf()
    )


    fun onRefresh() {


    }

    fun loadNextPage() {
        pagination.loadNextPage()
    }

    fun loadAllData() {
        pagination.loadFirstPage()
    }


}