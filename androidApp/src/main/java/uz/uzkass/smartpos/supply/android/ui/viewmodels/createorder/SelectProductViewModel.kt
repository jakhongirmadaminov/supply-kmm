package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.icerock.moko.network.generated.apis.MobileProductResourceApi
import dev.icerock.moko.network.generated.models.ProductDetailMobileDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.uzkass.smartpos.supply.core.utils.resultOf
import uz.uzkass.smartpos.supply.settings.LocalProductRepository
import uz.uzkass.smartpos.supply.settings.OrderProductModel
import uz.uzkass.smartpos.supply.viewmodels.home.SelectCustomerScreenState
import uz.uzkass.smartpos.supply.viewmodels.home.model.DropdownModel

class SelectProductViewModel constructor(
    private val productApi: MobileProductResourceApi,
    private val localProductRepository: LocalProductRepository
) :
    ViewModel() {

    private var query: String = ""
    private lateinit var pagingSource: ProductPageSource
    var productPage = Pager(config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            ProductPageSource(
                productApi = productApi,
                searchQuery = query
            ).also {
                pagingSource = it
            }
        })
        .flow


    private val _productDateState = MutableStateFlow(AddProductState())
    val productDateState: StateFlow<AddProductState> = _productDateState


    fun getProductByQuery(newQuery: String) {
        query = newQuery
        pagingSource.invalidate()
    }


    fun addProduct(qty: Int) {
        Log.d("TTT", localProductRepository.toString())

        localProductRepository.addProduct(
            OrderProductModel(
                id = _productDateState.value.productData!!.id,
                name = _productDateState.value.productData!!.name,
                qty = qty,
                unitId = _productDateState.value.productData?.unit?.id,
                price = _productDateState.value.productData?.price,
                totalPrice = _productDateState.value.productData?.price ?: (0.0 * qty)
            )
        )
    }

    fun loadProductData(productId: Long) {
        _productDateState.update { it.copy(loading = true) }

        viewModelScope.launch {
            resultOf {

                productApi.getUsingGET97(productId)

            }.onSuccess { product ->
                _productDateState.update {
                    it.copy(
                        loading = false,
                        productData = product
                    )
                }
            }.onFailure {

            }
        }
    }

}

data class ProductItemModel(
    val id: Long?,
    val label: String,
    val price: String,
)


data class AddProductState(
    val loading: Boolean = false,
    val productData: ProductDetailMobileDTO? = null,
    val unitList: List<DropdownModel> = emptyList()
)