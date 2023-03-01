package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder.models.ProductItemModel
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.settings.LocalProductRepository
import uz.uzkass.smartpos.supply.settings.OrderProductModel
import uz.uzkass.smartpos.supply.viewmodels.home.SelectCustomerScreenState
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

class AddProductViewModel constructor(
    private val localProductRepository: LocalProductRepository
) :
    ViewModel() {


    private val _productDateState = MutableStateFlow(AddProductState())
    val productDateState: StateFlow<AddProductState> = _productDateState


    fun getProductByQuery(newQuery: String) {

    }


    fun addProduct(qty: Int, productItemModel: ProductItemModel) {

        val totalPriceWithoutVat = productItemModel.price * qty

        val vatAmount =
            (totalPriceWithoutVat * (productItemModel.vatAmount / 100.0))


        localProductRepository.addProduct(
            OrderProductModel(
                id = productItemModel.id,
                name = productItemModel.label,
                qty = qty,
                unitId = productItemModel.unitId,
                price = totalPriceWithoutVat,
                totalPrice = totalPriceWithoutVat + vatAmount,
                vatPrice = vatAmount
            )
        )
    }
//
//    fun loadProductData(productId: Long) {
//        _productDateState.update { it.copy(loading = true) }
//
//        viewModelScope.launch {
////            resultOf {
////
////                productApi.getUsingGET97(productId)
////
////            }.onSuccess { product ->
////                _productDateState.update {
////                    it.copy(
////                        loading = false,
////                        productData = product
////                    )
////                }
////            }.onFailure {
////
////            }
//        }
//    }

}


data class AddProductState(
    val loading: Boolean = false,
//    val productData: ProductDetailMobileDTO? = null,
    val unitList: List<DropdownModel> = emptyList()
)