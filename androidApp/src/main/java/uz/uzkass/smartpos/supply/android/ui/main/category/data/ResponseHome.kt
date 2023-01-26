package uz.uzkass.smartpos.supply.android.ui.main.category.data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ResponseHome(
    @SerialName("orderTotalAmount")
    val orderTotalAmount: String? = null,
    @SerialName("orderTotalAmountForToday")
    val orderTotalAmountForToday: String? = null,
    @SerialName("orderTotalCount")
    val orderTotalCount: String? = null,
    @SerialName("orderTotalCountForToday")
    val orderTotalCountForToday: String? = null,
    @SerialName("planRouteCompletedTotal")
    val planRouteCompletedTotal: String? = null,
    @SerialName("planRouteTotal")
    val planRouteTotal: String? = null
)