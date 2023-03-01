package uz.uzkass.smartpos.supply.android.ui.viewmodels.createorder

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.icerock.moko.network.generated.apis.MobileOrderResourceApi
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

class SelectProductViewModel constructor(
    private val productApi: MobileOrderResourceApi,
    private val localProductRepository: LocalProductRepository
) :
    ViewModel() {


    private val _screenState = MutableStateFlow(SelectProductState())
    val screenState: StateFlow<SelectProductState> = _screenState


    init {
        getProductByQuery()
    }

//    private var query: String = ""
//    private lateinit var pagingSource: ProductPageSource
//    var productPage = Pager(config = PagingConfig(pageSize = 20),
//        pagingSourceFactory = {
//            ProductPageSource(
//                productApi = productApi,
//                searchQuery = query
//            ).also {
//                pagingSource = it
//            }
//        })
//        .flow


    fun getProductByQuery(newQuery: String = "") {


        viewModelScope.launch {
            _screenState.update {
                it.copy(loading = true)
            }
            resultOf {
                productApi.getProductLookUpUsingGET11(
                    branchId = localProductRepository.companyBranchId?.toLongOrNull(),
                    warehouseId = localProductRepository.companyWarehouseId?.toLongOrNull(),
                    search = newQuery
                )
            }.onSuccess { list ->
                _screenState.update {
                    it.copy(
                        loading = false,
                        productList = list.map {
                            ProductItemModel(
                                id = it.id,
                                label = it.name.toString(),
                                price = it.price?:0.0,
                                unitId = it.unit?.id?:0,
                                vatAmount = it.vat?.amount?:0.0

                            )
                        }
                    )
                }
            }.onFailure {
                _screenState.update {
                    it.copy(loading = false)
                }
            }
        }

    }


//    fun addProduct(qty: Int) {
//
//        val totalPriceWithoutVat = (_screenState.value.productData?.price?:0.0) * qty
//
//        val vatAmount =
//            (totalPriceWithoutVat * ((_screenState.value.productData?.vat?.amount?:0.0) / 100.0))
//
//
//        localProductRepository.addProduct(
//            OrderProductModel(
//                id = _screenState.value.productData!!.id,
//                name = _screenState.value.productData!!.name,
//                qty = qty,
//                unitId = _screenState.value.productData?.unit?.id,
//                price = totalPriceWithoutVat,
//                totalPrice = totalPriceWithoutVat + vatAmount,
//                vatPrice = vatAmount
//            )
//        )
//    }

//    fun loadProductData(productId: Long) {
//        _screenState.update { it.copy(loading = true) }
//
//        viewModelScope.launch {
//            resultOf {
//
//                productApi.getUsingGET97(productId)
//
//            }.onSuccess { product ->
//                _screenState.update {
//                    it.copy(
//                        loading = false,
//                        productData = product
//                    )
//                }
//            }.onFailure {
//
//            }
//        }
//    }

}

data class SelectProductState(
    val loading: Boolean = false,
    val productList: List<ProductItemModel> = emptyList()
)