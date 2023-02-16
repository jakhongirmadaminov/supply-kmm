package uz.uzkass.smartpos.supply.settings


class LocalProductRepository {
    val productList = mutableListOf<OrderProductModel>()

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
    }
}

data class OrderProductModel(
    val id: Long?,
    val name: String?,
    val qty: Int?
)