package uz.uzkass.smartpos.supply.settings


class LocalProductRepository<T> {
    val productList = mutableListOf<T>()

    fun addProduct(product: T) {

    }

    fun getProducts(): List<T> {
        return productList
    }

    fun clean() {
        productList.clear()
    }
}
