package uz.uzkass.smartpos.supply.settings

object LocalRepositoryHolder {
    val productList = mutableListOf<String>()

    fun addProduct() {

    }

    fun clean(){
        productList.clear()
    }

}

class LocalRepository<T> {
    val productList = mutableListOf<T>()

    fun addProduct(product: T) {

    }

    fun getProducts(): List<T> {
        return productList
    }


}
