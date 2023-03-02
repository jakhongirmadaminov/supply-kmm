package uz.uzkass.smartpos.supply.viewmodels.orders.models


data class OrdersModel(
    val id: Long?,

    val customer: String,

    val orderDate: kotlin.String,

    val orderNumber: kotlin.String,

    val status: OrderStatusModel,

    val totalPrice: Double

)