package uz.uzkass.smartpos.supply.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.network.generated.apis.MobileDashboardResourceApi
import dev.icerock.moko.network.generated.models.MobileOrderStatisticsDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


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
            kotlin.runCatching {
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
    val orderTotalAmount: String? = null,
    val orderTotalAmountForToday: String? = null,
    val orderTotalCount: String? = null,
    val orderTotalCountForToday: String? = null,
    val planRouteCompletedTotal: String? = null,
    val planRouteTotal: String? = null
) {
    companion object {
        fun generateHomeData(rawData: MobileOrderStatisticsDTO): HomeDataState {
            val temp = HomeDataState(
                orderTotalAmount = rawData.orderTotalAmount.toString(),
                orderTotalAmountForToday = rawData.orderTotalAmountForToday.toString(),
                orderTotalCount = rawData.orderTotalCount.toString(),
                orderTotalCountForToday = rawData.orderTotalCountForToday.toString(),
                planRouteCompletedTotal = rawData.planRouteCompletedTotal.toString(),
                planRouteTotal = rawData.planRouteTotal.toString()
            )
            return temp
        }
    }
}