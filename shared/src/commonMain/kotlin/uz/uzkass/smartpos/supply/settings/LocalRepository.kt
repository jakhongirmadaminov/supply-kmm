package uz.uzkass.smartpos.supply.settings


class LocalProductRepository {
    var productList = mutableListOf<OrderProductModel>()

    var contractId: String? = null
    var customerId: Long? = null

    var companyBranchId: String? = null
    var companyWarehouseId: String? = null

    var sellTypeId: String? = null
    var customerBranchId: String? = null

    val totalVatPrice: Double
        get() {
            return productList.sumOf { it.vatPrice ?: 0.0 }
        }

    val totalPriceWithoutVat: Double
        get() {
            return productList.sumOf { it.price ?: 0.0 }
        }

    val totalPriceWithVat: Double
        get() {
            return productList.sumOf { it.totalPrice ?: 0.0 }
        }


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

        companyBranchId = null
        companyWarehouseId = null

        sellTypeId = null

        customerBranchId = null
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