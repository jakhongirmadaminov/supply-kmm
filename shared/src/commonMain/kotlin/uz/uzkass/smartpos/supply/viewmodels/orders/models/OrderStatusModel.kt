package uz.uzkass.smartpos.supply.viewmodels.orders.models


data class OrderStatusModel(
    val name: String,
    val code: OrderStatusCodeEnum
)


enum class OrderStatusCodeEnum {
    NEW,
    COMPLETED,
    APPROVED
}