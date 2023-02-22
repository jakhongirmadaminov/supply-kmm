package uz.uzkass.smartpos.supply.settings


class LocalProductRepository {
    var productList = mutableListOf<OrderProductModel>()
    var contractId: String? = null
    var customerId: Long? = null
    var deliveryBranchId: String? = null
    var warehouseId: String? = null
    var sellTypeId: String? = null

    fun addProduct(product: OrderProductModel) {
        productList.add(product)
    }

    fun removeProduct(product: OrderProductModel) {
        productList.remove(product)
    }

    fun getProducts(): List<OrderProductModel> {
        return productList
    }

    fun clean() {
        productList.clear()
        contractId = null
        customerId = null
        deliveryBranchId = null
        warehouseId = null
    }
}

data class OrderProductModel(
    val id: Long?,
    val name: String?,
    val qty: Int?,

    val orderProductId: Long? = null,
    val price: Double? = null,
    val totalPrice: Double? = null,
    val totalVatPrice: Double? = null,
    val unitId: Long? = null,
    val vatPrice: Double? = null,
    val vatRate: Long? = null
)