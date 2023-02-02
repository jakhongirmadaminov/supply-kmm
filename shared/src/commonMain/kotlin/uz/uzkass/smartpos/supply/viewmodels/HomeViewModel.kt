package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileDashboardResourceApi
import dev.icerock.moko.network.generated.models.MobileOrderStatisticsDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf


class HomeViewModel
constructor(private val api: MobileDashboardResourceApi) : ViewModel(
) {
    private val _homeData = MutableStateFlow(HomeDataState())
    val homeData get() = _homeData.asStateFlow()


    init {
        getAllData()
    }

    private fun getAllData() {
        viewModelScope.launch {
            resultOf {
                api.getOrderStatisticsUsingGET1()
            }.onFailure {

            }.onSuccess { rawData ->
                _homeData.emit(
                    HomeDataState.generateHomeData(rawData)
                )
            }
        }
    }

    fun refreshData() {
        getAllData()
    }


}

data class HomeDataState(
    val todayOrderCount: String = "",
    val summaTodayOrders: String = "",

    val orderCount: String = "",
    val summaAllOrders: String = "",

    val completedOrder: String = "",
    val countOrderPlane: String = "",

    val avatarImageUrl: String? = null
) {
    companion object {
        fun generateHomeData(rawData: MobileOrderStatisticsDTO): HomeDataState {
            val temp = HomeDataState(
                todayOrderCount = rawData.orderTotalAmount.toString(),
                summaTodayOrders = rawData.orderTotalAmountForToday.toString(),
                orderCount = rawData.orderTotalCount.toString(),
                summaAllOrders = rawData.orderTotalCountForToday.toString(),
                completedOrder = rawData.planRouteCompletedTotal.toString(),
                countOrderPlane = rawData.planRouteTotal.toString()
            )
            return temp
        }
    }
}