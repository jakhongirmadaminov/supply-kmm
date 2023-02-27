package uz.uzkass.smartpos.supply.android.ui.viewmodels.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.network.generated.apis.MobileProductResourceApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf

class ProductViewModel constructor(
    private val productResourceApi: MobileProductResourceApi

) : ViewModel(

) {
    private val _productState = MutableStateFlow(ProductDetailState())
    val productState get() = _productState.asStateFlow()

    fun getProductInfoById(productId: Long) {
        viewModelScope.launch {

            resultOf {
                productResourceApi.getUsingGET97(productId)
            }.onSuccess { item ->
                _productState.update {
                    it.copy(
                        name = item.name,
                        catalogName = item.catalogName,
                        vatBarcode = item.vatBarcode,
                        barcode = item.barcode,
                        packageName = item.packageName,
                        qtyInPackage = item.qtyInPackage.toString(),

                    )
                }
            }.onFailure {

            }

        }
    }


}

data class ProductDetailState(
    val name: String? = null,

    val catalogName: String? = null,
    val vatBarcode: String? = null,
    val barcode: String? = null,

    val packageName: String? = null,
    val qtyInPackage: String? = null,
)